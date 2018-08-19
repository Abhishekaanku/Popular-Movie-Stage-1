package com.exmaple.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.exmaple.android.popularmoviesstage1.Utility.InternetUtils;
import com.exmaple.android.popularmoviesstage1.Utility.MovieAPI;


public class MainActivity extends AppCompatActivity implements InternetUtils.URLGenerator{

    private GridView movieGridView;
    private ArrayList<Movie> movieList;
    private ProgressBar mProgressBar;
    private TextView mErrorMessageView;
    private MovieAdapter movieAdapter;
    private ImageView mErrorImageView;
    private MenuItem mViewByPopularity;
    private MenuItem mViewByRating;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGridView =  findViewById(R.id.tv_list_movie);
        mProgressBar = findViewById(R.id.progress_bar);
        mErrorMessageView =  findViewById(R.id.tv_error_content);
        mErrorImageView =  findViewById(R.id.error_image);
        mContext=this;

        Picasso.with(this)
                .load(R.drawable.interneterror)
                .into(mErrorImageView);

       if(isConnectedtoInternet()) {
           BackGroundTask backGroundTask=new BackGroundTask();

           if(savedInstanceState!=null && savedInstanceState.containsKey("movieList")) {
               movieList=savedInstanceState.getParcelableArrayList("movieList");
               backGroundTask.onPostExecute(true);
           }
           else {
               backGroundTask.execute();
           }
       }
       else {
           showErrorMessage();
       }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movieList",movieList);
        super.onSaveInstanceState(outState);
    }
    public static Context getContext() {
        return mContext;
    }

    private void showMovieGridView() {
        movieGridView.setVisibility(View.VISIBLE);
        mErrorMessageView.setVisibility(View.INVISIBLE);
        mErrorImageView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        movieGridView.setVisibility(View.INVISIBLE);
        mErrorMessageView.setVisibility(View.VISIBLE);
        mErrorImageView.setVisibility(View.VISIBLE);
    }

    private void resetScreen() {
        if(movieList!=null) {
            movieList.clear();
        }
        if(movieAdapter!=null) {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private boolean isConnectedtoInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    @Override
    public String fetchURL() {
        try {
            if(mViewByPopularity.isChecked()) {
                return getString(R.string.baseAPIURL1);
            }
            else {
                return getString(R.string.baseAPIURL2);
            }
        }
        //For starting case sort by popularity is default checked
        catch(NullPointerException e) {
            return getString(R.string.baseAPIURL1);
        }
    }


    class BackGroundTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void...params) {
            String response;
            try {
                response= InternetUtils.get_Response(
                        InternetUtils.getURL(MainActivity.this));
                if(response!=null) {
                    movieList=MovieAPI.getMovies(response);
                    return true;
                }
            }
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            super.onPostExecute(status);
            mProgressBar.setVisibility(View.INVISIBLE);
            if(status==null) {
                showErrorMessage();
                return;
            }
            showMovieGridView();
            movieAdapter = new MovieAdapter(MainActivity.this, movieList);
            movieGridView.setAdapter(movieAdapter);



            showMovieGridView();
            movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra(Intent.EXTRA_TEXT,movieList);
                    intent.putExtra(Main2Activity.POSITION,i);
                    startActivity(intent);
                }
            });

        }
    }

    //OptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sortaction,menu);
        mViewByPopularity=menu.findItem(R.id.sort_popular);
        mViewByRating=menu.findItem(R.id.sort_rating);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id) {
            case R.id.sort_rating:
                mViewByRating.setChecked(true);
                mViewByPopularity.setChecked(false);
                break;
            case R.id.sort_popular:
                mViewByPopularity.setChecked(true);
                mViewByRating.setChecked(false);
                break;
            case R.id.refresh:
                showMovieGridView();
                resetScreen();
                break;
        }
        new BackGroundTask().execute();

        return super.onOptionsItemSelected(item);
    }
}