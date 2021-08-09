package com.example.e_vaccination;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class Polio extends AppCompatActivity {
    TextView call;
    private static final int call_Code=1122;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polio);
        call=findViewById(R.id.callNo);


        findViewById(R.id.actioncall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionCall();
            }


        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void actionCall() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)
            ==PackageManager.PERMISSION_DENIED){
            String[] permissions={Manifest.permission.CALL_PHONE};
            requestPermissions(permissions,call_Code);
        }
        else {
            Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +call.getText().toString()));
            startActivity(intent);
        }
    }
}