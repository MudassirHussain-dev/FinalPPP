package com.example.myapplication.Fragment.FragmentComplaint;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Fragment.Fragment_home;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Fragment_complaint extends Fragment {

    View view;
    Spinner spinner_register_complaint;
    ImageView home_logo;
    Button btn_create_complaint;
    EditText er_articel_number;

    String articlenumber, services_complaint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaint, container, false);

        spinner_register_complaint = (Spinner) view.findViewById(R.id.spinner_register_complaint);
        er_articel_number = (EditText) view.findViewById(R.id.et_article_complaint_number);

        btn_create_complaint = (Button) view.findViewById(R.id.btn_create_complaint);
        btn_create_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (er_articel_number.getText().toString().trim().length() == 8) {
                    articlenumber = er_articel_number.getText().toString();
                    services_complaint = spinner_register_complaint.getSelectedItem().toString();

                   // Toast.makeText(getActivity(), articlenumber + services_complaint, Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_complaint_form(articlenumber, services_complaint)).commit();
                    complaintarticlenocleardata();
                }else {
                    SweetAlertDialog builder1 =    new SweetAlertDialog(getContext());
                   // AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    if(er_articel_number.length()!= 8) {
                        builder1.setContentText("Invalid Article Number!");
                    }
                    builder1.setConfirmText("Error!");
                    builder1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    builder1.show();
                   // builder1.setCancelable(true);
                  /*  builder1.setTitle("Error!");
                    builder1.setIcon(R.drawable.ic_delete);
                    builder1.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                   AlertDialog alert11 = builder1.create();
                    alert11.show();
                    */

                  /*   new SweetAlertDialog(getContext())
                            .setTitleText("Please Enter Article Number!")
                            .setContentText("You won't be able to recover this file!")
    .setConfirmText("Delete!")
     .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
        @Override
        public void onClick(SweetAlertDialog sDialog) {
            sDialog.dismissWithAnimation();
        }
    }).show();*/
                }
            }
        });


        return view;
    }

    private void complaintarticlenocleardata() {
        er_articel_number.setText("");
    }
}
