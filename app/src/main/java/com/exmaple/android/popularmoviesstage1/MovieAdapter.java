package com.exmaple.android.popularmoviesstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>{

    MovieAdapter(Context context,List<Movie> movieList) {
        super(context,0,movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movieCurrent=getItem(position);

        int layoutID=R.layout.recyclergridview;
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        if(convertView==null) {
            convertView = layoutInflater.inflate(layoutID,parent,false);
        }

        ImageView mImageViewMoviePoster=convertView.findViewById(R.id.movie_poster);
        Picasso.with(getContext())
                .load(movieCurrent.getPoster())
                .placeholder(R.drawable.imageplaceholder)
                .into(mImageViewMoviePoster);
        return convertView;
    }
}
