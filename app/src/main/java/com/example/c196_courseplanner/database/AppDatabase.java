package com.example.c196_courseplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_courseplanner.DAO.TermEntityDAO;
import com.example.c196_courseplanner.Models.Term;


@Database(entities = {Term.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //Program DAOs
    public abstract TermEntityDAO termEntityDAO();

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "CoursePlanner_db";
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