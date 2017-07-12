package com.brymian.boppo.bryant.sendTo;


import java.io.Serializable;

public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;
    private String episodeTitle, episodeHostName, episodeHostUsername, imagePath;
    private int episodeEid;
    private boolean isSelected;


    public Episode(){

    }

    public Episode(String episodeTitle, String episodeHostName, String episodeHostUsername, int episodeEid, String imagePath, boolean isSelected){
        this.episodeTitle = episodeTitle;
        this.episodeHostName = episodeHostName;
        this.episodeHostUsername = episodeHostUsername;
        this.episodeEid = episodeEid;
        this.imagePath = imagePath;
        this.isSelected = isSelected;
    }

    public void setEpisodeTitle(String episodeTitle){
        this.episodeTitle = episodeTitle;
    }

    public String getEpisodeTitle(){
        return episodeTitle;
    }

    public void setEpisodeHostName(String episodeHostName){
        this.episodeHostName = episodeHostName;
    }

    public String getEpisodeHostName(){
        return episodeHostName;
    }

    public void setEpisodeEid(int episodeEid){
        this.episodeEid = episodeEid;
    }

    public int getEpisodeEid(){
        return episodeEid;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }
}

