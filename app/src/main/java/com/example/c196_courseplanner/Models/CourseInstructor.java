package com.example.c196_courseplanner.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_instructor_table")
public class CourseInstructor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "full_name")
    private String fullName;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "email")
    private String email;

}
