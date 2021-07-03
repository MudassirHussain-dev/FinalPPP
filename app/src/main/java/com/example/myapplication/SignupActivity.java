package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedOutputStream;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText et_Fname, et_Lname, et_phone, et_emai, et_city, et_address, et_cnic, et_pass, et_r_pass, et_typ;
    Button btn_register;

    ProgressDialog progressDialog;

    String fname,lname,phone,email,city,address,cnic,pass,repass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_Fname = (TextInputEditText) findViewById(R.id.et_firtName);
        et_Lname = (TextInputEditText) findViewById(R.id.et_lastName);
        et_phone = (TextInputEditText) findViewById(R.id.et_phoneNumber);
        et_emai = (TextInputEditText) findViewById(R.id.et_email);
        et_city = (TextInputEditText) findViewById(R.id.et_cityName);
        et_address = (TextInputEditText) findViewById(R.id.et_address);
        et_cnic = (TextInputEditText) findViewById(R.id.et_cnic);
        et_pass = (TextInputEditText) findViewById(R.id.et_password);
        et_r_pass = (TextInputEditText) findViewById(R.id.et_rePassword);

        btn_register = (Button) findViewById(R.id.btn_register);

        progressDialog = new ProgressDialog(this);
        /*string str = "124456789X";
if ((str.Count(char.IsDigit) == 9) && // only 9 digits
    (str.EndsWith("X", StringComparison.OrdinalIgnoreCase)
     || str.EndsWith("V", StringComparison.OrdinalIgnoreCase)) && //a letter at the end 'x' or 'v'
    (str[2] != '4' && str[2] != '9')) //3rd digit can not be equal to 4 or 9
{
    //Valid

}
else
{
    //invalid
}*/

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 fname = et_Fname.getText().toString();
                 lname = et_Lname.getText().toString();
                 phone = et_phone.getText().toString();
                 email = et_emai.getText().toString();
                 city = et_city.getText().toString();
                 address = et_address.getText().toString();
                 cnic = et_cnic.getText().toString();
                 pass = et_pass.getText().toString();
                 repass = et_r_pass.getText().toString();

                Pattern p = Pattern.compile(Utils.regEx);
                Matcher m = p.matcher(email);

                Pattern p1 = Pattern.compile(Utils.regEx_PH);
                Matcher m1 = p1.matcher(phone);


                if (fname.equals("") || fname.length() == 0 ||lname.equals("") || fname.length() == 0
                        || email.equals("") || email.length() == 0
                        || phone.equals("") || phone.length() == 0
                        || city.equals("") || city.length() == 0
                        || address.equals("") || address.length() == 0
                        || cnic.equals("") || cnic.length() == 0
                        || pass.equals("") || pass.length() < 0
                        || repass.equals("")
                        || repass.length() < 0) {

                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!m.find()) {
                    Toast.makeText(getApplicationContext(), "Your Email iD is invalid", Toast.LENGTH_SHORT).show();
                } else if (!m1.find()) {
                    Toast.makeText(getApplicationContext(), "Your Phone Number is invalid", Toast.LENGTH_SHORT).show();
                } else if (!repass.equals(pass)) {
                    Toast.makeText(getApplicationContext(), "Both Password doesn't match", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 8 && pass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                }else if(cnic.length()!=13){
                    Toast.makeText(getApplicationContext(), "Please Enter the Right CNIC Number!", Toast.LENGTH_SHORT).show();
                }else if(city.length()==0){
                    Toast.makeText(getApplicationContext(), "Please Enter the Right City Name!", Toast.LENGTH_SHORT).show();
                }else if(address.length()==0){
                    Toast.makeText(getApplicationContext(), "Please Enter the Right Address!", Toast.LENGTH_SHORT).show();

                } else {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {
                        new Backgroundtask().execute(getString(R.string.reg));
                    }else {
                        new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                    }

                }

            /*    if (et_Fname.getText().toString().trim().length() == 0 || et_Lname.getText().toString().trim().length() == 0 || et_phone.getText().toString().trim().length() == 11
                        || et_emai.getText().toString().trim().length() == 0 || et_city.getText().toString().trim().length() == 0 || et_address.getText().toString().trim().length() == 0
                        || et_cnic.getText().toString().trim().length() == 13 || et_pass.getText().toString().trim().length() == 0 || et_r_pass.getText().toString().trim().length() == 0) {
                 Toast.makeText(getApplicationContext(),"Please Insert All Records",Toast.LENGTH_SHORT).show();
                }else {
                    new Backgroundtask().execute(getString(R.string.reg));
                }*/

            }
        });
    }

    private class Backgroundtask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
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
//`FName`, `LName`, `Phone`, `Email`, `City`, `Address`, `CNIC`, `Password`

                //Now Start the Output Stream.............
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("FName", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8")
                        + "&" + URLEncoder.encode("LName", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8")
                        + "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                        + "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("City", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
                        + "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                        + "&" + URLEncoder.encode("CNIC", "UTF-8") + "=" + URLEncoder.encode(cnic, "UTF-8")
                        + "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                // + "&" + URLEncoder.encode("UserType", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result != null) {
                Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

           /* if (result != null) {
                if (result.equals("Data Inserted")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();

          *//*  if (result != null) {

                Toast.makeText(SignupActivity.this, result + "", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignupActivity.this, result + "", Toast.LENGTH_SHORT).show();
            }*//*



        }*/
            }
        }
    }
}



