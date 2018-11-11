package com.example.isaac.metrolinq.FirebaseRecyclerViewClasses;

import java.io.Serializable;

public class JourneyInfo implements Serializable {

    private final Object currentTime;
    private String plateNumber;
    private Double oriLat, oriLon, desLat, delLon;
    private Long fare;
    private Long min;
    private Long hour;
    private Long day;
    private Long month;
    private Long year;
    private String driver;
    private String clientName, payType;

    public Object getCurrentTime() {
        return currentTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Double getOriLat() {
        return oriLat;
    }

    public void setOriLat(Double oriLat) {
        this.oriLat = oriLat;
    }

    public Double getOriLon() {
        return oriLon;
    }

    public void setOriLon(Double oriLon) {
        this.oriLon = oriLon;
    }

    public Double getDesLat() {
        return desLat;
    }

    public void setDesLat(Double desLat) {
        this.desLat = desLat;
    }

    public Double getDelLon() {
        return delLon;
    }

    public void setDelLon(Double delLon) {
        this.delLon = delLon;
    }

    public Long getFare() {
        return fare;
    }

    public void setFare(Long fare) {
        this.fare = fare;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public JourneyInfo(Object oriLat, Object oriLon, Object desLat,
                       Object delLon, Object fare, Object min, Object hour,
                       Object day, Object month, Object year, Object driver, Object plateNumber, Object clientName,
                       Object payType, Object currentTime) {

        if (driver == null){
            driver = "";
        }
        if(plateNumber == null){
            plateNumber = "";
        }

        this.oriLat = (Double) oriLat;
        this.oriLon = (Double) oriLon;
        this.desLat = (Double) desLat;
        this.delLon = (Double) delLon;
        this.fare = (Long) fare;
        this.min = (Long) min;
        this.hour = (Long) hour;
        this.day = (Long) day;
        this.month = (Long) month;
        this.year = (Long) year;
        this.driver = (String) driver;
        this.plateNumber = (String)plateNumber;
        this.clientName = (String) clientName;
        this.payType = (String)payType;
        this.currentTime = currentTime;
    }
}
