package com.example.c196_courseplanner.database;

import android.content.Context;

import com.example.c196_courseplanner.DAO.TermEntityDAO;
import com.example.c196_courseplanner.Models.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository instance;

    //lists of objects
    public List<Term> termsList;

    private AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();


    public AppRepository(Context context) {
        appDatabase = AppDatabase.getInstance(context);
//        termsList = getAllTerms();

    }

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    public List<Term> getAllTerms(){
        return appDatabase.termEntityDAO().getAllTerms();
    }

    public Term getTermById(int id){
        return appDatabase.termEntityDAO().getTermById(id);
    }

    public void insertTerm(Term term){
        executor.execute(() -> appDatabase.termEntityDAO().insertTerm(term));
    }
    public void deleteTerm(Term term){
        executor.execute(() -> appDatabase.termEntityDAO().deleteTerm(term));
    }
    public void deleteAllTerms(){
        executor.execute(() -> appDatabase.termEntityDAO().deleteAllTerms());
    }
}
