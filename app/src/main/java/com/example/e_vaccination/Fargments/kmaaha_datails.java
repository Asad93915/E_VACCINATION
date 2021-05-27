package com.example.e_vaccination.Fargments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_vaccination.R;

public class kmaaha_datails extends Fragment {
    TextView Tv_StNo;
    EditText Et_StNo;
    ListView streetList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_kmaaha_datails, container, false);
        Tv_StNo=view.findViewById(R.id.tvstreetno);
        Et_StNo=view.findViewById(R.id.streetno);
        streetList=view.findViewById(R.id.houselist);
        return view;
    }
}