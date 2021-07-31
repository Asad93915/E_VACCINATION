package com.example.e_vaccination.Fargments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_vaccination.Activities.Login_Activity;
import com.example.e_vaccination.R;


public class LogoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_logout, container, false);
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutintent = new Intent(getContext(), Login_Activity.class);
                startActivity(logoutintent);
                SharedPreferences loginSharedPreferences;
                loginSharedPreferences = getActivity().getSharedPreferences(
                        (String) Login_Activity.MyPREFERENCES, Context.MODE_PRIVATE);
                Toast.makeText(getActivity(), "LogOut", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = loginSharedPreferences.edit();
                editor.putString("UniqueId", "");
                editor.commit();
                getActivity().finish();

            }
        });
        return  view;
    }
}