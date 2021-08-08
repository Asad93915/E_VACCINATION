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
import com.example.e_vaccination.models.Child;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChildernsViewAdapter extends RecyclerView.Adapter<ChildernsViewAdapter.ViewHolder> {
    private final List<Child> childers;
    private IItemClickListener listener;

    public ChildernsViewAdapter(List<Child> children, IItemClickListener listener) {
        this.childers = children;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ChildernsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_childerns_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildernsViewAdapter.ViewHolder holder, int position) {
        Child item = childers.get(position);
        if (item == null || item.getName() == null) return;
        holder.mName.setText(item.getName());
        holder.mDOB.setText(item.getDob());

        holder.itemView.setOnClickListener(v -> {
//            deleteCategory(holder.itemView.getContext(), item.getKey());
            listener.onClick(position);
        });

        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.logo)
                .into(holder.mImage);
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
        return childers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName, mDOB;
        private CircleImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.listview_name);
            mImage = itemView.findViewById(R.id.listview_image);
            mDOB = itemView.findViewById(R.id.list_item_dob);
        }
    }

    public interface IItemClickListener {
        void onClick(int position);
    }

}
