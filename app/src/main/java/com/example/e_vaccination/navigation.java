package com.example.e_vaccination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.e_vaccination.Activities.Base_Activity;
import com.example.e_vaccination.Fargments.AboutusFragment;
import com.example.e_vaccination.Fargments.LogoutFragment;
import com.example.e_vaccination.Fargments.NotificationFragment;
import com.example.e_vaccination.Fargments.ProfileFragment;
import com.example.e_vaccination.Fargments.ShareFragment;
import com.google.android.material.navigation.NavigationView;

public class navigation extends Base_Activity {
    NavigationView nav;
    ActionBarDrawerToggle toogle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
//        LayoutInflater inflater=LayoutInflater.from(this);
//        View view=inflater.inflate(R.layout.activity_login_,null,false);
//        drawerLayout.addView(view,0);
        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);

        toogle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_user:
                        temp=new ProfileFragment();
                        break;
                    case R.id.menu_notification:
                        temp=new NotificationFragment();
                        break;
                    case R.id.menu_Share:
                        temp=new ShareFragment();
                        break;
                    case R.id.menu_Aboutus:
                        temp=new AboutusFragment();
                        break;
                    case R.id.menu_Logout:
                        temp=new LogoutFragment();
                        break;




                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}