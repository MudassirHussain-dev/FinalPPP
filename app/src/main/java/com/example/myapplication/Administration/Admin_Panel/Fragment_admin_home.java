package com.example.myapplication.Administration.Admin_Panel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Administration.Registrar_Panel.Fragment_Swapping_RegisteredParcel;
import com.example.myapplication.Administration.Registrar_Panel.Fragment_admin_registered_parcel;
import com.example.myapplication.Fragment.Fragment_Tracking;
import com.example.myapplication.Fragment.Fragment_contact_us;
import com.example.myapplication.Fragment.Fragment_feedback;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_admin_home extends Fragment {
    View view;
    LinearLayout adminregister, admintracker, adminparceldetail, adminviewcomplain, adminemployee, admincontactus, adminvieworder, admincreateaccount;
    TextView txtlogutadmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_home, null);
        adminregister = (LinearLayout) view.findViewById(R.id.LLadmin_registeerParcel);
        admintracker = (LinearLayout) view.findViewById(R.id.LLadmintrackerparcel);
        adminparceldetail = (LinearLayout) view.findViewById(R.id.LLadminviewparceldetail);
        adminviewcomplain = (LinearLayout) view.findViewById(R.id.LLadminviewcustomercomplaint);
        adminemployee = (LinearLayout) view.findViewById(R.id.LLadminviewemployeeregistrar);
        admincontactus = (LinearLayout) view.findViewById(R.id.LLadmincontactus);
        adminvieworder = (LinearLayout) view.findViewById(R.id.LL_onlineorder);
        admincreateaccount = (LinearLayout) view.findViewById(R.id.LLcreatenewacount);
        txtlogutadmin =(TextView)view.findViewById(R.id.txt_logutadmin);
        txtlogutadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Log out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        });

        adminregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_admin_registered_parcel()).commit();
            }
        });



        admintracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_Tracking()).commit();

            }
        });

        adminviewcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_viewcomplaint()).commit();
            }
        });

        adminparceldetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_view_parcel()).commit();
            }
        });

        adminemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_view_usertypes()).commit();
            }
        });

        admincontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_view_feedback()).commit();

            }
        });
        adminvieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_view_order()).commit();
                // Toast.makeText(getActivity(), "Make it", Toast.LENGTH_SHORT).show();
            }
        });
        admincreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainfram, new Fragment_admin_createaccount()).commit();
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

        builder1.setTitle("Registrar Side").setMessage("Do you want to exit or logout here?")
                .setCancelable(true)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        getActivity().finish();
                        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getActivity().finish();
                                // startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));

                            }
                        }
                );

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
/* @Override
    public void onDetach() {
        super.onDetach();



    }*/
}