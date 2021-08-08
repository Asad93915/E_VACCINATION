package com.example.e_vaccination.Utils;

import com.example.e_vaccination.models.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {
    public static final String PATIENT = "patient";
    public static final String Worker = "worker";
    public static final String Vaccinator = "vaccinator";
    public static final String NutritionSUPERVISOR = "nutritionsupervisor";
    public static final String USERS = "users";
    //child

    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";

    public static final String PHONENUMBER = "phoneNumber";
    public static final String PASSWORD = "password";
    public static final String UID = "uid";
    public static final String USER_TYPE = "userType";
    public static final String NAME = "name";
    public static final String FATHERNAME = "fatherName";
    public static final String GENDER = "gender";
    public static final String Image = "Image";
    public static final String DOB = "dob";
    public static final String CITY = "city";
    public static final String UC = "uc";
    public static final String ADDRESS = "address";
    public static final String DISTRICT = "district";

    public static final String Childerns = "Childerns";
    public static final String SCHEDULES = "schedules";

    public static  List<Vaccine> vaccines() {
//        BCG Hepatitis OPV ROTA PCV Penta OPV-2 ROTA-2 PCV-2 Penta-2 OPV-3 IPV-1 PCV-3 Penta-3 Measles-MR-2
        List<Vaccine> vaccines = new ArrayList<>();

        vaccines.add(new Vaccine("injection", "BCG"));
        vaccines.add(new Vaccine("injection", "Hepatitis"));
        vaccines.add(new Vaccine("drop", "OPV"));
        vaccines.add(new Vaccine("drop", "ROTA"));
        vaccines.add(new Vaccine("injection", "PCV"));
        vaccines.add(new Vaccine("injection", "Penta"));
        vaccines.add(new Vaccine("drop", "OPV-2 "));
        vaccines.add(new Vaccine("injection", "Penta-2"));
        vaccines.add(new Vaccine("drop", "OPV-3"));
        vaccines.add(new Vaccine("injection", "IPV-1"));
        vaccines.add(new Vaccine("injection", "PCV-3"));
        vaccines.add(new Vaccine("injection", "Penta-3"));
        vaccines.add(new Vaccine("injection", "Measles-MR-2"));

        return vaccines;
    }

    // public AppConstants()
}
