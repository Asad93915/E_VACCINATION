package com.example.e_vaccination.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.adapters.PatientsAdapter;
import com.example.e_vaccination.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {
    private final List<User> users = new ArrayList<>();
    private PatientsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        RecyclerView mRecyclerView = findViewById(R.id.patientsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new PatientsAdapter(users, position -> {
            // todo open further activity here
        });

        mRecyclerView.setAdapter(mAdapter);

        loadPatients();

    }

    private void loadPatients() {
        FirebaseDatabase.getInstance().getReference().child(AppConstants.USERS)
                .orderByChild("userType").equalTo(AppConstants.Vaccinator)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // todo load patients and map on view
                        // todo baqi kall inshallah
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}