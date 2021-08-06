package com.example.e_vaccination.models;

public class User {
    private String name;
    private String email;
    private String address;
    private String number;
    private String district;
    private String uc;
    private String uid;
    private String userType;

    public User() {

    }

    public User(String name, String email, String address, String number, String district, String uc, String uid, String userType) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
        this.district = district;
        this.uc = uc;
        this.uid = uid;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getDistrict() {
        return district;
    }

    public String getUc() {
        return uc;
    }

    public String getUid() {
        return uid;
    }

    public String getUserType() {
        return userType;
    }
}
