package com.example.c196_courseplanner;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.UI.CourseAdapter;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseActivity extends AppCompatActivity {

    private AppRepository appRepository;
    private int currentTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //Get Intent extras
        currentTermId = getIntent().getIntExtra("termId", -1);

        FloatingActionButton fab = findViewById(R.id.courseAdder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, CourseDetail.class);
                intent.putExtra("currentTermId", currentTermId);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        appRepository = new AppRepository(getApplicationContext());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Course> mCourses = appRepository.getAllCourseByTermID(currentTermId);
            RecyclerView recyclerView = findViewById(R.id.courseRecycleView);
            final CourseAdapter adapter = new CourseAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (mCourses != null)
                adapter.setCourses(appRepository.getAllCourseByTermID(currentTermId));

        });
    }
}