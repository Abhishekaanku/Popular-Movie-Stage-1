package com.exmaple.android.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title;
    private String overView;
    private String poster;
    private String originalTitle;
    private float user_rating;
    private String releaseDate;
    private float popularity;

    public Movie() {}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate=releaseDate;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }


    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public float getUser_rating() {
        return user_rating;
    }

    public float getPopularity() {
        return popularity;
    }


    @Override
    public String toString() {
        return(getTitle()+"\n"+getPoster()+"\n"+getReleaseDate()+"\n"+getOverView());
    }

    private Movie(Parcel in) {
        this.title=in.readString();
        this.overView=in.readString();
        this.poster=in.readString();
        this.releaseDate=in.readString();
        this.originalTitle=in.readString();
        this.user_rating=in.readFloat();
        this.popularity=in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(overView);
        parcel.writeString(poster);
        parcel.writeString(releaseDate);
        parcel.writeString(originalTitle);
        parcel.writeFloat(user_rating);
        parcel.writeFloat(popularity);
    }

    public static final Parcelable.Creator<Movie> CREATOR=new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}