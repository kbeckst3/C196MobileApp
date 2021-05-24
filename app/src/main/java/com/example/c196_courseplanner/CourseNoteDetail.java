package com.example.c196_courseplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.database.AppRepository;

public class CourseNoteDetail extends AppCompatActivity {

    private int courseNoteId;
    private EditText courseNoteView;
    private int courseId;

    private AppRepository appRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_note_detail);

        courseNoteId = getIntent().getIntExtra("courseNoteId", -1);
        String courseNote = getIntent().getStringExtra("courseNote");
        courseId = getIntent().getIntExtra("associatedCourseId", -1);
        appRepository = new AppRepository(getApplicationContext());

        courseNoteView = findViewById(R.id.courseNoteEditView);
        System.out.println("Course Note Id: " + courseNoteId);
        System.out.println("Course Note : " + courseNote);

        if(courseNoteId != -1){
            courseNoteView.setText(courseNote);
        }

    }

    public void saveCourseNote(View view) {
        Intent intent = new Intent(CourseNoteDetail.this, CourseInfo.class);
        intent.putExtra("courseId", courseId);

        CourseNote courseNote;
        if(courseNoteId == -1){
            courseNote = new CourseNote(courseNoteView.getText().toString(), courseId);
        }else{
            courseNote = new CourseNote(courseNoteId, courseNoteView.getText().toString(), courseId);
        }
        appRepository.insertCourseNote(courseNote);
        startActivity(intent);

    }
    public void deleteCourseNote(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(CourseNoteDetail.this);
        builder.setMessage("Are you sure you want to DELETE this course note?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        appRepository.deleteCourseNoteById(courseNoteId);
                        Intent intent = new Intent(CourseNoteDetail.this, CourseInfo.class);
                        intent.putExtra("courseId", courseId);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("not deleting");
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void shareCourseNote(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, courseNoteView.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE,  "Course Note");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Sharing Course Note");
        startActivity(shareIntent);
    }
}