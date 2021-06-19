package com.example.c196_courseplanner.utilities;

import android.content.Context;

import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.database.AppRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SampleDateLoader {

    private static final String SAMPLE_TERM_TITLE = "Sample Term";
    private static final String SAMPLE_COURSE_TITLE = "Sample Course";
    private static final String SAMPLE_ASSESSMENT_TITLE = "Sample Assessment";
    private static final String COURSE_NOTE_CONTENT = "Sample Course Note";
    AppRepository appRepository;

    public void createSampleData(Context context){
        appRepository = new AppRepository(context);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->{
            //Clean previous data
            appRepository.deleteAllTerms();
            appRepository.deleteAllCourses();
            appRepository.deleteAllAssessments();
            appRepository.deleteAllCourseNotes();
            appRepository.deleteAllCoursesInstructors();

            //Add Sample Terms
            appRepository.insertTerm(new Term(1, SAMPLE_TERM_TITLE, "2021-05-23", "2021-05-30"));
            appRepository.insertTerm(new Term(2, SAMPLE_TERM_TITLE, "2021-05-23", "2021-05-30"));
            appRepository.insertTerm(new Term(3, SAMPLE_TERM_TITLE, "2021-05-23", "2021-05-30"));

            //Add Sample Courses
            appRepository.insertCourse(new Course(1, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 1));
            appRepository.insertCourse(new Course(2, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 1));
            appRepository.insertCourse(new Course(3, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 2));
            appRepository.insertCourse(new Course(4, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 2));
            appRepository.insertCourse(new Course(5, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 3));
            appRepository.insertCourse(new Course(6, SAMPLE_COURSE_TITLE, "2021-05-23", "2021-05-30", "in progress", 1, 3));

            //Add Sample Assessments
            appRepository.insertAssessment(new Assessment(1, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Performance Assessment", 1));
            appRepository.insertAssessment(new Assessment(2, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Performance Assessment", 2));
            appRepository.insertAssessment(new Assessment(3, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Performance Assessment", 3));
            appRepository.insertAssessment(new Assessment(4, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Objective Assessment", 5));
            appRepository.insertAssessment(new Assessment(5, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Objective Assessment", 4));
            appRepository.insertAssessment(new Assessment(6, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Objective Assessment", 6));
            appRepository.insertAssessment(new Assessment(7, SAMPLE_ASSESSMENT_TITLE, "2021-05-23", "2021-05-30", "This is an assessment", "Performance Assessment", 6));

            //Add Sample Course Notes
            appRepository.insertCourseNote(new CourseNote(1, COURSE_NOTE_CONTENT, 1));
            appRepository.insertCourseNote(new CourseNote(2, COURSE_NOTE_CONTENT, 2));
            appRepository.insertCourseNote(new CourseNote(3, COURSE_NOTE_CONTENT, 3));
            appRepository.insertCourseNote(new CourseNote(4, COURSE_NOTE_CONTENT, 4));
            appRepository.insertCourseNote(new CourseNote(5, COURSE_NOTE_CONTENT, 5));
            appRepository.insertCourseNote(new CourseNote(6, COURSE_NOTE_CONTENT, 6));

            //Add Course Instructor
            appRepository.insertCourseInstructor(new CourseInstructor(1, "Stacy Smith", "321-213-4561", "ssmith@wgu.edu"));
            appRepository.insertCourseInstructor(new CourseInstructor(2, "Mike Lowes", "912-543-9012", "mlowes@wgu.edu"));
            appRepository.insertCourseInstructor(new CourseInstructor(3, "Amelia McDonald", "702-234-7095", "amcdonald@wgu.edu"));
        });
    }

}
