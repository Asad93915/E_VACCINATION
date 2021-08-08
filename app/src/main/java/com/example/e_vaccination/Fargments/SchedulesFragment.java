package com.example.e_vaccination.Fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.adapters.SchedulesAdapter;
import com.example.e_vaccination.models.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SchedulesFragment extends BaseFragment {
    private SchedulesAdapter mAdapter;
    private final List<Schedule> scheduleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedules_fragment, container, false);

        RecyclerView mRecyclerViw = view.findViewById(R.id.schedulesRecyclerView);
        mRecyclerViw.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SchedulesAdapter(scheduleList);
        mRecyclerViw.setAdapter(mAdapter);

        loadSchedules();

        return view;
    }

    private void loadSchedules() {
        getReference().child(AppConstants.SCHEDULES)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) return;

                        scheduleList.clear();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Schedule schedule = snap.getValue(Schedule.class);

                            scheduleList.add(schedule);
                        }

                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}