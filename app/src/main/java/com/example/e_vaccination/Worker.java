package com.example.e_vaccination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_vaccination.Fargments.Kahna_details;
import com.example.e_vaccination.Fargments.Nishter_details;
import com.example.e_vaccination.Fargments.kmaaha_datails;

public class Worker extends AppCompatActivity {
    TextView Kahna,Nishter,Kmaaha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        Nishter=findViewById(R.id.nishterDetails);
        Kahna=findViewById(R.id.kahnaDetails);
        Kmaaha=findViewById(R.id.kmaahaDetails);

        Nishter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Nishter_details());
            }
        });
        Kahna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Kahna_details());
            }
        });
        Kmaaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new kmaaha_datails());
            }
        });



    }
    private void loadFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }
}