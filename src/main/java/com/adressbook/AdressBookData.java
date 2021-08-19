package com.adressbook;

import java.util.Objects;

public class AdressBookData {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zipCode;
    public String phoneNumber;
    public String email;

    public AdressBookData(int id, String firstName, String lastName, String address, String city, String state, int zipCode, String phoneNumber, String emailId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = emailId;
    }
}
