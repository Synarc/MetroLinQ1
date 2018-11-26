package com.example.isaac.metrolinq.FirebaseRecyclerViewClasses;

public class QueueTimeName {

    private String time;
    private String name;
    private String paymentType;
    private String date;
    private String oriName;

    public String getOriName() {
        return oriName;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName = desName;
    }

    private String desName;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QueueTimeName(String time, String name, String date, String paymentType,String oriName, String desName) {
        this.time = time;
        this.name = name;
        this.date = date;
        this.paymentType = paymentType;
        this.desName= desName;
        this.oriName = oriName;
    }
}
