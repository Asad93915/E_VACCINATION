package com.example.e_vaccination.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.adapters.VaccinatorScheduleAdapter;
import com.example.e_vaccination.models.ChildVaccines;
import com.example.e_vaccination.models.VaccinatorSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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
            Global.selecteVaccinatorSchedule = vaccinatorSchedules.get(position);
            startActivity(new Intent(VacccinatorMainActivity.this, ChildVaccinationActivity.class));
        });

        mRecyclerView.setAdapter(mAdapter);

        loadSchedules();

    }

    private void deleteCategory(Context context, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dView = LayoutInflater.from(context).inflate(R.layout.delete_update, null);
        builder.setView(dView);
        final AlertDialog dialog = builder.create();

        dView.findViewById(R.id.actionDelete).setOnClickListener(v -> {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", true);

            FirebaseDatabase.getInstance().getReference()
                    .child("VaccinesSchedule")
                    .child(Global.currentVaccinator.getUid())
                    .child(key)
                    .updateChildren(hashMap)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(context, "Vaccine record updated successfully ...", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    });

        });

        dView.findViewById(R.id.actionCancel).setOnClickListener(v1 -> dialog.dismiss());


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
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
                            if (!schedule.isStatus()) {
                                vaccinatorSchedules.add(schedule);
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}