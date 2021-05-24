package com.example.c196_courseplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196_courseplanner.Models.CourseNote;

import java.util.List;

@Dao
public interface CourseNoteEntityDAO {
    @Query("Select * from course_note_table Order By id Asc")
    List<CourseNote> getAllCourseNotes();

    @Query("Select * From course_note_table Where id = :id")
    CourseNote getCourseNoteById(int id);

    @Query("Select * From course_note_table Where associated_course_id = :id")
    List<CourseNote> getAllCourseNotesByCourseId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourseNote(CourseNote courseNote);

    @Delete
    void deleteCourseNote(CourseNote courseNote);

    @Query("Delete From course_note_table Where id = :id")
    void deleteCourseNoteById(int id);

    @Query("Delete From course_note_table")
    void deleteAllCoursesNotes();
}
