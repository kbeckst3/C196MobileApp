package com.example.c196_courseplanner.database;

import android.content.Context;

import com.example.c196_courseplanner.DAO.TermEntityDAO;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {

    private final AppDatabase appDatabase;
    private final Executor executor = Executors.newSingleThreadExecutor();


    public AppRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);

    }


    /*
     * Assessment DAO functionality
     */

    /*
     * Course DAO functionality
     */
    public List<Course> getAllCourse() {
        return appDatabase.courseEntityDAO().getAllCourses();
    }

    public List<Course> getAllCourseByTermID(int id) {
        return appDatabase.courseEntityDAO().getAllCoursesByTermId(id);
    }

    public Course getCourseById(int id) {
        return appDatabase.courseEntityDAO().getCourseById(id);
    }

    public void insertCourse(Course course) {
        executor.execute(() -> appDatabase.courseEntityDAO().insertCourse(course));
    }

    public void deleteCourse(Course course) {
        executor.execute(() -> appDatabase.courseEntityDAO().deleteCourse(course));
    }

    public void deleteAllCourses() {
        executor.execute(() -> appDatabase.courseEntityDAO().deleteAllCourses());
    }

    /*
     * Course Instructor DAO functionality
     */

    /*
     * Course Note DAO functionality
     */

    /*
     * Term DAO functionality
     */
    public List<Term> getAllTerms() {
        return appDatabase.termEntityDAO().getAllTerms();
    }

    public Term getTermById(int id) {
        return appDatabase.termEntityDAO().getTermById(id);
    }

    public void insertTerm(Term term) {
        executor.execute(() -> appDatabase.termEntityDAO().insertTerm(term));
    }

    public void deleteTerm(Term term) {
        executor.execute(() -> appDatabase.termEntityDAO().deleteTerm(term));
    }

    public void deleteAllTerms() {
        executor.execute(() -> appDatabase.termEntityDAO().deleteAllTerms());
    }

}
