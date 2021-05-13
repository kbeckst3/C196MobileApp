package com.example.c196_courseplanner.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "start_date")
    private String startDate;
    @ColumnInfo(name = "end_date")
    private String endDate;
    @ColumnInfo(name = "info")
    private String info;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "associated_course_id")
    private int associatedCourseID;

    @Ignore
    public Assessment(String title, String startDate, String endDate, String info, String type, int associatedCourseID) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.type = type;
        this.associatedCourseID = associatedCourseID;
    }

    public Assessment(int id, String title, String startDate, String endDate, String info, String type, int associatedCourseID) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.type = type;
        this.associatedCourseID = associatedCourseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAssociatedCourseID() {
        return associatedCourseID;
    }

    public void setAssociatedCourseID(int associatedCourseID) {
        this.associatedCourseID = associatedCourseID;
    }
}
