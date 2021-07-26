package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup_Activity extends Base_Activity implements View.OnClickListener {
    EditText name,phoneNo,email,password;
    String userType="";
       FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        database = FirebaseDatabase.getInstance();
        ImageView logoUp = findViewById(R.id.logoUp);
        name = findViewById(R.id.firstName);

        phoneNo = findViewById(R.id.phoneNo);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.alreadyHaveAccount).setOnClickListener(this);

/*        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.vaccinator) {
                    userType = AppConstants.Vaccinator;
                } else if(checkedId == R.id.nSupervisor){
                    userType=AppConstants.NutritionSUPERVISOR;
                } else
                    userType = AppConstants.PATIENT;

            }
        });*/

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO SIGN UP USER THEN UPLOAD INFO
                if (name.getText().toString().isEmpty())
                    name.setError("Name Required");
                else if (email.getText().toString().isEmpty())
                    email.setError("Email Required");
                else if (phoneNo.getText().toString().isEmpty())
                    phoneNo.setError("Enter a Password");
                else if (password.getText().toString().isEmpty())
                    password.setError("Enter a Password");

                else {getAuth().createUserWithEmailAndPassword(getText(email), getText(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signUp) {
            if (name.getText().toString().isEmpty())
                    name.setError("Name Required");
                else if (email.getText().toString().isEmpty())
                    email.setError("Email Required");
                else if (phoneNo.getText().toString().isEmpty())
                    phoneNo.setError("Enter a Password");
                else if (password.getText().toString().isEmpty())
                    password.setError("Enter a Password");
            else if (userType.isEmpty())
                Toast.makeText(getApplicationContext(), "select user type", Toast.LENGTH_SHORT).show();
             else {
                getAuth().createUserWithEmailAndPassword(getText(email), getText(password))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                addUserDetails(task.getResult().getUser().getUid());
                            } else {

                            }
                        });
            }
        } else {
            newActivity(Login_Activity.class);
            finish();
        }

    }
public void addUserDetails(String uid){
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put(AppConstants.FIRSTNAME, getText(name));
    hashMap.put(AppConstants.EMAIL, getText(email));
    hashMap.put(AppConstants.PHONENUMBER, getText(phoneNo));
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