package com.example.e_vaccination;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_vaccination.Activities.Base_Activity;

import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;

public class Patient extends Base_Activity {
    private static final int RESULT_LOAD_IMAGE = 123;
    EditText name,fName,cit,uc,address;
    ImageView childImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        name=findViewById(R.id.name);
        fName=findViewById(R.id.fName);
        cit=findViewById(R.id.city);
        uc=findViewById(R.id.uc);
        address=findViewById(R.id.address);
        childImage=findViewById(R.id.childImage);
        findViewById(R.id.camPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},555);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }


            }
        });
        findViewById(R.id.uploadPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);



            }
        });
    }
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                //Bitmap bitmap = getBitmapFromUri(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                childImage.setImageBitmap(bitmap);


            //to know about the selected image width and height
           // Toast.makeText(MainActivity.this, image_view.getDrawable().getIntrinsicWidth()+" & "+image_view.getDrawable().getIntrinsicHeight(), Toast.LENGTH_SHORT).show();
            Toast.makeText(Patient.this, childImage.getDrawable().getIntrinsicWidth()+" & "+childImage.getDrawable().getIntrinsicHeight(), Toast.LENGTH_SHORT).show();
        }

    }*/

}