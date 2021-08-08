package com.example.e_vaccination.models;

public class Vaccinator {
    private String name;
    private String email;
    private String uid;
    private String phone;
    private String password;


    public Vaccinator() {

    }

    public Vaccinator(String name, String email, String uid, String phone,String password) {
        this.name = name;
        this.password=password;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
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
