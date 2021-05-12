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

import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class TermDetail extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mTitle;
    private static EditText mStartDate;
    private static EditText mEndDate;
    private Button mSaveTerm;

    public static DatePickerDialogFragment mDatePickerDialogFragment;
    private AppRepository appRepository;

    //Variable used for editing a term
    private int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        appRepository = new AppRepository(getApplicationContext());

        mTitle = findViewById(R.id.termTitle);
        mStartDate = (EditText) findViewById(R.id.termStartField);
        mEndDate = (EditText) findViewById(R.id.termEndField);
        mSaveTerm = (Button) findViewById(R.id.saveTerm);
        mDatePickerDialogFragment = new DatePickerDialogFragment();
        mStartDate.setOnClickListener((View.OnClickListener) this);
        mEndDate.setOnClickListener((View.OnClickListener) this);
        mSaveTerm.setOnClickListener((View.OnClickListener) this);

        //Grabbing intent Extras for editing Term
        termID = getIntent().getIntExtra("termID", -1);
        String termEditTitle = getIntent().getStringExtra("termTitle");
        String termEditStartDate = getIntent().getStringExtra("termStartDate");
        String termEditEndDate = getIntent().getStringExtra("termEndDate");

        if(termID != -1)
        {
            mTitle.setText(termEditTitle);
            mStartDate.setText(termEditStartDate);
            mEndDate.setText(termEditEndDate);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.termStartField) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.termEndField) {
            mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.saveTerm) {
            Term mTerm;
            if (termID == -1) {
                 mTerm = new Term(Objects.requireNonNull(mTitle.getText()).toString(), mStartDate.getText().toString(), mEndDate.getText().toString());
            } else {
                 mTerm = new Term(termID, Objects.requireNonNull(mTitle.getText()).toString(), mStartDate.getText().toString(), mEndDate.getText().toString());
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
                mStartDate.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                mEndDate.setText(format.format(calendar.getTime()));
            }
        }

    }
}