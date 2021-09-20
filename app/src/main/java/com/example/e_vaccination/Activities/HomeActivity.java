package com.example.e_vaccination.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.e_vaccination.Fargments.PatientHomeFragment;
import com.example.e_vaccination.Fargments.navigation.ProfileFragment;
import com.example.e_vaccination.R;
import com.example.e_vaccination.Utils.Global;
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

        View hView = navigationView.getHeaderView(0);
        TextView name = hView.findViewById(R.id.nav_user_name);
        TextView email = hView.findViewById(R.id.nav_user_email);

        name.setText(Global.currentUSer.getName());
        email.setText(Global.currentUSer.getEmail());

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
            case R.id.menu_home:
                loadFragment(new PatientHomeFragment());
                break;
            case R.id.menu_profile:
                loadFragment(new ProfileFragment());
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
        shareMessage = shareMessage + "https://i.diawi.com/sDXnMN";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));
    }

}