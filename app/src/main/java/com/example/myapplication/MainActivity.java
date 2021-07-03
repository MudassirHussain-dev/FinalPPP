package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.Administration.Admin_Panel.Fragment_viewcomplaint;
import com.example.myapplication.Fragment.FragmentComplaint.Fragment_complaint;
import com.example.myapplication.Fragment.Fragment_Tracking;
import com.example.myapplication.Fragment.Fragment_contact_us;
import com.example.myapplication.Fragment.Fragment_express_services;
import com.example.myapplication.Fragment.Fragment_feedback;
import com.example.myapplication.Fragment.Fragment_home;
import com.example.myapplication.Fragment.Fragment_location_postoffice;
import com.example.myapplication.Pickupservices.Fragment_pickup_services;
import com.example.myapplication.Fragment.Fragment_post_codes;
import com.example.myapplication.Fragment.Fragment_tarrif;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long backPressTime;
    private Toast backToast;
    // DrawerLayout drawer;
    //public static FragmentManager fragmentManager;

    TextView fullname, emailprofile;

    // private AppBarConfiguration mAppBarConfiguration;
    String firstname, lastname, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);

        firstname = preferences.getString("FName", "");
        lastname = preferences.getString("LName", "");
        email = preferences.getString("Email", "");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
  /*      FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fragment_home()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState    ();


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        fullname = (TextView) headerView.findViewById(R.id.fullname_profile);
        emailprofile = (TextView) headerView.findViewById(R.id.email_profile);
        fullname.setText(firstname + " " + lastname);
        emailprofile.setText(email);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new Fragment_home();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    // boolean ishome = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }  else {
            super.onBackPressed();

            /*if (backPressTime + 2000 > System.currentTimeMillis()) {
               backToast.cancel();
               // super.onBackPressed();
                return;
            } else {
                backToast = Toast.makeText(this, "Press back to again exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressTime = System.currentTimeMillis();*/


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (id == R.id.v_home) {
            // Handle the camera action
          /*  FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,new Fragment_Home());
            fragmentTransaction.commit();*/
            Fragment fragment = new Fragment_home();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).replace(R.id.frame, fragment).commit();

            // getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_home()).commit();


        } else if (id == R.id.nav_trace_trace) {
            Fragment fragment = new Fragment_Tracking();
            getSupportFragmentManager().beginTransaction().add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
            // getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Fragment_Tracking()).commit();
        } else if (id == R.id.nav_registr_complaint) {
            Fragment fragment = new Fragment_complaint();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_express_services) {
            Fragment fragment = new Fragment_express_services();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_tarif) {

            Fragment fragment = new Fragment_tarrif();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_post_code) {
            Fragment fragment = new Fragment_post_codes();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_location) {
            Fragment fragment = new Fragment_location_postoffice();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_pickup) {
            Fragment fragment = new Fragment_pickup_services();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        } else if (id == R.id.nav_contact_us) {
            Fragment fragment = new Fragment_contact_us();
            getSupportFragmentManager().beginTransaction()
                    .add(new Fragment_home(), "Fragment_home").addToBackStack("Fragment_home").replace(R.id.frame, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {

            Toast.makeText(getApplicationContext(), "Log out", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
       /* else if(id==R.id.action_feedback){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Fragment_viewcomplaint()).commit();
        }*/
        return super.onOptionsItemSelected(item);
    }
}
