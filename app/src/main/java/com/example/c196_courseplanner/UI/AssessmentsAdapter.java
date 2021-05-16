package com.example.c196_courseplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.AssessmentDetail;
import com.example.c196_courseplanner.CourseInfo;
import com.example.c196_courseplanner.CourseNoteDetail;
import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.R;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentStartDate;
        private final TextView assessmentEndDate;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentTitleView = itemView.findViewById(R.id.assessmentItemTitle);
            assessmentStartDate = itemView.findViewById(R.id.assessmentItemStart);
            assessmentEndDate = itemView.findViewById(R.id.assessmentItemEnd);
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

