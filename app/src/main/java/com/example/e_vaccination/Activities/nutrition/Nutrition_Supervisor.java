package com.example.e_vaccination.Activities.nutrition;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.Utils.AppUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Nutrition_Supervisor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition__supervisor);

        EditText vacName = findViewById(R.id.vaccinatorName);
        EditText vacEmail = findViewById(R.id.vaccinatorEmail);
        EditText vacPhone = findViewById(R.id.vaccinatorPhone);
        EditText vacPassword = findViewById(R.id.vaccinatorPassword);

        findViewById(R.id.actionAddVaccinator).setOnClickListener(v -> {
            String name = vacName.getText().toString();
            String email = vacEmail.getText().toString();
            String phone = vacPhone.getText().toString();
            String password = vacPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(Nutrition_Supervisor.this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!AppUtils.isValidEmail(email)) {
                Toast.makeText(Nutrition_Supervisor.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            AppUtils.startProgressBar(this, "Please wait...");


            String key = AppUtils.getRandomKey();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("email", email);
            hashMap.put("phone", phone);
            hashMap.put("password", password);
            hashMap.put("uid", key);
            hashMap.put("userType", "vaccinator");

            FirebaseDatabase.getInstance().getReference()
                    .child(AppConstants.Vaccinator)
                    .child(key)
                    .setValue(hashMap).addOnSuccessListener(unused -> {
                if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                Toast.makeText(Nutrition_Supervisor.this, "Vaccinator added successfully.", Toast.LENGTH_SHORT).show();
            });
        });

        findViewById(R.id.actionManageVaccinator).setOnClickListener(v -> {
            startActivity(new Intent(this, ManageVaccinators.class));
        });
    }
}