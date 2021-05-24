package com.example.c196_courseplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.c196_courseplanner.Models.Assessment;

import java.util.List;

@Dao
public interface AssessmentEntityDAO {

    @Query("Select * From assessment_table Where id = :id")
    Assessment getAssessmentById(int id);

    @Query("Select * From assessment_table Where associated_course_id = :id")
    List<Assessment> getAllAssessmentsByCourseId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);


    @Query("Delete From assessment_table Where id = :id")
    void deleteAssessmentById(int id);

    @Query("Delete From assessment_table")
    void deleteAllAssessments();
}
