package com.example.c196_courseplanner.database;

import android.content.Context;

import com.example.c196_courseplanner.DAO.TermEntityDAO;
import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.Models.CourseNote;
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
    public List<Assessment> getAllAssessments() {
        return appDatabase.assessmentEntityDAO().getAllAssessments();
    }

    public List<Assessment> getAllAssessmentsByCourse(int id) {
        return appDatabase.assessmentEntityDAO().getAllAssessmentsByCourseId(id);
    }

    public Assessment getAssessmentById(int id){
       return appDatabase.assessmentEntityDAO().getAssessmentById(id);
    }

    public void insertAssessment(Assessment assessment) {
        executor.execute(() -> appDatabase.assessmentEntityDAO().insertAssessment(assessment));
    }

    public void deleteAssessment(Assessment assessment) {
        executor.execute(() -> appDatabase.assessmentEntityDAO().deleteAssessment(assessment));
    }

    public void deleteAllAssessments() {
        executor.execute(() -> appDatabase.assessmentEntityDAO().deleteAllAssessments());
    }

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

    public void addCourseInstructorToCourse(int courseInstructorId, int courseId){
        executor.execute(() -> appDatabase.courseEntityDAO().addCourseInstructorToCourse(courseInstructorId, courseId));
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
    public List<CourseInstructor> getAllCourseInstructors() {
        return appDatabase.courseInstructorEntityDAO().getAllCourseInstructors();
    }

    public CourseInstructor getCourseInstructorById(int id) {
        return appDatabase.courseInstructorEntityDAO().getCourseInstructorById(id);
    }

    public void insertCourseInstructor(CourseInstructor courseInstructor) {
        executor.execute(() -> appDatabase.courseInstructorEntityDAO().insertCourseInstructor(courseInstructor));
    }

    public void deleteCourseInstructor(CourseInstructor courseInstructor) {
        executor.execute(() -> appDatabase.courseInstructorEntityDAO().deleteCourseInstructor(courseInstructor));
    }

    public void deleteAllCoursesInstructors() {
        executor.execute(() -> appDatabase.courseInstructorEntityDAO().deleteAllCoursesInstructors());
    }
    /*
     * Course Note DAO functionality
     */

    public List<CourseNote> getAllCourseNotes() {
        return appDatabase.courseNoteEntityDAO().getAllCourseNotes();
    }

    public CourseNote getCourseNoteById(int id) {
        return appDatabase.courseNoteEntityDAO().getCourseNoteById(id);
    }

    public List<CourseNote> getAllCourseNotesByCourseId(int courseId) {
        return appDatabase.courseNoteEntityDAO().getAllCourseNotesByCourseId(courseId);
    }

    public void insertCourseNote(CourseNote courseNote) {
        executor.execute(() -> appDatabase.courseNoteEntityDAO().insertCourseNote(courseNote));
    }

    public void deleteCourseNote(CourseNote courseNote) {
        executor.execute(() -> appDatabase.courseNoteEntityDAO().deleteCourseNote(courseNote));
    }

    public void deleteAllCourseNotes(){
        executor.execute(() -> appDatabase.courseNoteEntityDAO().deleteAllCoursesNotes());
    }

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
