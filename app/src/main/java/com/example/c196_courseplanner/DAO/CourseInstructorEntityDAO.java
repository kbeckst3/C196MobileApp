package com.example.c196_courseplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196_courseplanner.Models.CourseInstructor;

import java.util.List;

@Dao
public interface CourseInstructorEntityDAO {

    @Query("Select * from course_instructor_table Order By id Asc")
    List<CourseInstructor> getAllCourseInstructors();

    @Query("Select * From course_instructor_table Where id = :id")
    CourseInstructor getCourseInstructorById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseInstructor(CourseInstructor courseInstructor);

    @Delete
    void deleteCourseInstructor(CourseInstructor courseInstructor);

    @Query("Delete From course_instructor_table")
    void deleteAllCoursesInstructors();
}
