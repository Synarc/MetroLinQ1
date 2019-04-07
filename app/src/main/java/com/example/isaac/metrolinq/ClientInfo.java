package com.example.isaac.metrolinq;

class ClientInfo {

    String firstName, lastName;
    int clientId;


    public ClientInfo(String firstName, String lastName, int clientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientId = clientId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
