package com.example.c196_courseplanner;

import android.os.Bundle;

import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Spinner;

import com.example.c196_courseplanner.UI.SectionsPagerAdapter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseInfo extends AppCompatActivity {

    private Spinner courseInstructorSelector;
    private AppRepository appRepository;
    private int courseId;
    private String courseTitle;
    private String currentCourseStatus;
    private String currentCourseStart;
    private String currentCourseEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        appRepository = new AppRepository(getApplicationContext());
        courseId = getIntent().getIntExtra("courseId", -1);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Course course = appRepository.getCourseById(courseId);

            courseTitle = course.getTitle();
            currentCourseStatus = course.getStatus();
            currentCourseStart = course.getStartDate();
            currentCourseEnd = course.getEndDate();

        });

    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCurrentCourseStatus() {
        return currentCourseStatus;
    }

    public String getCurrentCourseStart() {
        return currentCourseStart;
    }

    public String getCurrentCourseEnd() {
        return currentCourseEnd;
    }
}