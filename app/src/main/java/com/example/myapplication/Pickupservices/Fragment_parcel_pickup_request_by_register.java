package com.example.myapplication.Pickupservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragment.Fragment_home;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_parcel_pickup_request_by_register extends Fragment {
    View view;
    //ProgressDialog progressDialog;
    EditText fullname,cnic,senderphonenumber,receiverphonenumber,email,pickupcity,transfercity,subcity,nearpostoffice,fulladdress;
   Spinner spinner_pickupservices;
    String currentDate,time;
    Button btn_conformorder;
    SweetAlertDialog pDialog;

    String pickupservices,name,rphone,sphone,address,email1,cnic1,trancity,subcity1,pkupcity,neapostoffice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_parcel_pickup_request,null);
        pDialog = new SweetAlertDialog(getActivity());

        fullname =(EditText)view.findViewById(R.id.et_pickupname);
        cnic =(EditText)view.findViewById(R.id.et_pickupcnic);
        senderphonenumber =(EditText)view.findViewById(R.id.et_pickupsenderphoneno);
        receiverphonenumber =(EditText)view.findViewById(R.id.et_pickupreciever);
        email =(EditText)view.findViewById(R.id.et_pickupemail);
        pickupcity =(EditText)view.findViewById(R.id.et_pickupcity);
        transfercity =(EditText)view.findViewById(R.id.et_pickuptransfercity);
        subcity =(EditText)view.findViewById(R.id.et_pickupsubcity);
        nearpostoffice =(EditText)view.findViewById(R.id.et_pickuppostoffice);
        fulladdress =(EditText)view.findViewById(R.id.et_pickupfulladdress);
        spinner_pickupservices = (Spinner)view.findViewById(R.id.spinner_pickup_services);
        btn_conformorder=(Button)view.findViewById(R.id.btn_confromOrder);

        //currentDateAgain
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //Again CurrentTime
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(calendar.getTime());

        btn_conformorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 pickupservices = spinner_pickupservices.getSelectedItem().toString();
                name =fullname.getText().toString();
                cnic1 = cnic.getText().toString();
                sphone = senderphonenumber.getText().toString();
                rphone = receiverphonenumber.getText().toString();
                email1= email.getText().toString();
                pkupcity = pickupcity.getText().toString();
                trancity= transfercity.getText().toString();
                subcity1 = subcity.getText().toString();
                neapostoffice = nearpostoffice.getText().toString();
                address = fulladdress.getText().toString();

                if (name.length() > 4 && cnic1.length() == 13 &&
                       rphone.length() == 11 &&
                        sphone.length() == 11 && email1.length() > 0&& (email1.contains("@gmail.com") ||
                        email1.contains("@hotmail.com") || email1.contains("@yahoo.com"))
                        && pkupcity.length() > 6 && trancity.length() > 6
                        && subcity1.length() > 6 && neapostoffice.length() > 6
                        && address.length() > 0 ) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {

                    new BackgroundPickupbyregiserTask().execute(getString(R.string.request_pickup_order));
                    }else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                    }

                } else {
                    SweetAlertDialog builder1 = new SweetAlertDialog(getContext());
                    if (name.length() < 4) {
                        builder1.setContentText("Invalid  Name!");
                    }else if(cnic1.length()!=13){
                        builder1.setContentText("Invalid CNIC Number!");
                    } else if (sphone.length() != 11 || !sphone.startsWith("03")) {
                        builder1.setContentText("Invalid Sender Phone Number");
                    } else if (rphone.length() != 11 || !rphone.startsWith("03")) {
                        builder1.setContentText("Invalid Receiver Phone Number");
                    } else if (pkupcity.length() < 6) {
                        builder1.setContentText("Invalid Pickup City");
                    }else if (subcity1.length() < 6) {
                        builder1.setContentText("Invalid Sub City");
                    }else if (trancity.length() < 6) {
                        builder1.setContentText("Invalid Transfer City");
                    }else if (neapostoffice.length() < 6) {
                        builder1.setContentText("Invalid Near post Office  City");
                    }else if (address.length() <0) {
                        builder1.setContentText("Invalid Address City");
                    }else if(!email1.contains("@gmail.com" ) || !email1.contains("@hotmail.com")|| !email1.contains("@yahoo.com") ) {
                        builder1.setContentText("Please Enter Correct Email");
                    }
                    builder1.setConfirmText("Error!");
                    builder1.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                    builder1.show();

                }
            }
        });

        return view;
    }

    private class BackgroundPickupbyregiserTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//`Time`, `Date`, `ServiceName`, `FullName`, `CNIC`, `Sender_Phone`, `Receiver_Phone`, `Email`,
// `Pickup_City`, `Transfer_City`, `Near_PostOffice`, `FullAddress
                String data = URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")
                        + "&" + URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode( currentDate, "UTF-8")
                        + "&" + URLEncoder.encode("ServiceName", "UTF-8") + "=" + URLEncoder.encode(pickupservices, "UTF-8")
                        + "&" + URLEncoder.encode("FullName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("CNIC", "UTF-8") + "=" + URLEncoder.encode(cnic1, "UTF-8")
                        + "&" + URLEncoder.encode("Sender_Phone", "UTF-8") + "=" + URLEncoder.encode(sphone, "UTF-8")
                        + "&" + URLEncoder.encode("Receiver_Phone", "UTF-8") + "=" + URLEncoder.encode(rphone, "UTF-8")
                        + "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email1, "UTF-8")
                        + "&" + URLEncoder.encode("Pickup_City", "UTF-8") + "=" + URLEncoder.encode(pkupcity, "UTF-8")
                        + "&" + URLEncoder.encode("Transfer_City", "UTF-8") + "=" + URLEncoder.encode(trancity, "UTF-8")
                        + "&" + URLEncoder.encode("Sub_City", "UTF-8") + "=" + URLEncoder.encode(subcity1, "UTF-8")
                        + "&" + URLEncoder.encode("Near_PostOffice", "UTF-8") + "=" + URLEncoder.encode(neapostoffice, "UTF-8")
                        + "&" + URLEncoder.encode("FullAddress", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);

                }
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            clearpickuporderbyregister();
            new SweetAlertDialog(getActivity())
                    .setTitleText("" + result)
                    .show();

            //Toast.makeText(getActivity(), s + "", Toast.LENGTH_SHORT).show();

           // startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        /*  startActivity(new Intent(getContext(),MainActivity.class));
          getActivity().finish();*/

        }
    }

    private void clearpickuporderbyregister() {
        // EditText fullname,cnic,senderphonenumber,receiverphonenumber,email,pickupcity,transfercity,subcity,nearpostoffice,fulladdress;
        fullname.setText("");
        cnic.setText("");
        senderphonenumber.setText("");
        receiverphonenumber.setText("");
        email.setText("");
        pickupcity.setText("");
        transfercity.setText("");
        subcity.setText("");
        nearpostoffice.setText("");
        fulladdress.setText("");

    }
}
