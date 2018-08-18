package com.exmaple.android.popularmoviesstage1.Utility;

import com.exmaple.android.popularmoviesstage1.MainActivity;
import com.exmaple.android.popularmoviesstage1.Movie;
import com.exmaple.android.popularmoviesstage1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieAPI {

    public static ArrayList<Movie> getMovies(String JSONResponse) {
        try {
            ArrayList<Movie> moviesList=new ArrayList<>();
            JSONObject moviesJSON=new JSONObject(JSONResponse);
            JSONArray movieJSONArray=moviesJSON.getJSONArray("results");
            int JSONArraySize=movieJSONArray.length();
            int index;
            for(index=0;index<JSONArraySize;index++) {
                moviesList.add(getMovie(movieJSONArray.getJSONObject(index)));
            }
            return moviesList;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Movie getMovie(JSONObject movieJSON) throws JSONException {

        Movie movie=new Movie();

        movie.setTitle(movieJSON.getString("title"));

        String overView=movieJSON.getString("overview");
        if(overView.length()==0) {
            movie.setOverView(MainActivity.getContext().getString(R.string.DefaultOverview));
        }
        else {
            movie.setOverView(overView);
        }

        movie.setPopularity((float)movieJSON.getDouble("popularity"));

        movie.setOriginalTitle(movieJSON.getString("original_title"));

        movie.setUser_rating((float)movieJSON.getDouble("vote_average"));

        movie.setReleaseDate(movieJSON.getString("release_date"));

        String baseImageURL= MainActivity.getContext().getString(R.string.baseImageURL);
        String image=movieJSON.getString("poster_path");
        image=baseImageURL+image;
        movie.setPoster(image);

        return movie;
    }

}
