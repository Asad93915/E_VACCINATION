package com.example.e_vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_vaccination.Activities.Base_Activity;
import com.example.e_vaccination.Utils.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup_Activity extends Base_Activity {
    EditText firstName,lastName,phoneNo,email,password,confirmPassword;
    String userType="";
    //    FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
//        database = FirebaseDatabase.getInstance();
        ImageView logoUp = findViewById(R.id.logoUp);
        firstName = findViewById(R.id.fistName);
        lastName = findViewById(R.id.lastName);
        phoneNo = findViewById(R.id.phoneNo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.cPassword);
        RadioGroup radioGroup=findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.vaccinator)
                {
                    userType=AppConstants.VACCINATOR;
                }
                else
                    userType=AppConstants.PATIENT;

            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO SIGN UP USER THEN UPLOAD INFO
                if (getText(firstName).isEmpty())
                    firstName.setError("Name Required");
                else if (getText(lastName).isEmpty())
                    lastName.setError("Required");
                else if (getText(password).isEmpty())
                    password.setError("Enter a Password");
                else if (getText(email).isEmpty())
                    email.setError("Email Required");
                else if (getText(confirmPassword).isEmpty())
                    confirmPassword.setError("Confirm Password");
                else if (!getText(password) .equals(getText(confirmPassword)) ) {
                    Toast.makeText(Signup_Activity.this, "Password is not Matching", Toast.LENGTH_SHORT).show();
                } else {getAuth().createUserWithEmailAndPassword(getText(email), getText(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (task.isSuccessful()) {
                                addUserDetails(task.getResult().getUser().getUid());
                            } else {
                                Toast.makeText(Signup_Activity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });
                }
            }
        });
        findViewById(R.id.alreadyHaveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newActivity(Login_Activity.class);

            }
        });
    }
public void addUserDetails(String uid){
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put(AppConstants.FIRSTNAME, getText(firstName));
    hashMap.put(AppConstants.LASTNAME, getText(lastName));
    hashMap.put(AppConstants.PHONENUMBER, getText(phoneNo));
    hashMap.put(AppConstants.EMAIL, getText(email));
    hashMap.put(AppConstants.USER_TYPE,userType);

    getReference(AppConstants.USERS).child(uid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Toast.makeText(Signup_Activity.this, "Data added Successfuly", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
                finish();
            }

        }
    });



}

}