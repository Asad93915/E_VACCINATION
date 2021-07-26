package com.example.e_vaccination;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.VideoView;

public class Polio extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polio);
        //requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},555);
        //VideoView videoView=findViewById(R.id.videoView);
        Uri uri = Uri.parse("https://web.facebook.com/PTIOfficial/videos/784154188765256");
//        videoView.setVideoURI(uri);
//        videoView.start();
////        TextView textView=findViewById(R.id.textView);
//
//        Thread thread=new Thread()
//        {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("Gujjar toor" );
//                    }
//                });
//            }
//        };
//        thread.start();
    }
}