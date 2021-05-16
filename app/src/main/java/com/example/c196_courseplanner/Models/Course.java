package com.example.c196_courseplanner.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "start_date")
    private String startDate;
    @ColumnInfo(name = "end_date")
    private String endDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "associated_course_instructor_id")
    private int associatedCourseInstructorId;
    @ColumnInfo(name = "associated_term_id")
    private int associatedTermId;

    @Ignore
    public Course(String title, String startDate, String endDate, String status, int associatedTermId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.associatedTermId = associatedTermId;
    }

    @Ignore
    public Course(int id, String title, String startDate, String endDate, String status, int associatedTermId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.associatedTermId = associatedTermId;
    }

    public Course(int id, String title, String startDate, String endDate, String status, int associatedCourseInstructorId, int associatedTermId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.associatedCourseInstructorId = associatedCourseInstructorId;
        this.associatedTermId = associatedTermId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAssociatedCourseInstructorId() {
        return associatedCourseInstructorId;
    }

    public void setAssociatedCourseInstructorId(int associatedCourseInstructorId) {
        this.associatedCourseInstructorId = associatedCourseInstructorId;
    }

    public int getAssociatedTermId() {
        return associatedTermId;
    }

    public void setAssociatedTermId(int associatedTermId) {
        this.associatedTermId = associatedTermId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", associatedCourseInstructorId=" + associatedCourseInstructorId +
                ", associatedTermId=" + associatedTermId +
                '}';
    }
}
