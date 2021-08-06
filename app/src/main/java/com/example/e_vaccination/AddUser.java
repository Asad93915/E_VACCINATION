package com.example.e_vaccination;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_vaccination.Activities.BaseActivity;
import com.example.e_vaccination.Utils.AppConstants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddUser extends BaseActivity
{

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=11;
    EditText name,birthDate,city,uc,address;
    ImageView showimage,chooseImage;
    Button createChild;
    Person person;
    private DatabaseReference reference;
    private StorageReference storageref = FirebaseStorage.getInstance().getReference();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        name = findViewById(R.id.name);
        birthDate = findViewById(R.id.dob);
        city = findViewById(R.id.city);
        uc = findViewById(R.id.uc);
        chooseImage=findViewById(R.id.chooseImage);
        showimage=findViewById(R.id.showimage);
        createChild=findViewById(R.id.createChild);
        reference=FirebaseDatabase.getInstance().getReference().child("person");

        createChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getText(name).isEmpty()){
                    name.setError("Name Required!");
                }
                else if (getText(birthDate).isEmpty()){
                    birthDate.setError("BirthDate Required!");
                }
                else if (getText(city).isEmpty())
                {
                    city.setError("City Required!");
                }
                else  if (getText(uc).isEmpty())
                    uc.setError("Unioun Council Required!");
//                else if  (chooseImage.getDrawable() == null){
//                    error(null,"Image Require");
//                }
                else {

                    addUserDetails();

                }


            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
                }
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 11);

            }
        });


    }
    public void addUserDetails(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(AppConstants.FIRSTNAME, getText(name));
        hashMap.put(AppConstants.UC,Integer.parseInt(uc.getText().toString()));
        hashMap.put(AppConstants.CITY,getText(city));
        hashMap.put(AppConstants.DOB,Integer.parseInt(uc.getText().toString()));

        getReference(AppConstants.USERS).child(getAuth().getCurrentUser().getUid()).updateChildren(hashMap);



    }
    private void uploadToFirebase(Uri uri) {
        StorageReference fileref = storageref.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelid = root.push().getKey();
                        root.child(modelid).setValue(model);
                        Toast.makeText(AddUser.this, "upload succesfully", Toast.LENGTH_SHORT).show();
                        //   progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                // progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //   progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddUser.this, "uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  String getFileExtension(Uri muri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(muri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==11  && resultCode==RESULT_OK && data!=null){
            filePath=data.getData();
            showimage.setImageURI(filePath);
        }
    }
}