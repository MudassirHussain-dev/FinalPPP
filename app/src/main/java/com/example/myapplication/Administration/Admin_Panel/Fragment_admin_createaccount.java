package com.example.myapplication.Administration.Admin_Panel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.myapplication.Administration.Registrar_Panel.Fragment_Swapping_RegisteredParcel;
import com.example.myapplication.Fragment.Fragment_Tracking;
import com.example.myapplication.Fragment.Fragment_contact_us;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Utils;

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

public class Fragment_admin_createaccount extends Fragment {
    View view;
    EditText et_Fname, et_Lname, et_phone, et_emai, et_city, et_address, et_cnic, et_pass, et_r_pass;
    Spinner spinner_usertype;
    Button btn_adminCreateAccount;

    ProgressDialog progressDialog;

    String fname,lname,phone,email,city,address,cnic,pass,repass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_createaccount, null);
        progressDialog = new ProgressDialog(getContext());



        et_Fname = (EditText) view.findViewById(R.id.et_adminfirstname);
        et_Lname = (EditText) view.findViewById(R.id.et_adminlastname);
        et_phone = (EditText) view.findViewById(R.id.et_adminphone);
        et_emai = (EditText) view.findViewById(R.id.et_adminemail);
        et_city = (EditText) view.findViewById(R.id.et_adminrecity);
        et_address = (EditText) view.findViewById(R.id.et_adminaddress);
        et_cnic = (EditText) view.findViewById(R.id.et_admincnic);
        et_pass = (EditText) view.findViewById(R.id.et_adminre_password);
        et_r_pass = (EditText) view.findViewById(R.id.et_adminre_password);
        btn_adminCreateAccount = (Button) view.findViewById(R.id.btn_admincreateaccount);
        spinner_usertype = (Spinner) view.findViewById(R.id.spinner_admin_usertype);

        btn_adminCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new Backgroundtask().execute(getString(R.string.reg));
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
                    Toast.makeText(getActivity().getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!m.find()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Your Email iD is invalid", Toast.LENGTH_SHORT).show();
                } else if (!m1.find()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Your Phone Number is invalid", Toast.LENGTH_SHORT).show();
                } else if (!repass.equals(pass)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Both Password doesn't match", Toast.LENGTH_SHORT).show();
                } else if (pass.length() < 8 && pass.length() < 8) {
                    Toast.makeText(getActivity().getApplicationContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                   // new Backgroundtask().execute(getString(R.string.reg));
                    new AdminCreateAccountBackgroundtask().execute(getString(R.string.admin_create_account));

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

            /*    if (et_Fname.getText().toString().trim().length() > 0 && et_Lname.getText().toString().trim().length() > 0 && et_phone.getText().toString().trim().length() == 11
                        && et_emai.getText().toString().trim().length() > 0 && et_city.getText().toString().trim().length() > 0 && et_address.getText().toString().trim().length() > 0
                        && et_cnic.getText().toString().trim().length() == 13 && et_pass.getText().toString().trim().length() > 0 && et_r_pass.getText().toString().trim().length() > 0) {

                    if (et_pass.getText().toString().equals(et_r_pass.getText().toString())) {
                        new AdminCreateAccountBackgroundtask().execute(getString(R.string.admin_create_account));

                    }
                } else {
                    Toast.makeText(getContext(),"Please Enter All Records",Toast.LENGTH_SHORT).show();
                }


            }
        });*/


        return view;
    }

    public void adminsaveaccount(View view) {
        if (et_pass.getText().toString().equals(et_r_pass.getText().toString())) {
            //  new Backgroundtask().execute(getString(R.string.reg));
        } else {
            Toast.makeText(getActivity(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
        }

    }


    private class AdminCreateAccountBackgroundtask extends AsyncTask<String, Void, String> {
        /*String fname = et_Fname.getText().toString();
        String lname = et_Lname.getText().toString();
        String phone = et_phone.getText().toString();
        String email = et_emai.getText().toString();
        String city = et_city.getText().toString();
        String address = et_address.getText().toString();
        String cnic = et_cnic.getText().toString();
        String pass = et_pass.getText().toString();*/
        String usertype = spinner_usertype.getSelectedItem().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait...");
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
                        + "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")
                + "&" + URLEncoder.encode("UserType", "UTF-8") + "=" + URLEncoder.encode(usertype, "UTF-8");
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
            cleardata();

            if (result != null) {

                Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();
            }


        }



    }

    private void cleardata() {
        //et_Fname, et_Lname, et_phone, et_emai, et_city, et_address, et_cnic, et_pass, et_r_pass
        et_Fname.setText("");
        et_Lname.setText("");
        et_pass.setText("");
        et_emai.setText("");
        et_city.setText("");
        et_phone.setText("");
        et_address.setText("");
        et_cnic.setText("");

        et_r_pass.setText("");

    }
}