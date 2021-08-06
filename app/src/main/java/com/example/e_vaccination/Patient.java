package com.example.e_vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_vaccination.Activities.BaseActivity;

public class Patient extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        TextView addNewChild=findViewById(R.id.addNewChild);
        ImageView icAddUser=findViewById(R.id.addChild);
        TextView infoAboutVac=findViewById(R.id.infoAboutVac);
        TextView viewVaccinationSchedule=findViewById(R.id.viewVacSchedule);

        icAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();

            }
        });
        addNewChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        infoAboutVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Patient.this,InfoAboutVaccination.class));
            }
        });
        viewVaccinationSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Patient.this,Schedule.class));
            }
        });
    }
    public void addUser() {
        startActivity(new Intent(Patient.this,AddUser.class));
    }
}