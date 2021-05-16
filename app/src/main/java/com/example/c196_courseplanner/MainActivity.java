package com.example.c196_courseplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.database.AppRepository;

public class MainActivity extends AppCompatActivity {
    AppRepository appRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CourseInstructor courseInstructor1 = new CourseInstructor("Keller John", "8016417726", "Kellerjbeckstead@gmail.com");
//        CourseInstructor courseInstructor2 = new CourseInstructor("Kurt Keller", "2083217852", "mpa@gmail.com");
//        CourseInstructor courseInstructor3 = new CourseInstructor("Kara Ann", "3854244561", "albeckstead@gmail.com");
//
//        appRepository = new AppRepository(getApplicationContext());
//        appRepository.insertCourseInstructor(courseInstructor1);
//        appRepository.insertCourseInstructor(courseInstructor2);
//        appRepository.insertCourseInstructor(courseInstructor3);

//        appRepository.deleteAllTerms();
    }

    public void authenticateLogin(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);

    }
}