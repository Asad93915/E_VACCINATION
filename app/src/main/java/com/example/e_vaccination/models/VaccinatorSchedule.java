package com.example.e_vaccination.models;

public class VaccinatorSchedule {
    private String name;
    private String date;
    private String description;
    private String key;
    private boolean status;
    private String uc;
    private String district;
    private String uid;

    public VaccinatorSchedule(String name, String date, String description, String key, boolean status, String uc, String district, String uid) {
        this.name = name;
        this.date = date;
        this.uid = uid;
        this.description = description;
        this.key = key;
        this.status = status;
        this.uc = uc;
        this.district = district;
    }

    public VaccinatorSchedule() {

    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
