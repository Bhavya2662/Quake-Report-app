package com.example.quakereport;

public class Earthquake {
    private long timeInMilliseconds;
    private double magnitude;
    private String location;
    private String url;


    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url){
        this.magnitude=magnitude;
        this.timeInMilliseconds=timeInMilliseconds;
        this.location=location;
        this.url=url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl() {
        return url;
    }
}
