package com.example.e_vaccination.Fargments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.e_vaccination.R;
import com.example.e_vaccination.UserActivity;

public class PatientHomeFragment extends BaseFragment {
    private static final int RESULT_LOAD_IMAGE = 123;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_home, container, false);

        view.findViewById(R.id.actionAddNewChild).setOnClickListener(v -> {
            open(new AddChildFragment());
        });

        view.findViewById(R.id.actionViewChild).setOnClickListener(v -> {
            open(new ViewChildernFragment());
        });

        view.findViewById(R.id.actionVaccinationSchedule).setOnClickListener(v -> {
            open(new SchedulesFragment());
        });
        view.findViewById(R.id.actionVaccinationInfo).setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), UserActivity.class);
            startActivity(intent);
        });

        return view;
    }
}