package com.example.c196_courseplanner.UI;

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
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseTitleView;
        private final TextView courseStartDate;
        private final TextView courseEndDate;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseTitleView = itemView.findViewById(R.id.courseItemTitle);
            courseStartDate = itemView.findViewById(R.id.courseItemStart);
            courseEndDate = itemView.findViewById(R.id.courseItemEnd);
            ImageView courseEdit = itemView.findViewById(R.id.editCourse);
            courseEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseID", current.getId());
                    intent.putExtra("courseTitle", current.getTitle());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("currentTermId", current.getAssociatedTermId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseInfo.class);
                    intent.putExtra("courseID", current.getId());
                    intent.putExtra("courseTitle", current.getTitle());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
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
