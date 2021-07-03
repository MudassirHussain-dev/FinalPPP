package com.example.myapplication.Fragment.FragmentComplaint;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Fragment_complaint_Address extends Fragment {

    View view;
    ImageView home_logo;
    EditText et_addName, et_addMobile, et_addEmail, et_addCity, et_addAddress;
    Button btn_finalcreate_complaint;
    //ProgressDialog dialog;
    String addName, addMobile, addEmail, addCity, addAddress;
    SweetAlertDialog pDialog;
    String senderName, senderMobileno, senderEmail, senderCity, senderAddress;
    String artice_number, complaint_servic, com_name, com_mobile, bo_date, bo_time, bo_office, de_office, co_city;

    public Fragment_complaint_Address(String artice_number, String complaint_servic, String com_name, String com_mobile, String bo_date, String bo_time, String bo_office, String de_office, String co_city, String senderName, String senderMobileno, String senderEmail, String senderCity, String senderAddress) {

        this.artice_number = artice_number;
        this.complaint_servic = complaint_servic;
        this.com_name = com_name;
        this.com_mobile = com_mobile;
        this.bo_date = bo_date;
        this.bo_time = bo_time;
        this.bo_office = bo_office;
        this.de_office = de_office;
        this.co_city = co_city;
        this.senderName = senderName;
        this.senderMobileno = senderMobileno;
        this.senderEmail = senderEmail;
        this.senderCity = senderCity;
        this.senderAddress = senderAddress;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complaint_address, container, false);


        et_addName = (EditText) view.findViewById(R.id.et_addressName);
        et_addMobile = (EditText) view.findViewById(R.id.et_AddressMobileno);
        et_addEmail = (EditText) view.findViewById(R.id.et_addressEmail);
        et_addCity = (EditText) view.findViewById(R.id.et_addressCity);
        et_addAddress = (EditText) view.findViewById(R.id.et_addressAddress);
        btn_finalcreate_complaint = (Button) view.findViewById(R.id.btn_final_create_complaint);

       /* dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait");
        dialog.setCanceledOnTouchOutside(false);*/

        pDialog = new SweetAlertDialog(getActivity());

        btn_finalcreate_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addName = et_addName.getText().toString();
                    addMobile = et_addMobile.getText().toString();
                    addEmail = et_addEmail.getText().toString();
                    addCity = et_addCity.getText().toString();
                    addAddress = et_addAddress.getText().toString();

                    if (addName.length() > 6 && addMobile.length() == 11 && addEmail.length() > 0 && (addEmail.contains("@gmail.com") ||
                            addEmail.contains("@hotmail.com") || addEmail.contains("@yahoo.com"))
                            && addCity.length() > 4 && addAddress.length() > 0) {
                        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected() == true) {


                            new ComplaintReord().execute(getString(R.string.InsertComplaint));
                        }else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Please Check Your own Connection!")
                                    .show();
                        }

                    }else {
                        SweetAlertDialog builder1 = new SweetAlertDialog(getContext());
                        // AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        if (addName.length() < 6) {
                            builder1.setContentText("Invalid Addressee Name!");
                        } else if (addMobile.length() != 11 || !addMobile.startsWith("03")) {
                            builder1.setContentText("Invalid Phone Number");
                        }  else if (addCity.length() < 10) {
                            builder1.setContentText("Invalid City");
                        } else if (addAddress.length() < 0) {
                            builder1.setContentText("Please Enter Correct Address!");
                        }else if (!addEmail.contains("@gmail.com") || !addEmail.contains("@hotmail.com") || !addEmail.contains("@yahoo.com")) {
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

    private class ComplaintReord extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();
            // dialog.show();
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
                //Service_Name`, `Article_Number`, `cName`, `cMobile`, `Booking_Date`, `Booking_Time`, `Booking_Office`, `Deliviery_Officee`,
                // `cCity`, `sName`, `sMobile`, `sEmail`, `sCity`, `sAddress`, `adName`, `adMobile`, `adEmail`, `adCity`, `adAddress`

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("service_name", "UTF-8") + "=" + URLEncoder.encode(complaint_servic, "UTF-8")
                        + "&" + URLEncoder.encode("article_number", "UTF-8") + "=" + URLEncoder.encode(artice_number, "UTF-8")
                        + "&" + URLEncoder.encode("cName", "UTF-8") + "=" + URLEncoder.encode(com_name, "UTF-8")
                        + "&" + URLEncoder.encode("cMobile", "UTF-8") + "=" + URLEncoder.encode(com_mobile, "UTF-8")
                        + "&" + URLEncoder.encode("cCity", "UTF-8") + "=" + URLEncoder.encode(co_city, "UTF-8")
                        + "&" + URLEncoder.encode("booking_date", "UTF-8") + "=" + URLEncoder.encode(bo_date, "UTF-8")
                        + "&" + URLEncoder.encode("booking_time", "UTF-8") + "=" + URLEncoder.encode(bo_time, "UTF-8")
                        + "&" + URLEncoder.encode("booking_office", "UTF-8") + "=" + URLEncoder.encode(bo_office, "UTF-8")
                        + "&" + URLEncoder.encode("deliviery_officee", "UTF-8") + "=" + URLEncoder.encode(de_office, "UTF-8")
                        + "&" + URLEncoder.encode("s_name", "UTF-8") + "=" + URLEncoder.encode(senderName, "UTF-8")
                        + "&" + URLEncoder.encode("s_mobile", "UTF-8") + "=" + URLEncoder.encode(senderMobileno, "UTF-8")
                        + "&" + URLEncoder.encode("s_email", "UTF-8") + "=" + URLEncoder.encode(senderEmail, "UTF-8")
                        + "&" + URLEncoder.encode("s_city", "UTF-8") + "=" + URLEncoder.encode(senderCity, "UTF-8")
                        + "&" + URLEncoder.encode("s_address", "UTF-8") + "=" + URLEncoder.encode(senderAddress, "UTF-8")
                        + "&" + URLEncoder.encode("adName", "UTF-8") + "=" + URLEncoder.encode(addName, "UTF-8")
                        + "&" + URLEncoder.encode("adMobile", "UTF-8") + "=" + URLEncoder.encode(addMobile, "UTF-8")
                        + "&" + URLEncoder.encode("adEmail", "UTF-8") + "=" + URLEncoder.encode(addEmail, "UTF-8")
                        + "&" + URLEncoder.encode("adCity", "UTF-8") + "=" + URLEncoder.encode(addCity, "UTF-8")
                        + "&" + URLEncoder.encode("adAddress", "UTF-8") + "=" + URLEncoder.encode(addAddress, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Now Start the Input Stream.........
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                return stringBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //dialog.dismiss();

            complaintedittextclear();
            //Toast.makeText(getActivity(), "" + result, Toast.LENGTH_SHORT).show();
            new SweetAlertDialog(getActivity())
                    .setTitleText("" + result)
                    .show();
            //getFragmentManager().beginTransaction().add(R.id.frame, new Fragment_complaint()).commit();


        }
    }

    private void complaintedittextclear() {
        et_addAddress.setText("");
        et_addName.setText("");
        et_addCity.setText("");
        et_addEmail.setText("");
        et_addMobile.setText("");
    }
}
