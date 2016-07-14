package com.anhquan.movieproject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anhqu on 7/5/2016.
 */
public class Movie implements Serializable{

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
