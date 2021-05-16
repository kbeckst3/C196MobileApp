package com.example.c196_courseplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.AssessmentDetail;
import com.example.c196_courseplanner.CourseInfo;
import com.example.c196_courseplanner.CourseNoteDetail;
import com.example.c196_courseplanner.Models.Assessment;

import com.example.c196_courseplanner.R;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentsFragment extends Fragment {

    private Context context;
    private AppRepository appRepository;
    private int courseId;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assessments_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.assessmentsRecyclerView);
        appRepository = new AppRepository(getApplicationContext());
        CourseInfo courseInfo = (CourseInfo) getActivity();
        assert courseInfo != null;
        courseId = courseInfo.getCourseId();


        //Assessment adapter portion
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            List<Assessment> mAssessments = appRepository.getAllAssessmentsByCourse(courseId);
            final AssessmentsAdapter adapter = new AssessmentsAdapter(view.getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            if (mAssessments != null) {
                adapter.setAssessments(mAssessments);

            }
        });
        FloatingActionButton fab = view.findViewById(R.id.assessmentAdder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AssessmentDetail.class);
                intent.putExtra("associatedCourseId", courseId);
                startActivity(intent);
            }
        });
        return view;

    }

    public AssessmentsFragment(Context context) {
        this.context = context;
    }

    public Context getApplicationContext() {
        return context;
    }
}