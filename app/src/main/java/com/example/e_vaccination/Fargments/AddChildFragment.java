package com.example.e_vaccination.Fargments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.AppConstants;
import com.example.e_vaccination.Utils.AppUtils;
import com.example.e_vaccination.models.Child;
import com.example.e_vaccination.models.Vaccine;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AddChildFragment extends BaseFragment {

    private static final int PERMISSION_CODE = 10101;
    private static final int IMAGE_PICK_CODE = 10102;
    private CircleImageView mImage;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private EditText name, dob;

    private MaterialSpinner genderSpinner;
    private final String[] genders = {"Girl", "Boy", "Others"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_child_fragment, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("ProfileImages");

        name = view.findViewById(R.id.childName);
        dob = view.findViewById(R.id.childDOB);
        mImage = view.findViewById(R.id.childernImage);

        genderSpinner = view.findViewById(R.id.genderSelection);

        genderSpinner.setItems(genders);

        view.findViewById(R.id.actionPickImage).setOnClickListener(v -> {
            loadImage();
        });

        view.findViewById(R.id.actionAddChild).setOnClickListener(v -> {
            if (name.getText().toString().isEmpty() || dob.getText().toString().isEmpty())
                Toast.makeText(getActivity(), "All Fields Required", Toast.LENGTH_SHORT).show();
            else if (mImageUri == null)
                Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            else
                uploadFile();
        });


        return view;
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
        AppUtils.startProgressBar(getActivity(), "Please wait ....");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageUri = data.getData();
            mImage.setImageURI(mImageUri);
        }
    }

    private void addCityToDb(String url) {
        String key = AppUtils.getRandomKey();
        Child child = new Child(name.getText().toString(), dob.getText().toString(), url, key, genders[genderSpinner.getSelectedIndex()]);
        FirebaseDatabase.getInstance().getReference().child(AppConstants.USERS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(AppConstants.Childerns)
                .child(key)
                .setValue(child).addOnCompleteListener(task -> {

            getActivity().runOnUiThread(() -> {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", new Date(System.currentTimeMillis()) + "");
                hashMap.put("status", false);
                for (Vaccine vaccine : AppConstants.vaccines()) {
                    hashMap.put("key", AppUtils.getRandomKey());
                    hashMap.put("name", vaccine.getVaccineName());

                    getChilderRef(key).child(AppUtils.getRandomKey()).setValue(hashMap);
                }

                Toast.makeText(getActivity(), "Child added successfully .", Toast.LENGTH_SHORT).show();
                if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
            });
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (AppUtils.isProgressBarShowing) AppUtils.dismissProgressBar();
                    }
                });
            }
        });
    }
}