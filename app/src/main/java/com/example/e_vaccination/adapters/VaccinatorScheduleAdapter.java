package com.example.e_vaccination.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.models.VaccinatorSchedule;

import java.util.List;


public class VaccinatorScheduleAdapter extends RecyclerView.Adapter<VaccinatorScheduleAdapter.ViewHolder> {
    private final List<VaccinatorSchedule> schedules;
    private IItemClickListener listener;

    public VaccinatorScheduleAdapter(List<VaccinatorSchedule> schedules, IItemClickListener listener) {
        this.schedules = schedules;
        this.listener = listener;
    }


    @NonNull
    @Override
    public VaccinatorScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vaccinator_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinatorScheduleAdapter.ViewHolder holder, int position) {
        VaccinatorSchedule item = schedules.get(position);

        if (item == null || item.getName() == null) return;

        holder.mTittle.setText(item.getName());
        holder.mSatus.setText(item.isStatus() ? "Done" : "In-Progress");
        holder.mDescription.setText(item.getDescription());
        holder.mDistrict.setText(item.getDistrict() + "," + item.getUc());
        holder.mDate.setText(item.getDate());


        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
            listener.onClick(position);
        });

    }


    @Override
    public int getItemCount() {
        return schedules.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTittle, mDistrict, mDate, mDescription, mSatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTittle = itemView.findViewById(R.id.vaccineTittle);
            mDistrict = itemView.findViewById(R.id.district_uc);
            mDate = itemView.findViewById(R.id.vaccination_date);
            mDescription = itemView.findViewById(R.id.description);
            mSatus = itemView.findViewById(R.id.status);
        }
    }

    public interface IItemClickListener {
        void onClick(int position);
    }

}
