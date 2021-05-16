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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentDetail extends AppCompatActivity implements View.OnClickListener {

    private int assessmentId;
    private TextInputEditText assessmentTitleView;
    private EditText assessmentInfoView;
    private static EditText assessmentStartDateView;
    private static EditText assessmentEndDateView;
    private Spinner assessmentTypeSpinner;

    public static DatePickerDialogFragment mDatePickerDialogFragment;
    private AppRepository appRepository;

    //Id for editing course
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        appRepository = new AppRepository(getApplicationContext());

        //Attach variables to actual inputs
        assessmentTitleView = findViewById(R.id.assessmentTitle);
        assessmentInfoView = findViewById(R.id.assessmentDetail);
        assessmentStartDateView = findViewById(R.id.assessmentStartField);
        assessmentEndDateView = findViewById(R.id.assessmentEndField);
        ImageView assessmentStartCalendar = findViewById(R.id.assessmentStartCalendar);
        ImageView assessmentEndCalendar = findViewById(R.id.assessmentEndCalendar);
        Button saveButton = findViewById(R.id.saveAssessment);
        assessmentTypeSpinner = findViewById(R.id.assessmentTypeSpinner);

        //Date Picker Dialog
        mDatePickerDialogFragment = new DatePickerDialogFragment();

        courseId = getIntent().getIntExtra("associatedCourseId", -1);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        String assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        String assessmentStartDate = getIntent().getStringExtra("assessmentStart");
        String assessmentEndDate = getIntent().getStringExtra("assessmentEnd");
        String assessmentType = getIntent().getStringExtra("assessmentType");
        String assessmentInfo = getIntent().getStringExtra("assessmentInfo");

        if (assessmentId != -1) {
            assessmentTitleView.setText(assessmentTitle);
            assessmentStartDateView.setText(assessmentStartDate);
            assessmentEndDateView.setText(assessmentEndDate);
            assessmentInfoView.setText(assessmentInfo);

        }
        //Spinner setup
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<String> assessmentTypeLIst = Arrays.asList("Please Select", "Performance Assessment", "Objective Assessment");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.assessment_type_list_item, R.id.assessmentTypListItem, assessmentTypeLIst);
            assessmentTypeSpinner.setAdapter(adapter);
            //If the course already has a course instructor set drop down to that course instructor
            if (assessmentId != -1) {
                int arrayPosition = 0;
                for (String s : assessmentTypeLIst) {
                    if (s.equalsIgnoreCase(assessmentType)) {
                        assessmentTypeSpinner.setSelection(arrayPosition);
                        break;
                    } else
                        arrayPosition++;
                }
            }
        });
        //Set On Click Listeners
        assessmentStartDateView.setOnClickListener(this);
        assessmentStartCalendar.setOnClickListener(this);
        assessmentEndDateView.setOnClickListener(this);
        assessmentEndCalendar.setOnClickListener(this);
        saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.assessmentStartField || id == R.id.assessmentStartCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.assessmentEndField || id == R.id.assessmentEndCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.saveAssessment) {
            Assessment assessment;
            if (assessmentId == -1) {
                assessment = new Assessment(assessmentTitleView.getText().toString(), assessmentStartDateView.getText().toString(), assessmentEndDateView.getText().toString(), assessmentInfoView.getText().toString(), assessmentTypeSpinner.getSelectedItem().toString(), courseId);
            } else {
                assessment = new Assessment(assessmentId, assessmentTitleView.getText().toString(), assessmentStartDateView.getText().toString(), assessmentEndDateView.getText().toString(), assessmentInfoView.getText().toString(), assessmentTypeSpinner.getSelectedItem().toString(), courseId);
            }
            if (!assessment.getTitle().isEmpty()) {
                appRepository.insertAssessment(assessment);
            }
            Intent intent = new Intent(AssessmentDetail.this, CourseInfo.class);
            intent.putExtra("courseId", courseId);
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
                assessmentStartDateView.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                assessmentEndDateView.setText(format.format(calendar.getTime()));
            }
        }

    }
}
