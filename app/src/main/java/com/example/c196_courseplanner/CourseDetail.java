package com.example.c196_courseplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CourseDetail extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText courseTitleView;
    private TextInputEditText courseTermIdView;
    private  static EditText courseStartDateView;
    private static EditText courseEndDateView;

    public static DatePickerDialogFragment mDatePickerDialogFragment;
    private AppRepository appRepository;

    //Id for editing course
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        appRepository = new AppRepository(getApplicationContext());

        //Attach variables to actual inputs
        courseTitleView = findViewById(R.id.courseTitle);
        courseTermIdView = findViewById(R.id.associatedTermId);
        courseStartDateView = findViewById(R.id.courseStartField);
        courseEndDateView = findViewById(R.id.courseEndField);
        ImageView courseStartCalendar = findViewById(R.id.courseStartCalendar);
        ImageView courseEndCalendar = findViewById(R.id.courseEndCalendar);
        Button saveButton = findViewById(R.id.saveCourse);

        //Date Picker Dialog
        mDatePickerDialogFragment = new DatePickerDialogFragment();

        int courseId = getIntent().getIntExtra("courseID", -1);
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseStartDate = getIntent().getStringExtra("courseStartDate");
        System.out.println("Course Start: " +courseStartDate);
        String courseEndDate = getIntent().getStringExtra("courseEndDate");
        System.out.println("Course End: " +courseEndDate);
        int associatedTermId = getIntent().getIntExtra("currentTermId", -1);
        courseTermIdView.setText(String.valueOf(associatedTermId));

        if(courseId != -1){
            courseTitleView.setText(courseTitle);
            courseStartDateView.setText(courseStartDate);
            courseEndDateView.setText(courseEndDate);
        }
        //Set On Click Listeners
        courseStartDateView.setOnClickListener(this);
        courseStartCalendar.setOnClickListener(this);
        courseEndDateView.setOnClickListener(this);
        courseEndCalendar.setOnClickListener(this);
        saveButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.courseStartField || id == R.id.courseStartCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.courseEndField || id == R.id.courseEndCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        }else if (id == R.id.saveCourse) {
            Course course;
            if (courseId == -1) {
                course = new Course(Objects.requireNonNull(courseTitleView.getText()).toString(), courseStartDateView.getText().toString(), courseEndDateView.getText().toString(), Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            } else {
                course = new Course(courseId, Objects.requireNonNull(courseTitleView.getText()).toString(), courseStartDateView.getText().toString(), courseEndDateView.getText().toString(), Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            }
            if(!course.getTitle().isEmpty()) {
                appRepository.insertCourse(course);
            }
            Intent intent = new Intent(CourseDetail.this, CourseActivity.class);
            intent.putExtra("termID", Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            startActivity(intent);
        }
    }

    public static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (flag == FLAG_START_DATE) {
                courseStartDateView.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                courseEndDateView.setText(format.format(calendar.getTime()));
            }
        }

    }
}