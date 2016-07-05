package com.anhquan.movieproject;

/**
 * Created by anhqu on 7/5/2016.
 */
public class Movie {

    private String title;
    private String duration;
    private String vote;
    private String picUrl;
    private String summary;

    public Movie(String t,String d, String v, String p, String s)
    {
        title = t;
        duration= d;
        vote = v;
        picUrl = p;
        summary = s;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
