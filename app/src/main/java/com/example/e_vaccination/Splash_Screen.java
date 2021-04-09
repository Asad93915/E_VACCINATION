package com.example.e_vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_vaccination.Activities.Base_Activity;
import com.example.e_vaccination.Utils.AppConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Screen extends Base_Activity {
    TextView eVac;
    ImageView logoAnim;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash__screen);
/*
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(
                    FirebaseAuth.getInstance().getUid()
            ).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Toast.makeText(Splash_Screen.this, "" + snapshot.child("email").getValue().toString(), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String type = snapshot.child(AppConstants.USER_TYPE).getValue().toString();
                            if (type.equals(AppConstants.PATIENT))
                                startActivity(new Intent(Splash_Screen.this, Patient.class));
                            else
                            startActivity(new Intent(Splash_Screen.this, Vacccinator.class));
                            finish();
                        }
                    }, 1000);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            // zoomIn(view);
            //zoomOut(view);
            clockVise(view);
            blink(view);
        } else {*/
            new Handler().postDelayed(() -> {
                startActivity(new Intent(Splash_Screen.this, Login_Activity.class));
                finish();
            }, 3000);
 //       }

            // zoomIn(view);
            //zoomOut(view);
            clockVise(view);
            blink(view);



    }

    public void clockVise(View view) {
        logoAnim = findViewById(R.id.logoAnim);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clock_vise);
        logoAnim.startAnimation(animation1);
    }

    public void blink(View view) {
        eVac = findViewById(R.id.eVac);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        eVac.startAnimation(animation);
    }

    public void zoomIn(View view) {
        logoAnim = findViewById(R.id.logoAnim);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        logoAnim.startAnimation(animation1);

    }

    public void zoomOut(View view) {
        logoAnim = findViewById(R.id.logoAnim);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
        logoAnim.startAnimation(animation1);

    }

}