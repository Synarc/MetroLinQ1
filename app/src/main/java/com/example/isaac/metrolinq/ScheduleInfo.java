package com.example.isaac.metrolinq;

public class ScheduleInfo {

    private int hour;
    private int min;
    private double oriLat;
    private double oriLon;
    private double desLat;
    private double desLon;
    private int fare;

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public double getOriLat() {
        return oriLat;
    }

    public void setOriLat(double oriLat) {
        this.oriLat = oriLat;
    }

    public double getOriLon() {
        return oriLon;
    }

    public void setOriLon(double oriLon) {
        this.oriLon = oriLon;
    }

    public double getDesLat() {
        return desLat;
    }

    public void setDesLat(double desLat) {
        this.desLat = desLat;
    }

    public double getDesLon() {
        return desLon;
    }

    public void setDesLon(double desLon) {
        this.desLon = desLon;
    }

    public ScheduleInfo(Integer hour, Integer min, double oriLat, double oriLon, double desLat, double desLon, int fare) {
        this.hour = hour;
        this.min = min;
        this.oriLat = oriLat;
        this.oriLon = oriLon;
        this.desLat = desLat;
        this.desLon = desLon;
        this.fare = fare;
    }
}
