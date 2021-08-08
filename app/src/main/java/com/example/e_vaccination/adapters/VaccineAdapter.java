package com.example.e_vaccination.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.models.ChildVaccines;
import com.example.e_vaccination.models.Vaccine;

import java.util.List;


public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.ViewHolder> {
    private final List<Vaccine> vaccines;
    private final List<ChildVaccines> childVaccines;

    public VaccineAdapter(List<ChildVaccines> vaccines) {
        this.vaccines = AppConstants.vaccines();
        this.childVaccines = vaccines;
    }


    @NonNull
    @Override
    public VaccineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vacination_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineAdapter.ViewHolder holder, int position) {
        Vaccine item = vaccines.get(position);
        if (item == null || item.getVaccineName() == null) return;

        holder.vaccinationName.setText(item.getVaccineName());
        holder.vaccinationStatus.setText("Pending");

        if (childVaccines.size() > 0) {
            if (isVaccinated(item.getVaccineName())) {
                holder.vaccinationStatus.setText("Done");
                holder.previewVaccineDate.setText(childVaccines.get(position).getDate());
            } else {
                holder.vaccinationStatus.setText("Pending");
            }
        }


        holder.vaccinationTypeImage.setImageResource(item.getVaccineType().equals("drop") ? R.drawable.icon_drop : R.drawable.icon_inj);

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
        });


    }


    @Override
    public int getItemCount() {
        return vaccines.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView vaccinationName, previewVaccineDate, vaccinationStatus;
        private final ImageView vaccinationTypeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccinationName = itemView.findViewById(R.id.vaccinationName);
            previewVaccineDate = itemView.findViewById(R.id.previewVaccineDate);
            vaccinationStatus = itemView.findViewById(R.id.vaccinationStatus);
            vaccinationTypeImage = itemView.findViewById(R.id.vaccinationTypeImage);
        }
    }

    private boolean isVaccinated(String name) {
        for (ChildVaccines vaccines : childVaccines) {
            if (name.toLowerCase().equals(vaccines.getName().toLowerCase()))
                return vaccines.isStatus();
        }
        return false;
    }


}
