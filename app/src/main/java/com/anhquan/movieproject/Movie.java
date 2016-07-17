package com.anhquan.movieproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anhqu on 7/5/2016.
 */
public class Movie implements Parcelable{

    private String title;
    private String vote;
    private String picUrl;
    private String summary;
    private Date date;

    public Movie(String t, Date date, String v, String p, String s) {
        title = t;
        this.date = date;
        vote = v;
        picUrl = p;
        summary = s;
    }

    public Movie(Parcel in)
    {
        title = in.readString();
        this.date = new Date(in.readLong());
        vote = in.readString();
        picUrl = in.readString();
        summary = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeLong(date.getTime());
        dest.writeString(vote);
        dest.writeString(picUrl);
        dest.writeString(summary);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    public Date getDate(){
        return date;
    }

    public String getDateString()
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(date);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return title + " " + format1.format(date) + " " + vote;
    }
}
