package com.example.e_vaccination.Fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.adapters.VaccineAdapter;
import com.example.e_vaccination.models.ChildVaccines;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VaccinationFragment extends BaseFragment {
    private final List<ChildVaccines> mVaccinesList = new ArrayList<>();
    private VaccineAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.polio_vacination_fragment, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.vaccinationRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));

        mAdapter = new VaccineAdapter(mVaccinesList);
        mRecyclerView.setAdapter(mAdapter);

        TextView name = view.findViewById(R.id.previewChildName);
        name.setText(Global.selectedChildern.getName());

        TextView dob = view.findViewById(R.id.previewDOB);
        dob.setText(Global.selectedChildern.getDob());

        CircleImageView image = view.findViewById(R.id.childImagView);
        Glide.with(inflater.getContext())
                .load(Global.selectedChildern.getImage())
                .into(image);

        loadVaccinesData();


        return view;
    }

    private void loadVaccinesData() {
        getChilderRef(Global.selectedChildern.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) return;

                        mVaccinesList.clear();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            ChildVaccines vaccines = snap.getValue(ChildVaccines.class);
                            mVaccinesList.add(vaccines);
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}