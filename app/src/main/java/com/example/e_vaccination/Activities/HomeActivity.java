package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.e_vaccination.BuildConfig;
import com.example.e_vaccination.Fargments.PatientHomeFragment;
import com.example.e_vaccination.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //navigation drawer listener
        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(new PatientHomeFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_profile:
                // todo open profile fragment
                break;
            case R.id.menu_notification:
                // todo open notification fragment
                break;
            case R.id.menu_Share:
                shareApp();
                break;
            case R.id.menu_about_us:
                // todo open about us fragment
                break;
            case R.id.menu_Logout:
                FirebaseAuth.getInstance().signOut();
                newActivity(LoginActivity.class);
                finish();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String shareMessage = "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));
    }

}