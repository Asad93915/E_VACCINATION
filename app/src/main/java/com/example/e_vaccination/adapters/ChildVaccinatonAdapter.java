package com.example.e_vaccination.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.models.VaccinatorChild;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChildVaccinatonAdapter extends RecyclerView.Adapter<ChildVaccinatonAdapter.ViewHolder> {
    private final List<VaccinatorChild> childers;
    private IItemClickListener listener;

    public ChildVaccinatonAdapter(List<VaccinatorChild> children, IItemClickListener listener) {
        this.childers = children;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ChildVaccinatonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_vaccination_child_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildVaccinatonAdapter.ViewHolder holder, int position) {
        VaccinatorChild item = childers.get(position);
        if (item == null || item.getUser() == null) return;
        holder.mName.setText(item.getChild().getName());
        holder.mDOB.setText(item.getChild().getDob());

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
            listener.onClick(position);
        });

        holder.actionDone.setOnClickListener(v -> {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", true);
            FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(item.getUser().getUid())
                    .child("Childerns")
                    .child(item.getChild().getKey())
                    .child("vaccines")
                    .child(item.getChildVaccines().getKey())
                    .updateChildren(hashMap)
                    .addOnCompleteListener(task -> Toast.makeText(v.getContext(), "Child added in vaccination list", Toast.LENGTH_SHORT).show());
        });

        Glide.with(holder.itemView.getContext())
                .load(item.getChild().getImage())
                .placeholder(R.drawable.logo)
                .into(holder.mImage);
    }


    @Override
    public int getItemCount() {
        return childers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName, mDOB;
        private final CircleImageView mImage;
        private final Button actionDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.listview_name);
            mImage = itemView.findViewById(R.id.listview_image);
            mDOB = itemView.findViewById(R.id.list_item_dob);
            actionDone = itemView.findViewById(R.id.actionDoneVaccine);
        }
    }

    public interface IItemClickListener {
        void onClick(int position);
    }

}
