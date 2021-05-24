package com.example.c196_courseplanner.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.AssessmentDetail;
import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.R;
import com.example.c196_courseplanner.utilities.MyReceiver;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {
    private static int numAlert;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);


    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentStartDate;
        private final TextView assessmentEndDate;
        private long startDate;
        private long endDate;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentTitleView = itemView.findViewById(R.id.assessmentItemTitle);
            assessmentStartDate = itemView.findViewById(R.id.assessmentItemStart);
            assessmentEndDate = itemView.findViewById(R.id.assessmentItemEnd);
            ImageView assessmentAlert = itemView.findViewById(R.id.assessmentAlert);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentId", current.getId());
                    intent.putExtra("assessmentTitle", current.getTitle());
                    intent.putExtra("assessmentInfo", current.getInfo());
                    intent.putExtra("assessmentType", current.getType());
                    intent.putExtra("assessmentStart", current.getStartDate());
                    intent.putExtra("assessmentEnd", current.getEndDate());
                    intent.putExtra("associatedCourseId", current.getAssociatedCourseID());
                    context.startActivity(intent);
                }
            });
            assessmentAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);

                    Intent intent = new Intent(context, MyReceiver.class);
                    intent.putExtra("key", String.format("The assessment %s starts today!", current.getTitle()));
                    PendingIntent sender = PendingIntent.getBroadcast( context, ++numAlert, intent, 0);
                    AlarmManager alarmManager = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
                    LocalDate localDate = LocalDate.parse(current.getStartDate(), dtf);

                    startDate = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);

                    Intent intent1 = new Intent(context, MyReceiver.class);
                    intent1.putExtra("key", String.format("The assessment %s is due today!", current.getTitle()));
                    PendingIntent sender1 = PendingIntent.getBroadcast(context, ++numAlert, intent1, 0);
                    LocalDate localDate1 = LocalDate.parse(current.getEndDate(), dtf);

                    endDate = localDate1.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    //https://stackoverflow.com/questions/40048236/remove-persistent-notification-with-alarm-manager
                    //To add removing deleting notifications
                    alarmManager.set(AlarmManager.RTC_WAKEUP, endDate, sender1);
                    Snackbar.make(v, "Notification set for start and end dates", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
            });

        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Assessment> mAssessments;


    public AssessmentsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentsAdapter.AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessments_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentsAdapter.AssessmentViewHolder holder, int position) {

        if (mAssessments != null) {
            final Assessment current = mAssessments.get(position);
            holder.assessmentTitleView.setText(current.getTitle());
            holder.assessmentStartDate.setText(current.getStartDate());
            holder.assessmentEndDate.setText(current.getEndDate());
        } else {
            holder.assessmentTitleView.setText("No Text");
            holder.assessmentStartDate.setText("No Text");
            holder.assessmentEndDate.setText("No Text");

        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        } else return 0;
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}

