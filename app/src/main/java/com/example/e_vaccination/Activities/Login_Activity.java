package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_vaccination.Patient;
import com.example.e_vaccination.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends Base_Activity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        Locale locale = new Locale("en");
//        Locale.setDefault(locale);
//        Configuration config = getBaseContext().getResources().getConfiguration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_login_);
        ImageView logoIn = findViewById(R.id.logoIn);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


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
                                    Toast.makeText(Login_Activity.this, "login succesfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login_Activity.this, Patient.class));
                                } else {
                                    Toast.makeText(Login_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            });
                }
        });

        findViewById(R.id.forgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO FORGOT PASSWORD
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
}

