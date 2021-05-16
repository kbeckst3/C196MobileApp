package com.example.c196_courseplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.CourseActivity;
import com.example.c196_courseplanner.CourseNoteDetail;
import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.R;

import java.util.List;

public class CourseNoteAdapter extends RecyclerView.Adapter<CourseNoteAdapter.CourseNoteViewHolder> {


    class CourseNoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseNoteView;


        private CourseNoteViewHolder(View itemView){
            super(itemView);
            courseNoteView = itemView.findViewById(R.id.courseNoteViewItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseNote current = mCourseNotes.get(position);
                    Intent intent = new Intent(context, CourseNoteDetail.class);
                    intent.putExtra("courseNoteId", current.getId());
                    intent.putExtra("courseNote", current.getNote());
                    intent.putExtra("associatedCourseId", current.getAssociatedCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseNote> mCourseNotes;

    public CourseNoteAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.course_note_list_item, parent,  false);
        return new CourseNoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseNoteAdapter.CourseNoteViewHolder holder, int position) {

        if(mCourseNotes != null) {
            final CourseNote current = mCourseNotes.get(position);
            holder.courseNoteView.setText(current.getNote());
        } else {
            holder.courseNoteView.setText("No Text");

        }
    }

    @Override
    public int getItemCount() {
        if(mCourseNotes != null) {
            return mCourseNotes.size();
        }
        else return 0;
    }
    public void setCourseNotes(List<CourseNote> courseNotes){
        mCourseNotes = courseNotes;
        notifyDataSetChanged();
    }
}
