package com.example.isaac.metrolinq;

public class DriverCar {

    String driver, car, client;

    public DriverCar(String driver, String car, String client) {
        this.driver = driver;
        this.car = car;
        this.client = client;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
