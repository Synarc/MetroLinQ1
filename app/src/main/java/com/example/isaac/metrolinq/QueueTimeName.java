package com.example.isaac.metrolinq;

public class QueueTimeName {

    private String time, name;


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

    public QueueTimeName(String time, String name) {
        this.time = time;
        this.name = name;
    }
}
