package com.example.e_vaccination.models;

public class Child {
    private String name;
    private String dob;
    private String image;
    private String key;
    private String gender;

    public Child(){

    }

    public Child(String name, String dob, String image, String key, String gender) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
