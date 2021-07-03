package com.example.myapplication.Administration.Registrar_Panel;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SignupActivity;

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


public class Fragment_admin_registered_parcel extends Fragment {
    View view;
    EditText et_article, et_date, et_time, et_booking, et_delivery, et_mobile, et_targetOffice, etemail, et_customername;
    Button btn_reg;//,btn_crrentTime,btn_currentDate;
    // TextView tv_crrentTime,tv_currentDate;
    // ProgressDialog progressDialog;
    Spinner admin_service_spinner;
    SweetAlertDialog pDialog;
    String currentDate, time;
    String number, booking, delivery, mobile, target, service, email, customername;


    private static final String RegisterURl = "http://www.dabonjour.com/Mudassir/insert_tracking.php";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_registered_parcel, container, false);
        et_article = (EditText) view.findViewById(R.id.et_parcel_register);
        admin_service_spinner = (Spinner) view.findViewById(R.id.spinner_admin_services);
        /*et_date = (EditText) findViewById(R.id.et_date);
         et_time = (EditText) findViewById(R.id.et_time);
        tv_currentDate=(TextView)view.findViewById(R.id.tv_crrentDate);
        btn_currentDate=(Button)view.findViewById(R.id.btn_currentDate);*/

        et_booking = (EditText) view.findViewById(R.id.et_booking_office);
        et_delivery = (EditText) view.findViewById(R.id.et_delivery_office);
        et_targetOffice = (EditText) view.findViewById(R.id.et_destination_office);
        et_mobile = (EditText) view.findViewById(R.id.et_registerparcel_costomer_mobile);
        etemail = (EditText) view.findViewById(R.id.et_registerparcel_registraremail);
        et_customername = (EditText) view.findViewById(R.id.et_customer_name);

        // progressDialog = new ProgressDialog(getContext());
        pDialog = new SweetAlertDialog(getContext());


        //currentDateAgain

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());
        //Again CurrentTime
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(calendar.getTime());



    /*The Current Date Section only on Click
     btn_currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                tv_currentDate.setText(currentDate);

            }
        });*/

  /*      The Current Time Section on Click
        tv_crrentTime = (TextView)view.findViewById(R.id.tv_crrentTime);
        btn_crrentTime = (Button)view.findViewById(R.id.btn_currentTime);
        btn_crrentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time = "Time: "+format.format(calendar.getTime());

                tv_crrentTime.setText(time);
            }
        });*/


        btn_reg = (Button) view.findViewById(R.id.btnAdmin_parcle_register);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = et_article.getText().toString();
                //String dat = tv_currentDate.getText().toString();
                // String tim = tv_crrentTime.getText().toString();
                booking = et_booking.getText().toString();
                delivery = et_delivery.getText().toString();
                mobile = et_mobile.getText().toString();
                target = et_targetOffice.getText().toString();
                service = admin_service_spinner.getSelectedItem().toString();
                email = etemail.getText().toString();
                customername = et_customername.getText().toString();


                if (number.length() == 8 && customername.length() > 2
                        && booking.length() > 4
                        && delivery.length() > 4
                        && mobile.length() == 11
                        && email.length() > 0 && (email.contains("@gmail.com") || email.contains("@hotmail.com") || email.contains("@yahoo.com"))
                        && target.length() > 4) {

                    ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {

                        new Backgroundparcelregistertask().execute(RegisterURl);
                    } else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                    }
                } else {
                    SweetAlertDialog builder1 = new SweetAlertDialog(getContext());
                    if (number.length() != 8) {
                        builder1.setContentText("Please Enter Right Article Number!");
                    } else if (customername.length() < 2) {
                        builder1.setContentText("Please Enter Right Name!");
                    } else if (booking.length() < 4) {
                        builder1.setContentText("Please Enter Right Booking Office!");
                    } else if (delivery.length() < 4) {
                        builder1.setContentText("Please Enter Right To Deliver Office!");
                    } else if (target.length() < 4) {
                        builder1.setContentText("Please Enter Right Address!");
                    } else if (mobile.length() != 11 || !mobile.startsWith("03")) {
                        builder1.setContentText("Invalid Phone Number");
                    } else if (!email.contains("@gmail.com") || !email.contains("@hotmail.com") || !email.contains("@yahoo.com")) {
                        builder1.setContentText("Please Enter Correct Email");
                    }

                    // Toast.makeText(getActivity(),"Please Insert Record",Toast.LENGTH_SHORT).show();
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


    private class Backgroundparcelregistertask extends AsyncTask<String, Void, String> {
        /* String number = et_article.getText().toString();
         //String dat = tv_currentDate.getText().toString();
         // String tim = tv_crrentTime.getText().toString();
         String booking = et_booking.getText().toString();
         String delivery = et_delivery.getText().toString();
         String mobile = et_mobile.getText().toString();
         String target = et_targetOffice.getText().toString();
         String service = admin_service_spinner.getSelectedItem().toString();
         String email = etemail.getText().toString();
 */
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

                String data = URLEncoder.encode("Track_ID"/*+"Track_service"*/, "UTF-8") + "=" + URLEncoder.encode(service + number, "UTF-8")
                        + "&" + URLEncoder.encode("Datee", "UTF-8") + "=" + URLEncoder.encode(currentDate, "UTF-8")
                        + "&" + URLEncoder.encode("Timee", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")
                        + "&" + URLEncoder.encode("Delivery_office", "UTF-8") + "=" + URLEncoder.encode(booking, "UTF-8")
                        + "&" + URLEncoder.encode("Bookig_office", "UTF-8") + "=" + URLEncoder.encode(delivery, "UTF-8")
                        + "&" + URLEncoder.encode("Target_Office", "UTF-8") + "=" + URLEncoder.encode(target, "UTF-8")
                        + "&" + URLEncoder.encode("Email_Id", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("CustomerName", "UTF-8") + "=" + URLEncoder.encode(customername, "UTF-8")
                        + "&" + URLEncoder.encode("Mobile_no", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
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

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            cleardata();
            new SweetAlertDialog(getActivity())
                    .setTitleText("" + s)
                    .show();
            // et_article.setText("");
            // Toast.makeText(getActivity(), s + "", Toast.LENGTH_SHORT).show();
  /*          new AlertDialog.Builder(getActivity())
                    .setTitle("Alert Message!")
                    .setMessage(""+s)*/

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
                   /* .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })*/

            // A null listener allows the button to dismiss the dialog and take no further action.
            //.setNegativeButton(android.R.string.no, null)
            //.setIcon(R.drawable.ic_done_all)
            // .show();

        }
    }

    private void cleardata() {
        et_article.setText("");
        etemail.setText("");
        et_booking.setText("");
        et_delivery.setText("");
        et_targetOffice.setText("");
        et_mobile.setText("");
        et_customername.setText("");
    }


}


