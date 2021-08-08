package com.example.e_vaccination.Activities;

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

import com.example.e_vaccination.Activities.nutrition.Nutrition_Supervisor;
import com.example.e_vaccination.Fargments.AddChildFragment;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
import com.example.e_vaccination.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.e_vaccination.Utils.AppConstants.NutritionSUPERVISOR;
import static com.example.e_vaccination.Utils.AppConstants.PATIENT;
import static com.example.e_vaccination.Utils.AppConstants.Vaccinator;

public class SplashActivity extends BaseActivity {
    TextView eVac;
    ImageView logoAnim;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);


        clockVise(view);
        blink(view);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child("users").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) return;

                            User user = snapshot.getValue(User.class);

                            if (user == null || user.getUserType() == null) return;

                            Global.currentUSer = user;

                            switch (user.getUserType()) {
                                case PATIENT:
                                    newActivity(HomeActivity.class);
                                    break;
                                case Vaccinator:
                                    newActivity(VacccinatorMainActivity.class);
                                    Toast.makeText(view.getContext(), "Login As a Vaccinator", Toast.LENGTH_SHORT).show();
                                    break;

                                case NutritionSUPERVISOR:
//                                    Toast.makeText(view.getContext(), "Login As Nutrition Supervisor", Toast.LENGTH_SHORT).show();
                                    newActivity(Nutrition_Supervisor.class);
                                    break;
                                default:
                                    startActivity(new Intent(view.getContext(), AddChildFragment.class));
                                    Toast.makeText(view.getContext(), "Login As Patient", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("");
                        }
                    });
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                finish();
            }, 3000);
            //       }

            // zoomIn(view);
            //zoomOut(view);


        }

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