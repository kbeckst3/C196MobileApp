package com.example.c196_courseplanner.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_note_table")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "associated_course_id")
    private int associatedCourseID;

    @Ignore
    public CourseNote(String note, int associatedCourseID) {
        this.note = note;
        this.associatedCourseID = associatedCourseID;
    }

    public CourseNote(int id, String note, int associatedCourseID) {
        this.id = id;
        this.note = note;
        this.associatedCourseID = associatedCourseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAssociatedCourseID() {
        return associatedCourseID;
    }

    public void setAssociatedCourseID(int associatedCourseID) {
        this.associatedCourseID = associatedCourseID;
    }
}
