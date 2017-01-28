package com.theelitelabel.schoolevents2;

import android.widget.ImageView;

/**
 * Created by cfarl_000 on 8/17/2016.
 */
public class Event {
    private String name;
    private String organization;
    private String date;
    private Double dateNum;

    public Double getDateNum() {
        return dateNum;
    }

    public void setDateNum(Double dateNum) {
        this.dateNum = dateNum;
    }

    private String time;
    private String startTime;
    private String endTime;
    private Integer votes;
    private String address;
    private String picture;
    private String colorPicture;
    private String description;
    private String shareMessage;
    private String category;
    private ImageView background;
    private String lat;
    private String longitude;
    private String locationName;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    private String food;
    private String music;
    private String merchandise;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ImageView getBackground() {
        return background;
    }

    public void setBackground(ImageView background) {
        this.background = background;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareMessage() {
        return shareMessage;
    }

    public void setShareMessage(String shareMessage) {
        this.shareMessage = shareMessage;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Event(String name, String organization,String locationName,String date, Double dateNum,String endTime,String startTime,int votes, String address, String category, String description , String shareMessage,String picture, String colorPicture,ImageView background,
                 String food, String music, String merchandise, String lat, String longitude){
        this.name = name;
        this.organization = organization;
        this.locationName = locationName;
        this.date = date;
        this.dateNum = dateNum;
        this.endTime = endTime;
        this.startTime = startTime;
        this.votes = votes;
        this.address = address;
        this.lat = lat;
        this.longitude = longitude;
        this.category = category;
        this.description = description;
        this.shareMessage = shareMessage;
        this.background = background;
        this.picture = picture;
        this.colorPicture = colorPicture;
        this.food = food;
        this.music = music;
        this.merchandise = merchandise;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getColorPicture() {
        return colorPicture;
    }

    public void setColorPicture(String colorPicture) {
        this.colorPicture = colorPicture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Event(){

    }


}
