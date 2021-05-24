package com.example.c196_courseplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.c196_courseplanner.Models.Course;

import java.util.List;

@Dao
public interface CourseEntityDAO {
    @Query("Select * from course_table Order By id Asc")
    List<Course> getAllCourses();

    @Query("Select * From course_table Where id = :id")
    Course getCourseById(int id);

    @Query("Select * From course_table Where associated_term_id = :id")
    List<Course> getAllCoursesByTermId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Query("Delete From course_table Where id = :id ")
    void deleteCourseById(int id);

    @Query("Delete From course_table")
    void deleteAllCourses();

    @Query("Update course_table Set associated_course_instructor_id = :instructorId Where id = :id ")
    void addCourseInstructorToCourse(int instructorId, int id);
}
