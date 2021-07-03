package com.example.myapplication.Fragment.FragmentComplaint;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragment.Fragment_home;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_complaint_Sender extends Fragment {
    View view;
    EditText et_senderName, et_senderMobile, et_senderEmail, et_sender_city, et_senderAddress;
    ImageView home_logo;
    Button btn_backfirst, btn_next_second;

    String senderName, senderMobileno, senderEmail, senderCity, senderAddress;
    String article_number, complaint_services, complaint_name, complaint_mobile, bok_date, bok_time, bok_office, del_office, com_city;

    public Fragment_complaint_Sender(String article_number, String complaint_services
            , String complaint_name, String complaint_mobile, String bok_date
            , String bok_time, String bok_office, String del_office, String com_city) {
        this.article_number = article_number;
        this.complaint_services = complaint_services;
        this.complaint_name = complaint_name;
        this.complaint_mobile = complaint_mobile;
        this.bok_date = bok_date;
        this.bok_time = bok_time;
        this.bok_office = bok_office;
        this.del_office = del_office;
        this.com_city = com_city;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaint_sender, container, false);

        Toast.makeText(getActivity(), complaint_services + article_number + "" +
                complaint_mobile + bok_date + bok_time, Toast.LENGTH_SHORT).show();

        et_senderName = (EditText) view.findViewById(R.id.et_senderName);
        et_senderMobile = (EditText) view.findViewById(R.id.et_senderMobileno);
        et_senderEmail = (EditText) view.findViewById(R.id.et_senderEmail);
        et_sender_city = (EditText) view.findViewById(R.id.et_senderCity);
        et_senderAddress = (EditText) view.findViewById(R.id.et_senderAddress);
        btn_next_second = (Button) view.findViewById(R.id.btn_next_second);

        btn_next_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senderName = et_senderName.getText().toString();
                senderMobileno = et_senderMobile.getText().toString();
                senderEmail = et_senderEmail.getText().toString();
                senderCity = et_sender_city.getText().toString();
                senderAddress = et_senderAddress.getText().toString().toUpperCase();
               // Toast.makeText(getActivity(), senderName + senderMobileno, Toast.LENGTH_SHORT).show();
                if(senderName.length()>6&&senderMobileno.length()==11&&senderEmail.length()>0 &&( senderEmail.contains("@gmail.com" )||senderEmail.contains("@hotmail.com")||senderEmail.contains("@yahoo.com"))&&senderCity.length()>4&&senderAddress.length()>10) {

                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_complaint_Address(article_number, complaint_services,
                            complaint_name, complaint_mobile, bok_date, bok_time, bok_office, del_office, com_city,
                            senderName, senderMobileno, senderEmail, senderCity, senderAddress)).commit();
                   sendercomplaintcleardata();
                }
                else{
                    SweetAlertDialog builder1 =    new SweetAlertDialog(getContext());
                   // AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    if (senderName.length() < 6) {
                        builder1.setContentText("Invalid Sender Name!");
                    } else if (senderMobileno.length() != 11 || !senderMobileno.startsWith("03")) {
                        builder1.setContentText("Invalid Phone Number");
                    }else if (senderCity.length() < 10) {
                            builder1.setContentText("Invalid Sender City");
                        }
                    else if (senderAddress.length() < 10) {
                        builder1.setContentText("Please Enter Correct Address!");
                    } else if(! senderEmail.contains("@gmail.com" ) || !senderEmail.contains("@hotmail.com")|| !senderEmail.contains("@yahoo.com")) {
                        builder1.setContentText("Please Enter Correct Email");
                    }
                    builder1.setConfirmText("Error!");
                    builder1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    builder1.show();




                }
            }
        });



        return view;
    }

    private void sendercomplaintcleardata() {
        et_senderName.setText("");
        et_senderMobile.setText("");
        et_sender_city.setText("");
        et_senderAddress.setText("");
        et_senderEmail.setText("");
    }
}
