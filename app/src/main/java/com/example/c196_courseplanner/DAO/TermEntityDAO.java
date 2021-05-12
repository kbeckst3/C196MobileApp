package com.example.c196_courseplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196_courseplanner.Models.Term;

import java.util.List;

@Dao
public interface TermEntityDAO {

    @Query("Select * from term_table Order By id Asc")
    List<Term> getAllTerms();

    @Query("Select * From term_table Where id = :id")
    Term getTermById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("Delete From term_table")
    void deleteAllTerms();
}
