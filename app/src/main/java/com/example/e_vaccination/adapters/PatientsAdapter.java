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

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {
    private final List<User> users;
    private IItemClickListener listener;

    public PatientsAdapter(List<User> children, IItemClickListener listener) {
        this.users = children;
        this.listener = listener;
    }


    @NonNull
    @Override
    public PatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_patients_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsAdapter.ViewHolder holder, int position) {
        User item = users.get(position);

        if (item == null || item.getName() == null) return;

        holder.mName.setText(item.getName());
        holder.mDistrict.setText(item.getDistrict() + "," + item.getUc());
        holder.mPhone.setText(item.getNumber());

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
            listener.onClick(position);
        });


    }

    private void deleteCategory(Context context, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dView = LayoutInflater.from(context).inflate(R.layout.delete_update, null);
        builder.setView(dView);
        final AlertDialog dialog = builder.create();

        dView.findViewById(R.id.actionDelete).setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference()
                    .child(AppConstants.USERS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(AppConstants.Childerns)
                    .child(key)
                    .removeValue().addOnCompleteListener(task -> {
                Toast.makeText(context, "Deleted Successfully ...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            });

        });

        dView.findViewById(R.id.actionCancel).setOnClickListener(v1 -> dialog.dismiss());


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName, mDistrict, mPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.pattientsName);
            mDistrict = itemView.findViewById(R.id.district_uc);
            mPhone = itemView.findViewById(R.id.patientPhone);
        }
    }

    public interface IItemClickListener {
        void onClick(int position);
    }

}
