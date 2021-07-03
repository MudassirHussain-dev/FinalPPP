package com.example.myapplication.Administration.Admin_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.w3c.dom.Text;

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

public class Update_UserTypeActivity extends AppCompatActivity {
    EditText fname, lname, email, phone, cnic, city, address, usertype, password;
    Bundle bundle;
    Button btn_update;
    TextView id;
    String updateid;
    ProgressDialog progressDialog;
   // String fname1, lname1, phone1, email1, city1, address1, cnic1, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__user_type);
        progressDialog = new ProgressDialog(this);
        id=findViewById(R.id.txt_usertype_IDupdate);

      /*  fname1 = fname.getText().toString();
        lname1 = lname.getText().toString();
        phone1 = phone.getText().toString();
        email1 = email.getText().toString();
        city1 = city.getText().toString();
        address1 = address.getText().toString();
        cnic1 = cnic.getText().toString();
        pass = password.getText().toString();*/

        fname = findViewById(R.id.updatefirstname);
        lname = findViewById(R.id.updatelastname);
        email = findViewById(R.id.updateemial);
        phone = findViewById(R.id.updatephone);
        city = findViewById(R.id.updatecity);
        cnic = findViewById(R.id.updatecnic);
        address = findViewById(R.id.updateaddress);
        usertype = findViewById(R.id.updateusertype);
        password = findViewById(R.id.updatepassword);
        btn_update = findViewById(R.id.btn_updaterecord);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateid = id.getText().toString();
                new Backgroundupdateusettypetask().execute(getString(R.string.updateusertype));
            }
        });


        bundle = getIntent().getExtras();
        //SELECT `ID`, `FName`, `LName`, `Phone`, `Email`, `City`, `Address`, `CNIC`, `Password`, `UserType` FROM `tbl_reg` WHERE 1
        if (bundle != null) {

            fname.setText(bundle.getString("FName"));
            lname.setText(bundle.getString("LName"));
            email.setText(bundle.getString("Email"));
            phone.setText(bundle.getString("Phone"));
            city.setText(bundle.getString("City"));
            cnic.setText(bundle.getString("CNIC"));
            address.setText(bundle.getString("Address"));
            usertype.setText(bundle.getString("UserType"));
            password.setText(bundle.getString("Password"));

        }

    }

    private class Backgroundupdateusettypetask extends AsyncTask<String, Void, String> {
        String fname1 = fname.getText().toString();
        String lname1 = lname.getText().toString();
        String phone1 = phone.getText().toString();
        String email1 = email.getText().toString();
        String  city1 = city.getText().toString();
        String  address1 = address.getText().toString();
        String cnic1 = cnic.getText().toString();
        String pass = password.getText().toString();
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
                String data = URLEncoder.encode("FName", "UTF-8") + "=" + URLEncoder.encode(fname1, "UTF-8")
                        + "&" + URLEncoder.encode("LName", "UTF-8") + "=" + URLEncoder.encode(lname1, "UTF-8")
                        + "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(phone1, "UTF-8")
                        + "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email1, "UTF-8")
                        + "&" + URLEncoder.encode("City", "UTF-8") + "=" + URLEncoder.encode(city1, "UTF-8")
                        + "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(address1, "UTF-8")
                        + "&" + URLEncoder.encode("CNIC", "UTF-8") + "=" + URLEncoder.encode(cnic1, "UTF-8")
                       /* + "&" + URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(cnic1, "UTF-8")*/
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

            }
        }
    }
}
