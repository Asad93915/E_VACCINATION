package com.example.e_vaccination.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_);
        mAuth = FirebaseAuth.getInstance();
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
    public void loadFragment(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }
     public String getId()
     {
         return id;
     }



    public String getText(EditText editText)
    {
        return  editText.getText().toString().trim() ;
    }
    public boolean isNewUser(){
        return getAuth().getCurrentUser()==null;
    }

}