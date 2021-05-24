package com.example.c196_courseplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_courseplanner.DAO.AssessmentEntityDAO;
import com.example.c196_courseplanner.DAO.CourseEntityDAO;
import com.example.c196_courseplanner.DAO.CourseInstructorEntityDAO;
import com.example.c196_courseplanner.DAO.CourseNoteEntityDAO;
import com.example.c196_courseplanner.DAO.TermEntityDAO;
import com.example.c196_courseplanner.Models.Assessment;
import com.example.c196_courseplanner.Models.Course;
import com.example.c196_courseplanner.Models.CourseInstructor;
import com.example.c196_courseplanner.Models.CourseNote;
import com.example.c196_courseplanner.Models.Term;


@Database(entities = {Assessment.class, Course.class, CourseInstructor.class, CourseNote.class, Term.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    //Program DAOs
    public abstract AssessmentEntityDAO assessmentEntityDAO();
    public abstract CourseEntityDAO courseEntityDAO();
    public abstract CourseInstructorEntityDAO courseInstructorEntityDAO();
    public abstract CourseNoteEntityDAO courseNoteEntityDAO();
    public abstract TermEntityDAO termEntityDAO();

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "CoursePlanner.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }
}