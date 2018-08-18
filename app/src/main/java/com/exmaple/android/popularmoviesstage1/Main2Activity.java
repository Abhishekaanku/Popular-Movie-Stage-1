package com.exmaple.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    public final static String POSITION="POSITION";

    private final static int DEF_POS = -1;

    private String[] months=null;
    private ImageView mImagePosterView;
    private TextView mSynopsisView;
    private TextView mUserRatingView;
    private TextView mReleaseDateView;
    private TextView mOriginalTitle;
    private Movie movie;
    private TextView mMovieTitleView;
    private Button mClickNext;
    private int position;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle(R.string.MovieDetail);

        mImagePosterView= findViewById(R.id.posterView);
        mOriginalTitle= findViewById(R.id.originaltitle);
        mReleaseDateView= findViewById(R.id.ReleaseDate);
        mSynopsisView= findViewById(R.id.plotText);
        mUserRatingView= findViewById(R.id.user_rating);
        mMovieTitleView=findViewById(R.id.titleView);
        mClickNext=findViewById(R.id.clickNext);

        Intent intent=getIntent();

        if(savedInstanceState!=null && savedInstanceState.containsKey("Months")) {
            months=savedInstanceState.getStringArray("Months");
        }
        else {
            months=MainActivity.getContext().getResources().getStringArray(R.array.Months);
        }

        if(savedInstanceState!=null && savedInstanceState.containsKey("POS")) {
            position=savedInstanceState.getInt("POS");
        }
        else {
            if(intent.hasExtra(POSITION)) {
                position=intent.getIntExtra(POSITION,DEF_POS);
            }
        }

        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            movieList=intent.getParcelableArrayListExtra(Intent.EXTRA_TEXT);
        }

        movie=movieList.get(position);
        populateLayout();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("Months",months);
        outState.putInt("POS",position);
    }

    private void populateLayout() {
        Picasso.with(this)
                .load(movie.getPoster())
                .placeholder(R.drawable.imageplaceholder)
                .into(mImagePosterView);

        String userRating=Float.toString(movie.getUser_rating())
                + getString(R.string.fullscale);
        mUserRatingView.setText(userRating);

        String synopsis=getString(R.string.plot)
                +movie.getOverView();
        mSynopsisView.setText(synopsis);

        mReleaseDateView.setText(parseDate(movie.getReleaseDate()));

        mOriginalTitle.setText(movie.getOriginalTitle());

        mMovieTitleView.setText(movie.getTitle());

        mClickNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPosition=(1+position)%movieList.size();
                position=nextPosition;
                movie=movieList.get(nextPosition);
                populateLayout();
            }
        });
    }

    private String parseDate(String rawDate) {
        String date="";
        date+=rawDate.substring(8)+" ";
        date+=months[Integer.parseInt(rawDate.substring(5,7))-1]+" ";
        date+=rawDate.substring(0,4);
        return date;
    }
}
