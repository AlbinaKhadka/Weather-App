package com.example.weatherapp;

public class DataModel2 {

    private String date;
    private String imageUrl;
    private String temperature;
    public DataModel2( String date, String imageUrl, String temperature){


        this.date=date;
        this.imageUrl=imageUrl;
        this.temperature=temperature;


    }

    public String getDate() {return date;}
    public String getImageUrl() {return imageUrl;}
    public String getTemperature() {return temperature;}


}
