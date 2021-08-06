package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_vaccination.AddUser;
import com.example.e_vaccination.Nutrition_Supervisor;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppUtils;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.Vacccinator;
import com.example.e_vaccination.Worker;
import com.example.e_vaccination.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.e_vaccination.Utils.AppConstants.NutritionSUPERVISOR;
import static com.example.e_vaccination.Utils.AppConstants.PATIENT;
import static com.example.e_vaccination.Utils.AppConstants.USERS;
import static com.example.e_vaccination.Utils.AppConstants.Vaccinator;
import static com.example.e_vaccination.Utils.AppConstants.Worker;

public class LoginActivity extends BaseActivity {
    public static Object MyPREFERENCES;
    private FirebaseAuth mAuth;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.forgetPassword).setOnClickListener(this::resetPasswordViaEmail);

        findViewById(R.id.login).setOnClickListener(v -> {
            if (getText(email).isEmpty())
                email.setError("Email Required");
            else if (getText(password).isEmpty())
                password.setError("Password Required");
            else {
                AppUtils.startProgressBar(this, "Please wait ....");
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {

                                if (task.getResult() == null || task.getResult().getUser() == null)
                                    return;

                                validateUser(task.getResult().getUser().getUid());

                            } else {
                                if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }).addOnCanceledListener(() -> {
                    if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                });
            }
        });

        findViewById(R.id.dontHaveAccount).setOnClickListener(v -> {
            newActivity(SignupActivity.class);
            finish();

        });
    }

    private void validateUser(String uid) {
        getReference(USERS).child(uid).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();

                        if (snapshot.getValue() == null) return;

                        User user = snapshot.getValue(User.class);

                        if (user == null || user.getUserType() == null) return;

                        Global.currentUSer = user;

                        switch (user.getUserType()) {
                            case PATIENT:
                                newActivity(HomeActivity.class);
                                break;
                            case Vaccinator:
                                startActivity(new Intent(LoginActivity.this, Vacccinator.class));
                                Toast.makeText(LoginActivity.this, "Login As a Vaccinator", Toast.LENGTH_SHORT).show();
                                break;
                            case Worker:
                                startActivity(new Intent(LoginActivity.this, Worker.class));
                                Toast.makeText(LoginActivity.this, "Login As Worker", Toast.LENGTH_SHORT).show();
                                break;
                            case NutritionSUPERVISOR:
                                startActivity(new Intent(LoginActivity.this, Nutrition_Supervisor.class));
                                Toast.makeText(LoginActivity.this, "Login As Nutrition Supervisor", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                startActivity(new Intent(LoginActivity.this, AddUser.class));
                                Toast.makeText(LoginActivity.this, "Login As Patient", Toast.LENGTH_SHORT).show();
                                break;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                    }

                });
    }

    public void resetPasswordViaEmail(View view) {
        String Email = email.getText().toString();
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

