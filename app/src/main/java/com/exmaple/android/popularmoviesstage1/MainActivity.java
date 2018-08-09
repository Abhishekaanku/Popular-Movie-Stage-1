package com.exmaple.android.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView gridViewDisplay;
    GridViewAdapter gridViewAdapter;
    GridLayoutManager gridLayoutManager;
    int movie_count=100;
    int columnCount=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridViewDisplay = (RecyclerView)findViewById(R.id.movie_recycler);

        gridLayoutManager=new GridLayoutManager(this,columnCount);

        gridViewAdapter=new GridViewAdapter(movie_count,this);

        gridViewDisplay.setAdapter(gridViewAdapter);

        gridViewDisplay.setLayoutManager(gridLayoutManager);
    }
}
