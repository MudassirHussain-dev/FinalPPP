package com.example.myapplication.Fragment.FragmentComplaint;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Fragment.Fragment_home;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Fragment_complaint_form extends Fragment {
    View view;
    ImageView home_logo;
    // DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog timePickerDialog;

    EditText et_date, et_time, et_full_name, et_mobile_number, et_bok_office, et_delivery_office, et_com_city;
    Button btn_next_create_complaint;
    String articlenumber, services_complaint;
    String complaint_name, complaint_mobile, bok_date, bok_time, bok_office, del_office, com_city;

    //This is a Constuctor of FragmentComplaintFrom.....
    public Fragment_complaint_form(String articlenumber, String services_complaint) {
        this.articlenumber = articlenumber;
        this.services_complaint = services_complaint;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaint_form, container, false);

        //Toast.makeText(getActivity(),articl_enumber,Toast.LENGTH_SHORT).show();


        btn_next_create_complaint = (Button) view.findViewById(R.id.btn_next_create_complaint);
        et_date = (EditText) view.findViewById(R.id.et_datePicker);
        et_time = (EditText) view.findViewById(R.id.et_timePicker);
        et_full_name = (EditText) view.findViewById(R.id.et_fullName);
        et_mobile_number = (EditText) view.findViewById(R.id.et_mobileNumber);
        et_bok_office = (EditText) view.findViewById(R.id.et_booking_complaint_office);
        et_delivery_office = (EditText) view.findViewById(R.id.et_delivery_complaint_office);
        et_com_city = (EditText) view.findViewById(R.id.et_complaint_city);


        btn_next_create_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint_name = et_full_name.getText().toString();
                complaint_mobile = et_mobile_number.getText().toString();
                bok_date = et_date.getText().toString();
                bok_time = et_time.getText().toString();
                bok_office = et_bok_office.getText().toString();
                del_office = et_delivery_office.getText().toString();
                com_city = et_com_city.getText().toString().toUpperCase();

                if (complaint_name.length() > 4 && complaint_mobile.length() == 11 &&
                        bok_office.length() > 6 && del_office.length() > 6 &&
                        com_city.length() > 6) {
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_complaint_Sender(articlenumber, services_complaint
                            , complaint_name, complaint_mobile, bok_date, bok_time, bok_office, del_office, com_city)).commit();
                complainantcleardata();
                } else {
                    SweetAlertDialog builder1 =    new SweetAlertDialog(getContext());
                   // AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    if (complaint_name.length() < 4) {
                        builder1.setContentText("Invalid Name!");
                    } else if (complaint_mobile.length() != 11 || !complaint_mobile.startsWith("03")) {
                        builder1.setContentText("Invalid Phone Number");
                    }else if (bok_office.length() < 6) {
                        builder1.setContentText("Invalid Booking Office City");
                    }
                    else if (del_office.length() < 6) {
                        builder1.setContentText("Invalid Transfer City");
                    }
                   else if (com_city.length() < 6) {
                        builder1.setContentText("Invalid Complainter City");
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


        et_time.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int H = calendar.get(Calendar.HOUR_OF_DAY);
            int M = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                     /*   String am_pm;
                        if(hourOfDay<12){
                            am_pm="AM";
                            et_time.setText(hourOfDay+":"+minute+" "+am_pm);
                        }else if(hourOfDay==12){
                            am_pm="PM";
                            et_time.setText(hourOfDay+":"+minute+" "+am_pm);
                        }else{
                            am_pm="PM";
                            et_time.setText(hourOfDay+":"+minute+" "+am_pm);
                        }*/
                        et_time.setText(hourOfDay + ":" + minute);

                    }
                }, H, M, true);
                timePickerDialog.show();

            }
        });

         /*       txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txt_date.setText(date);
            }
        };*/


        et_date.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        et_date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        return view;
    }

    private void complainantcleardata() {
   et_full_name.setText("");
   et_mobile_number.setText("");
   et_bok_office.setText("");
   et_date.setText("");
   et_time.setText("");
   et_delivery_office.setText("");
   et_com_city.setText("");
    }
}
