package com.example.e_vaccination.Activities.nutrition;

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
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.adapters.VaccinatorsAdapter;
import com.example.e_vaccination.models.User;
import com.example.e_vaccination.models.Vaccinator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageVaccinators extends AppCompatActivity {
    private VaccinatorsAdapter mAdapter;
    private final List<Vaccinator> vaccinators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vaccinators);

        RecyclerView mRecyclerView = findViewById(R.id.vaccinatorsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new VaccinatorsAdapter(vaccinators, position -> {
            // todo vaccitors manage view
            manageVaccinatorsDialog(ManageVaccinators.this, vaccinators.get(position).getUid());
        });

        mRecyclerView.setAdapter(mAdapter);

        loadVaccinators();

    }

    private void loadVaccinators() {
        FirebaseDatabase.getInstance().getReference().child(AppConstants.Vaccinator)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) return;

                        vaccinators.clear();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            User user = snap.getValue(User.class);
                            if (user == null) return;

                            if (user.getUserType().equals(AppConstants.Vaccinator)) {

                                Vaccinator vaccinator = snap.getValue(Vaccinator.class);
                                vaccinators.add(vaccinator);
                            }

                        }

                        mAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void manageVaccinatorsDialog(Context context, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dView = LayoutInflater.from(context).inflate(R.layout.manage_vaccinator_dialog, null);
        builder.setView(dView);
        final AlertDialog dialog = builder.create();

        dView.findViewById(R.id.actionDelete).setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference()
                    .child(AppConstants.Vaccinator)
                    .child(key)
                    .removeValue().addOnCompleteListener(task -> {
                Toast.makeText(context, "Deleted Successfully ...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            });

        });

        dView.findViewById(R.id.actionAssignUC).setOnClickListener(v1 -> {
            Intent intent = new Intent(ManageVaccinators.this, AssignVaccineActivity.class);
            intent.putExtra("key", key);
            startActivity(intent);
            dialog.dismiss();
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


}