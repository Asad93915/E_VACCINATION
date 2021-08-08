package com.example.e_vaccination.models;

public class Vaccine {
    private String vaccineType;
    private String vaccineName;


    public Vaccine() {

    }

    public Vaccine(String vaccineType, String vaccineName) {
        this.vaccineType = vaccineType;
        this.vaccineName = vaccineName;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }


}
