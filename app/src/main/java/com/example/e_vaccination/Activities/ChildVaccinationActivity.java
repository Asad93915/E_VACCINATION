package com.example.e_vaccination.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.adapters.ChildVaccinatonAdapter;
import com.example.e_vaccination.models.Child;
import com.example.e_vaccination.models.ChildVaccines;
import com.example.e_vaccination.models.User;
import com.example.e_vaccination.models.VaccinatorChild;
import com.example.e_vaccination.models.VaccinatorSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChildVaccinationActivity extends AppCompatActivity {

    private ChildVaccinatonAdapter mAdapter;
    private List<VaccinatorChild> childVaccinations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_vaccination);

        RecyclerView recyclerView = findViewById(R.id.childVaccinationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ChildVaccinatonAdapter(childVaccinations, position -> {
            // todo d0 notting
        });

        recyclerView.setAdapter(mAdapter);

        loadChildToBeVacinate(Global.selecteVaccinatorSchedule);


    }

    private void loadChildToBeVacinate(VaccinatorSchedule schedule) {
        FirebaseDatabase.getInstance().getReference()
                .child("users").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getValue() == null) return; //todo onError Response...

                        for (DataSnapshot snap : snapshot.getChildren()) {

                            User user = snap.getValue(User.class);

                            if (user == null) return; // todo onError response

                            if (snap.hasChild("Childerns")
                                    && user.getDistrict().equals(schedule.getDistrict())
                                    && user.getUc().equals(schedule.getUc())) {

                                for (DataSnapshot child : snap.child("Childerns").getChildren()) {
                                    if (child.hasChild("vaccines")) {

                                        Child childItem = child.getValue(Child.class);

                                        childVaccinations.clear();

                                        for (DataSnapshot vaccine : child.child("vaccines").getChildren()) {
                                            if (vaccine.getValue() == null) return;

                                            ChildVaccines item = vaccine.getValue(ChildVaccines.class);
                                            if (item == null) return;

                                            if (item.getName().equals(schedule.getName()) && !item.isStatus()) {
                                                // key - snapshot.getKey()->snap.getKey()->Childerns->child.getKey()->vaccines- item.getKEy();

                                                VaccinatorChild vaccinatorChild = new VaccinatorChild(user, childItem, item);
                                                childVaccinations.add(vaccinatorChild);


                                            }
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}