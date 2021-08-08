package com.example.e_vaccination.models;

public class ChildVaccines {
    private String key;
    private String date;
    private String name;
    private boolean status;

    public ChildVaccines(String key, String date, String name, boolean status) {
        this.key = key;
        this.date = date;
        this.name = name;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public ChildVaccines() {

    }
}
