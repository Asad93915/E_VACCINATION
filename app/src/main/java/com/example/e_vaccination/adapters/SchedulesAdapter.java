package com.example.e_vaccination.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.models.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.ViewHolder> {
    private final List<Schedule> schedules;

    public SchedulesAdapter(List<Schedule> schedules) {
        this.schedules = schedules;
    }


    @NonNull
    @Override
    public SchedulesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_schedules, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesAdapter.ViewHolder holder, int position) {
        Schedule item = schedules.get(position);
        if (item == null || item.getTittle() == null) return;
        holder.mTittle.setText(item.getTittle());
        holder.mDate.setText(item.getDate());

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
        });


    }


    @Override
    public int getItemCount() {
        return schedules.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTittle, mDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTittle = itemView.findViewById(R.id.schedule_tittle);
            mDate = itemView.findViewById(R.id.schedule_date);
        }
    }

}
