package com.example.c196_courseplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196_courseplanner.database.AppRepository;

public class MainActivity extends AppCompatActivity {
    AppRepository appRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        appRepository.deleteAllTerms();
    }

    public void authenticateLogin(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);

    }
}