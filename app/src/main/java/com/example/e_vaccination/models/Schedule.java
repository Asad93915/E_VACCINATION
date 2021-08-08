package com.example.e_vaccination.models;


public class Schedule {
    private String tittle;
    private String key;
    private String date;

    public Schedule(String tittle, String key, String date) {
        this.tittle = tittle;
        this.key = key;
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }

    public String getTittle() {
        return tittle;
    }

    public Schedule() {

    }
}