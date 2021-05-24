package com.example.c196_courseplanner;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_courseplanner.Models.Term;
import com.example.c196_courseplanner.UI.TermAdapter;
import com.example.c196_courseplanner.database.AppRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermActivity extends AppCompatActivity {

    private AppRepository appRepository;
    private ArrayList<Term> terms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.termAdder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermActivity.this, TermDetail.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appRepository = new AppRepository(getApplicationContext());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            terms.addAll(appRepository.getAllTerms());
        });

        adapter.setTerms(terms);
    }
}