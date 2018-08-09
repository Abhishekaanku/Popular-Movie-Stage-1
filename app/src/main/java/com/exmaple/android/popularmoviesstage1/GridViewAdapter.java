package com.exmaple.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder>{

    private int movieItemCount;
    private String[] movieID=null;
    Context context;

    class GridViewHolder extends RecyclerView.ViewHolder implements  OnClickListener{
        public TextView mMovieID;
        public ImageView mMovieImage;

        GridViewHolder(View itemView) {
            super(itemView);
            mMovieID = (TextView) itemView.findViewById(R.id.movie_id);
            mMovieImage = (ImageView) itemView.findViewById((R.id.movie_poster));
            itemView.setOnClickListener(this);
        }
        void bind(int position) {
            mMovieID.setText(Integer.toString(position));
        }
        @Override

        public void onClick(View view) {
            int position=getAdapterPosition();
            Intent intent=new Intent(context,MovieDetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT,position);
            context.startActivity(intent);
        }
    }


    GridViewAdapter(int count,Context context) {
        this.movieItemCount=count;
        this.context=context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context viewGroupContext=parent.getContext();
        boolean isAttachToParent=false;
        int layoutID=R.layout.recyclergrid;
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroupContext);
        View view=layoutInflater.inflate(layoutID,parent,isAttachToParent);
        GridViewHolder gridViewHolder=new GridViewHolder(view);
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder gridViewHolder,int position)  {
        gridViewHolder.bind(position);
    }

    @Override

    public int getItemCount() {
        return movieItemCount;
    }

}
