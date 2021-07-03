package com.example.myapplication.Administration.Registrar_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Administration.Admin_Panel.Fragment_view_order;
import com.example.myapplication.Administration.Admin_Panel.Fragment_viewcomplaint;

import com.example.myapplication.Administration.Admin_Panel.Fragment_view_parcel;
import com.example.myapplication.Fragment.Fragment_Tracking;
import com.example.myapplication.Fragment.Fragment_contact_us;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Pickupservices.Fragment_parcel_pickup_request_by_register;
import com.example.myapplication.R;
import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;

import java.util.ArrayList;

public class RegistrarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_fram,new Fragment_admin_registered_parcel()).commit();
        final FlareBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.inboxb),"Parcel Register","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.searchb),"Parcel Tracker","#80DEEA"));
        //tabs.add(new Flaretab(getResources().getDrawable(R.drawable.phoneb),"Call Log","#B39DDB"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.view_detail_registrar),"Parcel Detail","#EF9A9A"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.pickup_order_byregistar),"Pickup Order","#B2DFDB"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(RegistrarActivity.this);
        bottomBar.setTabChangedListener(new TabEventObject.TabChangedListener() {

            @Override
            public void onTabChanged(LinearLayout selectedTab, int selectedIndex, int oldIndex) {
               // getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3
                if(selectedIndex==0){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_fram,new Fragment_admin_registered_parcel()).commit();
                }
                else if(selectedIndex==1){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_fram, new Fragment_Tracking()).commit();

                    // getSupportFragmentManager().beginTransaction().replace(R.id.admin_fram,new Fragment_contact_us()).commit();
                }
                else if(selectedIndex==2){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_fram, new Fragment_view_parcel()).commit();

                }
                else if(selectedIndex==3){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_fram, new Fragment_view_order()).commit();
                }
                //Toast.makeText(getApplicationContext(),"Tab "+ selectedIndex+" Selected.", Toast.LENGTH_SHORT).show();
            }
        });






    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setTitle("Registrar Side").setMessage("Do you want to exit or logout here?")
        .setCancelable(true)
        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Log out", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       finish();
                        // startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));

                    }
                }
                );

        AlertDialog alert11 = builder1.create();
        alert11.show();
        super.onBackPressed();
    }
}
