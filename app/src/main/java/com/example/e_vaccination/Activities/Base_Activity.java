package com.example.e_vaccination.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.e_vaccination.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  Base_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_);
        mAuth=FirebaseAuth.getInstance();

    }


    public FirebaseAuth getAuth() {

        return mAuth;
    }
    public DatabaseReference getReference(String child)
    {
        return FirebaseDatabase.getInstance().getReference().child(child);
    }
    public void newActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }



    public String getText(EditText editText)
    {
        return  editText.getText().toString();
    }

}