package com.example.e_vaccination.models;

public class Vaccinator {
    private String name;
    private String email;
    private String uid;
    private String phone;


    public Vaccinator() {

    }

    public Vaccinator(String name, String email, String uid, String phone) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
