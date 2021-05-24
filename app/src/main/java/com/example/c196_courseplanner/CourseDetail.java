package com.example.c196_courseplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseDetail extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText courseTitleView;
    private TextInputEditText courseTermIdView;
    private  static EditText courseStartDateView;
    private static EditText courseEndDateView;
    private Spinner courseStatusSpinner;

    public static DatePickerDialogFragment mDatePickerDialogFragment;
    private AppRepository appRepository;

    //Id for editing course
    private int courseId;
    private int associatedTermId;

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
        Button deleteButton = findViewById(R.id.deleteCourse);
        courseStatusSpinner = findViewById(R.id.courseStatusSpinner);

        //Date Picker Dialog
        mDatePickerDialogFragment = new DatePickerDialogFragment();

        courseId = getIntent().getIntExtra("courseId", -1);
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseStartDate = getIntent().getStringExtra("courseStartDate");
        String courseEndDate = getIntent().getStringExtra("courseEndDate");
        String courseStatus = getIntent().getStringExtra("courseStatus");
        associatedTermId = getIntent().getIntExtra("currentTermId", -1);
        courseTermIdView.setText(String.valueOf(associatedTermId));

        if(courseId != -1){
            courseTitleView.setText(courseTitle);
            courseStartDateView.setText(courseStartDate);
            courseEndDateView.setText(courseEndDate);
        }
        //Spinner setup
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<String> statusList = Arrays.asList("Please Select", "in progress", "completed", "dropped", "plan to take");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.status_list_item, R.id.statusListItem, statusList);
            courseStatusSpinner.setAdapter(adapter);
            //If the course already has a course instructor set drop down to that course instructor
            if (courseId != -1) {
                int arrayPosition = 0;
                for (String s : statusList) {
                    if (s.equalsIgnoreCase(courseStatus)) {
                        courseStatusSpinner.setSelection(arrayPosition);
                        break;
                    } else
                        arrayPosition++;
                }
            }
        });
        //Set On Click Listeners
        courseStartDateView.setOnClickListener(this);
        courseStartCalendar.setOnClickListener(this);
        courseEndDateView.setOnClickListener(this);
        courseEndCalendar.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

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
                course = new Course(Objects.requireNonNull(courseTitleView.getText()).toString(), courseStartDateView.getText().toString(), courseEndDateView.getText().toString(), courseStatusSpinner.getSelectedItem().toString(), Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            } else {
                course = new Course(courseId, Objects.requireNonNull(courseTitleView.getText()).toString(), courseStartDateView.getText().toString(), courseEndDateView.getText().toString(), courseStatusSpinner.getSelectedItem().toString(), Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            }
            if(!course.getTitle().isEmpty()) {
                appRepository.insertCourse(course);
            }
            Intent intent = new Intent(CourseDetail.this, CourseActivity.class);
            intent.putExtra("termId", Integer.parseInt(Objects.requireNonNull(courseTermIdView.getText()).toString()));
            startActivity(intent);
        }else if (id == R.id.deleteCourse){
            AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetail.this);
            builder.setMessage("Are you sure you want to DELETE the course?\n" +
                    " Deleting the course will delete associated assessments & notes")
                    .setPositiveButton("Delete", (dialog, id1) -> {
                        appRepository.deleteCourseById(courseId);
                        Intent intent = new Intent(CourseDetail.this, CourseActivity.class);
                        intent.putExtra("termId", associatedTermId);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, id12) -> System.out.println("not deleting"));
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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