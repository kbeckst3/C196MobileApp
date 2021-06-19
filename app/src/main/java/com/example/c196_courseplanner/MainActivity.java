package com.example.c196_courseplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.database.AppRepository;
import com.example.c196_courseplanner.utilities.SampleDateLoader;

public class MainActivity extends AppCompatActivity {
    AppRepository appRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SampleDateLoader sampleDateLoader = new SampleDateLoader();
        sampleDateLoader.createSampleData(getApplicationContext());
    }

    public void authenticateLogin(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);

    }
}