package com.example.myapplication.Administration.Admin_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Administration.Registrar_Panel.Fragment_Swapping_RegisteredParcel;
import com.example.myapplication.Fragment.Fragment_Tracking;
import com.example.myapplication.Fragment.Fragment_contact_us;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        getSupportFragmentManager().beginTransaction()/*.addToBackStack(null)*/.replace(R.id.mainfram, new Fragment_admin_home()).commit();


    }


}
