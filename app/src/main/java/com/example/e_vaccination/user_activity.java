package com.example.e_vaccination;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.e_vaccination.Activities.Base_Activity;

public class user_activity extends Base_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity);
        Button vaccintion=findViewById(R.id.vaccination);
        Button polio=findViewById(R.id.polio_worker);


        polio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_activity.this,Polio.class));
            }
        });
        vaccintion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_activity.this,Patient.class));
            }
        });
    }
}