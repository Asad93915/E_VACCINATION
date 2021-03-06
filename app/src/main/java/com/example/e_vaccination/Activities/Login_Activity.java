package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_vaccination.AddUser;
import com.example.e_vaccination.Nutrition_Supervisor;
import com.example.e_vaccination.Patient;
import com.example.e_vaccination.R;
//import com.example.e_vaccination.Staff.Nutrition_Supervisor;
import com.example.e_vaccination.Utils.AppConstants;
//import com.example.e_vaccination.Staff.Vacccinator;
//import com.example.e_vaccination.Staff.Worker;
import com.example.e_vaccination.Vacccinator;
import com.example.e_vaccination.Worker;
import com.example.e_vaccination.navigation;
import com.example.e_vaccination.user_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends Base_Activity {
    public static Object MyPREFERENCES;
    private FirebaseAuth mAuth;
    private EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        ImageView logoIn = findViewById(R.id.logoIn);
         email = findViewById(R.id.email);
         password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.forgetPassword).setOnClickListener(this::resetPasswordViaEmail);


        findViewById(R.id.login).setOnClickListener(v ->
        {

                if(getText(email).isEmpty())
                    email.setError("Email Required");
                else if (getText(password).isEmpty())
                    password.setError("Password Required");
                else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(Login_Activity.this, task -> {
                                if (task.isSuccessful()) {
                                    validateUser(task.getResult().getUser().getUid());

                                   Toast.makeText(Login_Activity.this, "login succesfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            });
                }
        });

        findViewById(R.id.dontHaveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newActivity(Signup_Activity.class);
                finish();

            }
        });
    }
    private void validateUser(String uid) {
        getReference(AppConstants.USERS).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot == null) return;
                String type = snapshot.child(AppConstants.USER_TYPE).getValue().toString();
                if (type.equals(AppConstants.Vaccinator)) {
                    startActivity(new Intent(Login_Activity.this, Vacccinator.class));
                    Toast.makeText(Login_Activity.this, "Login As a Vaccinator", Toast.LENGTH_SHORT).show();
                }
                else if (type.equals(AppConstants.Worker)) {
                    startActivity(new Intent(Login_Activity.this, Worker.class));
                    Toast.makeText(Login_Activity.this, "Login As Worker", Toast.LENGTH_SHORT).show();
                }
                else if (type.equals(AppConstants.NutritionSUPERVISOR)){
                    startActivity(new Intent(Login_Activity.this, Nutrition_Supervisor.class));
                    Toast.makeText(Login_Activity.this, "Login As Nutrition Supervisor", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(Login_Activity.this, AddUser.class));
                    Toast.makeText(Login_Activity.this, "Login As Patient", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    public void resetPasswordViaEmail(View view) {
        String  Email = email.getText().toString();
        // Check if there email is empty or not
        if (!Email.trim().isEmpty()) {
            mAuth.sendPasswordResetEmail(Email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password reset email has sent your mail. Check your email!", Toast.LENGTH_SHORT).show();

                            } else {
                                // ...
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();

        }
    }
}

