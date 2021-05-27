package com.example.e_vaccination.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_vaccination.R;
import com.google.android.material.snackbar.Snackbar;
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
    public void error(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

    }




    public String getText(EditText editText)
    {
        return  editText.getText().toString();
    }

}