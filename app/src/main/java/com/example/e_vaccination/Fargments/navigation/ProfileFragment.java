package com.example.e_vaccination.Fargments.navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.e_vaccination.Fargments.BaseFragment;
import com.example.e_vaccination.PasswordView;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends BaseFragment {
    private Uri mImageUri;
    private CircleImageView userProfile;
    private static final int PERMISSION_CODE = 10101;
    private static final int IMAGE_PICK_CODE = 10102;
    private StorageReference mStorageRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("ProfileImages");


        if (getActivity() == null || getView() == null) return;

        TextView tvname = getView().findViewById(R.id.user_profile_name);
        TextView tvEmail = getView().findViewById(R.id.user_profile_email);

        tvname.setText(Global.currentUSer.getName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

        getView().findViewById(R.id.actionPickImage).setOnClickListener(v -> {
            loadImage();
        });


        userProfile = getView().findViewById(R.id.userProfile);

        loadGlide(Global.currentUSer.getImage());


        getView().findViewById(R.id.actionChangePassword).setOnClickListener(v -> {
            changePasswordDialog();
        });

        getView().findViewById(R.id.actionSchedules).setOnClickListener(v -> {

        });

    }


    private void loadImage() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSION_CODE);

        } else {
            pickImageFromGallery();

        }

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();

            } else {
                Toast.makeText(getActivity(), "Permission Denied.....", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                fileReference.putFile
                        (mImageUri)
                        .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri ->
                                addCityToDb(uri.toString())));
            } else {
                Toast.makeText(getActivity(), "No file selected",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addCityToDb(String url) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("image", url);

        getUserRef()
                .updateChildren(hashMap)
                .addOnSuccessListener(unused -> {
                    // todo image uploaded successfully ....
                });
    }


    private void loadGlide(String imageURL) {
        Glide.with(this)
                .load(imageURL)
                .addListener(new RequestListener<Drawable>() {
                    @SuppressLint("NewApi")
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        userProfile.setImageResource(R.drawable.profile_picture);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(userProfile);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageUri = data.getData();
            userProfile.setImageURI(mImageUri);
            try {
                uploadFile();

            } catch (Exception e) {
                System.out.println("Exception Screen " + e.toString());
            }
        }
    }


    private void changePasswordDialog() {
        View view = this.getLayoutInflater().inflate(R.layout.change_password, null);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setView(view);

        PasswordView edtPassword = view.findViewById(R.id.edtEmail);


        view.findViewById(R.id.actionCancel).setOnClickListener(v1 -> dialog.dismiss());

        view.findViewById(R.id.applyPassword).setOnClickListener(v12 -> {
            String password = edtPassword.getText().toString().trim();
            if (password.isEmpty() || password.length() < 6) {
                Toast.makeText(getActivity(), "Please enter valid Password", Toast.LENGTH_SHORT).show();
                return;
            }

            final ProgressDialog mPB = new ProgressDialog(getActivity());
            mPB.setMessage("Please wait..");
            mPB.setCancelable(false);
            mPB.show();

            FirebaseAuth.getInstance().getCurrentUser().updatePassword(password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            mPB.dismiss();
                            Toast.makeText(getActivity(), "Password updated successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            dialog.dismiss();
                            mPB.dismiss();
                            Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        dialog.setCancelable(false);
        dialog.show();

    }


}