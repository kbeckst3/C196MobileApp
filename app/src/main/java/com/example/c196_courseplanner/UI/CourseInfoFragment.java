package com.example.c196_courseplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_courseplanner.CourseInfo;
import com.example.c196_courseplanner.CourseNoteDetail;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.R;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A placeholder fragment containing a simple view.
 */
public class CourseInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context context;
    private Spinner courseInstructorSelector;
    private AppRepository appRepository;
    private TextView courseInstructorPhone;
    private TextView courseInstructorEmail;
    private int courseId;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_info_fragment, container, false);

        appRepository = new AppRepository(getApplicationContext());
        CourseInfo courseInfo = (CourseInfo) getActivity();
        assert courseInfo != null;
        courseId = courseInfo.getCourseId();
        String courseTitle = courseInfo.getCourseTitle();
        String courseStatus = courseInfo.getCurrentCourseStatus();
        String courseStartDate = courseInfo.getCurrentCourseStart();
        String courseEndDate = courseInfo.getCurrentCourseEnd();


        courseInstructorSelector = view.findViewById(R.id.courseInstructorDropDown);
        courseInstructorEmail = view.findViewById(R.id.courseInstructorEmailView);
        courseInstructorPhone = view.findViewById(R.id.courseInstructorPhoneView);
        //Populate Course Info Fields and Change Title
        TextView courseTitleInfo = view.findViewById(R.id.courseTitleInfo);
        TextView currentCourseStatus = view.findViewById(R.id.currentCourseStatus);
        TextView currentCourseStart = view.findViewById(R.id.currentCourseStart);
        TextView currentCourseEnd = view.findViewById(R.id.currentCourseEnd);


        courseTitleInfo.setText(courseTitle);
        currentCourseStatus.setText(courseStatus);
        currentCourseStart.setText(courseStartDate);
        currentCourseEnd.setText(courseEndDate);

        //Course Instructor portion
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<CourseInstructor> mInstructorList = appRepository.getAllCourseInstructors();
            mInstructorList.add(0, new CourseInstructor(0, "Please Select ", "", ""));
            ArrayAdapter<CourseInstructor> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.course_instructor_list_item, R.id.courseInstructorListItem, mInstructorList);
            courseInstructorSelector.setAdapter(adapter);
            //If the course already has a course instructor set drop down to that course instructor
            Course currentCourse = appRepository.getCourseById(courseId);
            if (currentCourse.getAssociatedCourseInstructorId() != 0) {
                int arrayPosition = 0;
                for (CourseInstructor courseInstructor : mInstructorList) {
                    if (courseInstructor.getId() == currentCourse.getAssociatedCourseInstructorId()) {
                        courseInstructorSelector.setSelection(arrayPosition);
                        courseInstructorPhone.setText(courseInstructor.getPhoneNumber());
                        courseInstructorEmail.setText(courseInstructor.getEmail());
                        break;
                    } else
                        arrayPosition++;
                }
            }
        });
        //Portion for course note recycler
        executor.execute(() -> {

            List<CourseNote> mCourseNotes = appRepository.getAllCourseNotesByCourseId(courseId);
            RecyclerView recyclerView = view.findViewById(R.id.courseNoteRecyclerView);
            final CourseNoteAdapter adapter = new CourseNoteAdapter(view.getContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            if (mCourseNotes != null) {
                adapter.setCourseNotes(mCourseNotes);

            }
        });
        FloatingActionButton fab = view.findViewById(R.id.courseNoteAdder);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(view.getContext(), CourseNoteDetail.class);
                                       intent.putExtra("associatedCourseId", courseId);
                                       startActivity(intent);
                                   }
                               });
        courseInstructorSelector.setOnItemSelectedListener(this);
        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        CourseInstructor current = (CourseInstructor) parent.getItemAtPosition(pos);
        courseInstructorPhone.setText(current.getPhoneNumber());
        courseInstructorEmail.setText(current.getEmail());
        System.out.println("this just runs when we load the fragment");
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            //Adds courseInstructor to course
            appRepository.addCourseInstructorToCourse(current.getId(), courseId);
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public CourseInfoFragment(Context context) {
        this.context = context;
    }

    public Context getApplicationContext() {
        return context;
    }
}