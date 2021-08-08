package com.example.e_vaccination.Fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.adapters.ChildernsViewAdapter;
import com.example.e_vaccination.models.Child;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewChildernFragment extends BaseFragment {

    private ChildernsViewAdapter mAdapter;
    private final List<Child> childList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_childers, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.childernsRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ChildernsViewAdapter(childList, position -> {
            Global.selectedChildern = childList.get(position);
            open(new VaccinationFragment());
        });

        mRecyclerView.setAdapter(mAdapter);

        loadChilderns();


        return view;
    }

    private void loadChilderns() {
        FirebaseDatabase.getInstance().getReference()
                .child(AppConstants.USERS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(AppConstants.Childerns)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) return;

                        childList.clear();

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Child child = snap.getValue(Child.class);
                            childList.add(child);
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}