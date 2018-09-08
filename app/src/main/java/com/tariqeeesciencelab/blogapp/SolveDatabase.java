package com.tariqeeesciencelab.blogapp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;




public class SolveDatabase implements Serializable {

    @Exclude
    private String id;
    private int number;
    private String date;
    private String title;
    private String description;
    private String image;
    private String video;


    public SolveDatabase() {

    }

    public SolveDatabase( String date,int number,String title, String description, String image, String video ) {

        this.date = date;
        this.number = number;
        this.title = title;
        this.description = description;
        this.image = image;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }
}
