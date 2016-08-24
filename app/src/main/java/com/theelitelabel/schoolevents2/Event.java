package com.theelitelabel.schoolevents2;

/**
 * Created by cfarl_000 on 8/17/2016.
 */
public class Event {
    private String name;
    private String date;
    private String time;
    private int votes;
    private String address;
    private String picture;
    private String description;
    private String category;


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

    public Event(String name, String date,int votes, String address, String category, String description , String picture){
        this.name = name;
        this.date = date;
        //this.time = time;
        this.votes = votes;
        this.address = address;
        this.category = category;
        this.description = description;

        this.picture = picture;
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

    public int getVotes() {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Event(){

    }


}
