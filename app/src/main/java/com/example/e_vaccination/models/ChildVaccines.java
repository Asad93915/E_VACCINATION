package com.example.e_vaccination.models;

public class ChildVaccines {
    private String key;
    private String date;
    private String name;

    public ChildVaccines(String key, String date, String name) {
        this.key = key;
        this.date = date;
        this.name = name;
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
}
