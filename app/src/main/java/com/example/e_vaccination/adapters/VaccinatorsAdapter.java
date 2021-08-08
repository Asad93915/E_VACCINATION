package com.example.e_vaccination.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_vaccination.R;
import com.example.e_vaccination.models.Vaccinator;

import java.util.List;


public class VaccinatorsAdapter extends RecyclerView.Adapter<VaccinatorsAdapter.ViewHolder> {
    private final List<Vaccinator> vaccinators;
    private IItemClickListener listener;

    public VaccinatorsAdapter(List<Vaccinator> vaccinators, IItemClickListener listener) {
        this.vaccinators = vaccinators;
        this.listener = listener;
    }


    @NonNull
    @Override
    public VaccinatorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vaccinators_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinatorsAdapter.ViewHolder holder, int position) {
        Vaccinator item = vaccinators.get(position);
        if (item == null || item.getName() == null) return;
        holder.mName.setText(item.getName());
        holder.mPhone.setText(item.getPhone());

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
            listener.onClick(position);
        });


    }


    @Override
    public int getItemCount() {
        return vaccinators.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName, mPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.item_vaccinator_name);
            mPhone = itemView.findViewById(R.id.item_vaccinator_email);
        }
    }

    public interface IItemClickListener {
        void onClick(int position);
    }

}
