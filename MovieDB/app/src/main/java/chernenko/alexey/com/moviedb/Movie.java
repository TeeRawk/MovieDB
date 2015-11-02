package chernenko.alexey.com.moviedb;

import java.io.Serializable;

/**
 * Created by saion on 21.10.2015.
 */
public class Movie implements Serializable {

    private String mTitle;
    private String mPosterUrl;
    private String mDate;
    private String mDescription;
    private String mGenres;
    private double    mVote;

    public Movie() {
    }

    public Movie(String title, String posterUrl) {
        mTitle = title;
        mPosterUrl = posterUrl;
    }

    public Movie(String title, String posterUrl, String date, String description, double vote,String genres) {
        mTitle = title;
        mVote = vote;
        mDescription = description;
        mDate = date;
        mPosterUrl = posterUrl;
        mGenres=genres;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }

    public String getDate() {
        return mDate;
    }

    public String getYear ()
    {
       return mDate.substring(0,4);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public double getVote() {
        return mVote;
    }

    public void setVote(double vote) {
        mVote = vote;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getGenres() {
        return mGenres;
    }

    public void setGenres(String genres) {
        mGenres = genres;
    }
}
