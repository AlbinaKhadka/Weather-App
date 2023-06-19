package com.example.weatherapp;

import android.widget.ImageView;

public class DataModel1 {
    private String cityname;
    private String date;
    private String image;
    private String temperature;
    private String description;


    public DataModel1(ImageView cityname, String date, String temperature, String description, String image) {

        this.cityname = String.valueOf(cityname);
        this.date = date;
        this.image = image;
        this.temperature = temperature;
        this.description = description;

    }

    public String getCityname() {
        return cityname;
    }

    public String getdate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

}
