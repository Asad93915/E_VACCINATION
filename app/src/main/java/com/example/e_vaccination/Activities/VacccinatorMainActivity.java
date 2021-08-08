package com.example.e_vaccination.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.adapters.VaccinatorScheduleAdapter;
import com.example.e_vaccination.models.VaccinatorSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VacccinatorMainActivity extends AppCompatActivity {
    private VaccinatorScheduleAdapter mAdapter;
    private final List<VaccinatorSchedule> vaccinatorSchedules = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinator_main);

        RecyclerView mRecyclerView = findViewById(R.id.vaccinatorHomeRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new VaccinatorScheduleAdapter(vaccinatorSchedules, position -> {
            // todo open further activity here
        });

        mRecyclerView.setAdapter(mAdapter);

        loadSchedules();


    }

    private void loadSchedules() {
        FirebaseDatabase.getInstance().getReference().child("VaccinesSchedule")
                .child(Global.currentVaccinator.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) return;

                        vaccinatorSchedules.clear();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            VaccinatorSchedule schedule = snap.getValue(VaccinatorSchedule.class);
                            vaccinatorSchedules.add(schedule);
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}