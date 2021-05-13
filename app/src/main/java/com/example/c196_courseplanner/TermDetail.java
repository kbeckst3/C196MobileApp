package com.example.c196_courseplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
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

import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class TermDetail extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText termTitleView;
    @SuppressLint("StaticFieldLeak")
    private static EditText termStartDateView;
    @SuppressLint("StaticFieldLeak")
    private static EditText termEditDateView;

    public static DatePickerDialogFragment mDatePickerDialogFragment;
    private AppRepository appRepository;

    //Variable used for editing a term
    private int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        appRepository = new AppRepository(getApplicationContext());

        termTitleView = findViewById(R.id.termTitle);
        termStartDateView = findViewById(R.id.termStartField);
        termEditDateView = findViewById(R.id.termEndField);
        ImageView termStartCalendar = findViewById(R.id.termStartCalendar);
        ImageView termEndCalendar = findViewById(R.id.termEndCalendar);
        Button termSaveButton = findViewById(R.id.saveTerm);
        mDatePickerDialogFragment = new DatePickerDialogFragment();
        //Set On Click Listeners
        termStartDateView.setOnClickListener(this);
        termStartCalendar.setOnClickListener(this);
        termEditDateView.setOnClickListener(this);
        termEndCalendar.setOnClickListener(this);
        termSaveButton.setOnClickListener(this);

        //Grabbing intent Extras for editing Term
        termID = getIntent().getIntExtra("termID", -1);
        String termTitle = getIntent().getStringExtra("termTitle");
        String termStartDate = getIntent().getStringExtra("termStartDate");
        String termEndDate = getIntent().getStringExtra("termEndDate");

        if(termID != -1)
        {
            termTitleView.setText(termTitle);
            termStartDateView.setText(termStartDate);
            termEditDateView.setText(termEndDate);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.termStartField || id == R.id.termStartCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.termEndField || id == R.id.termEndCalendar) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.saveTerm) {
            Term mTerm;
            if (termID == -1) {
                 mTerm = new Term(Objects.requireNonNull(termTitleView.getText()).toString(), termStartDateView.getText().toString(), termEditDateView.getText().toString());
            } else {
                 mTerm = new Term(termID, Objects.requireNonNull(termTitleView.getText()).toString(), termStartDateView.getText().toString(), termEditDateView.getText().toString());
            }
            if(!mTerm.getTitle().isEmpty()) {
                appRepository.insertTerm(mTerm);
            }
            Intent intent = new Intent(TermDetail.this, TermActivity.class);
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
                termStartDateView.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                termEditDateView.setText(format.format(calendar.getTime()));
            }
        }

    }
}