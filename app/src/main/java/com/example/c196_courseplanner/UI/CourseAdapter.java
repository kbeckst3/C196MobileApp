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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.CourseDetail;
import com.example.c196_courseplanner.CourseInfo;
import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.R;
import com.example.c196_courseplanner.utilities.MyReceiver;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private static int numAlert;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseTitleView;
        private final TextView courseStartDate;
        private final TextView courseEndDate;

        private long startDate;
        private long endDate;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseTitleView = itemView.findViewById(R.id.courseItemTitle);
            courseStartDate = itemView.findViewById(R.id.courseItemStart);
            courseEndDate = itemView.findViewById(R.id.courseItemEnd);
            ImageView courseEdit = itemView.findViewById(R.id.editCourse);
            ImageView courseAlert = itemView.findViewById(R.id.courseAlert);
            courseEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseId", current.getId());
                    intent.putExtra("courseTitle", current.getTitle());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("courseStatus", current.getStatus());
                    intent.putExtra("currentTermId", current.getAssociatedTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            courseAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);

                    Intent intent = new Intent(context, MyReceiver.class);
                    intent.putExtra("key", String.format("The course %s starts today!", current.getTitle()));
                    PendingIntent sender = PendingIntent.getBroadcast( context, ++numAlert, intent, 0);
                    AlarmManager alarmManager = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
                    LocalDate localDate = LocalDate.parse(current.getStartDate(), dtf);

                    startDate = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);

                    Intent intent1 = new Intent(context, MyReceiver.class);
                    intent1.putExtra("key", String.format("The course %s ends today!", current.getTitle()));
                    PendingIntent sender1 = PendingIntent.getBroadcast(context, ++numAlert, intent1, 0);
                    AlarmManager alarmManager1 = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
                    LocalDate localDate1 = LocalDate.parse(current.getEndDate(), dtf);

                    endDate = localDate1.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    //https://stackoverflow.com/questions/40048236/remove-persistent-notification-with-alarm-manager
                    //To add removing deleting notifications
                    alarmManager1.set(AlarmManager.RTC_WAKEUP, endDate, sender1);
                    Snackbar.make(v, "Notification set for start and end dates", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseInfo.class);
                    intent.putExtra("courseId", current.getId());
                    intent.putExtra("courseTitle", current.getTitle());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("courseStatus", current.getStatus());
                    intent.putExtra("currentTermId", current.getAssociatedTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private final LayoutInflater mInflater;
    private final Context context;
    private List<Course> mCourses;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.course_list_item, parent,  false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {

        if(mCourses != null) {
            final Course current = mCourses.get(position);
            holder.courseTitleView.setText(current.getTitle());
            holder.courseStartDate.setText(current.getStartDate());
            holder.courseEndDate.setText(current.getEndDate());
        } else {
            holder.courseTitleView.setText("No Title");
            holder.courseStartDate.setText("No Start Date");
            holder.courseEndDate.setText("No End Date");
        }
    }

    @Override
    public int getItemCount() {
        if(mCourses != null) {
            return mCourses.size();
        }
        else return 0;
    }
    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

}
