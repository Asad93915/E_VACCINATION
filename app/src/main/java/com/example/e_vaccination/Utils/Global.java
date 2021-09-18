package com.example.e_vaccination.Utils;

import com.example.e_vaccination.models.Child;
import com.example.e_vaccination.models.User;
import com.example.e_vaccination.models.Vaccinator;
import com.example.e_vaccination.models.VaccinatorSchedule;

public class Global {
    public static User currentUSer = new User();
    public static Child selectedChildern = new Child();
    public static Vaccinator currentVaccinator = new Vaccinator();

    public static VaccinatorSchedule selecteVaccinatorSchedule = new VaccinatorSchedule();


}
