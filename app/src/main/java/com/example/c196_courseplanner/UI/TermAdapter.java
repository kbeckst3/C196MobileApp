package com.example.c196_courseplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.CourseActivity;
import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.R;
import com.example.c196_courseplanner.TermDetail;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {


    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termTitleView;
        private final TextView termStartDate;
        private final TextView termEndDate;

        private TermViewHolder(View itemView){
            super(itemView);
            termTitleView = itemView.findViewById(R.id.termItemTitle);
            termStartDate = itemView.findViewById(R.id.termItemStart);
            termEndDate = itemView.findViewById(R.id.termItemEnd);
            ImageView termEdit = itemView.findViewById(R.id.editTerm);
            termEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("termId", current.getId());
                    intent.putExtra("termTitle", current.getTitle());
                    intent.putExtra("termStartDate", current.getStartDate());
                    intent.putExtra("termEndDate", current.getEndDate());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("termId", current.getId());
                    intent.putExtra("termTitle", current.getTitle());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private final LayoutInflater mInflater;
    private final Context context;
    private List<Term> mTerms;

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.term_list_item, parent,  false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        if(mTerms != null) {
            final Term current = mTerms.get(position);
            holder.termTitleView.setText(current.getTitle());
            holder.termStartDate.setText(current.getStartDate());
            holder.termEndDate.setText(current.getEndDate());
        } else {
            holder.termTitleView.setText("No Title");
            holder.termStartDate.setText("No Start Date");
            holder.termEndDate.setText("No End Date");
        }
    }

    @Override
    public int getItemCount() {
        if(mTerms != null) {
            return mTerms.size();
        }
        else return 0;
    }
    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

}
