package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Administration.Registrar_Panel.RegistrarActivity;
import com.example.myapplication.Administration.Admin_Panel.AdminActivity;
import com.github.tntkhang.gmailsenderlibrary.GMailSender;
import com.github.tntkhang.gmailsenderlibrary.GmailListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText et_userEmail, et_userPass;
    Button btn_login;
    TextView tv_createAccount, tv_btn_forgtPass;
    CheckBox show_hide_password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        et_userEmail = (TextInputEditText) findViewById(R.id.et_userEmailAccount);
        //forget Password Activit........
        tv_btn_forgtPass = (TextView) findViewById(R.id.tv_btn_forgtPass);
        tv_btn_forgtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {

                    if (et_userEmail.getText().toString().trim().length() > 0) {
                        //startActivity(new Intent(getApplicationContext(), ForgetActivity.class));
                        GMailSender.withAccount("contacthafizmudassir@gmail.com", "rescue1122")
                                //GMailSender.withAccount("narmeenshahzadi28@gmail.com", "rescue1122")
                                .withTitle("Zephyrs")
                                .withBody("Please Send Me Code My email this " + et_userEmail.getText().toString())
                                .withSender(getString(R.string.app_name))
                                .toEmailAddress("contacthafizmudassir@gmail.com") // one or multiple addresses separated by a comma
                                .withListenner(new GmailListener() {
                                    @Override
                                    public void sendSuccess() {
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void sendFail(String err) {
                                        Toast.makeText(getApplicationContext(), "Fail: " + err, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .send();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please insert your valid email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_userPass = (TextInputEditText) findViewById(R.id.et_userpass);
        //Check Box for Show & Hide Password.....
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {

                // If it is checkec then show password else hide
                // password
                if (isChecked) {

                    show_hide_password.setText(R.string.hide_pwd);
                    et_userPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    et_userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());// show password
                } else {
                    show_hide_password.setText(R.string.show_pwd);
                    et_userPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password

                }

            }
        });


        //This is a new Account.............
        tv_createAccount = (TextView) findViewById(R.id.tv_btn_newAccount);
        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });

        //This is a Login Coding.............
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_userEmail.getText().toString().trim().length() > 0 && et_userPass.getText().toString().trim().length() > 0) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true){
                        new Backgroundlogintask().execute(getString(R.string.login));
                    }else {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                    }
                } else {
                    if(et_userEmail.length()==0){
                        Toast.makeText(LoginActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
                    }else if (et_userPass.length()==0){
                        Toast.makeText(LoginActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();

                    }
                  //  Toast.makeText(LoginActivity.this, "Email or Password is Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private class Backgroundlogintask extends AsyncTask<String, Void, ArrayList<String>> {
        String userEmail = et_userEmail.getText().toString();
        String userPassword = et_userPass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //Now Output Stream Start.....
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8")
                        + "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(userPassword, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Now Input Stream Start................
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                String finalString = stringBuffer.toString();
                ArrayList<String> arrayList = new ArrayList<>();
                if (finalString.trim().charAt(0) == '{') {
                    JSONObject jsonObject = new JSONObject(finalString);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject finalJasonObject = jsonArray.getJSONObject(i);
                        arrayList.add(finalJasonObject.getString("FName"));
                        arrayList.add(finalJasonObject.getString("LName"));
                        arrayList.add(finalJasonObject.getString("Phone"));
                        arrayList.add(finalJasonObject.getString("Email"));
                        arrayList.add(finalJasonObject.getString("City"));
                        arrayList.add(finalJasonObject.getString("Address"));
                        arrayList.add(finalJasonObject.getString("CNIC"));
                        arrayList.add(finalJasonObject.getString("Password"));
                        arrayList.add(finalJasonObject.getString("UserType"));
                        // arrayList.add(finalJasonObject.getString("UserType"));
                    }
                    return arrayList;
                } else {
                    arrayList.add("Invalid!");
                    return arrayList;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result != null) {
                if (result.size() > 0) {
                    if (result.size() == 1) {
                        et_userEmail.setError("Invalid Email");
                        et_userPass.setError("Invalid Password");
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("FName", result.get(0));
                        editor.putString("LName", result.get(1));
                        editor.putString("Phone", result.get(2));
                        editor.putString("Email", result.get(3));
                        editor.putString("City", result.get(4));
                        editor.putString("Address", result.get(5));
                        editor.putString("CNIC", result.get(6));
                        editor.putString("Password", result.get(7));
                        editor.putString("UserType", result.get(8));
                        editor.commit();

                        if (result.get(8).equals("2")) {

                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences.edit();
                            editor1.putString("decider", "2");
                            editor1.commit();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else if (result.get(8).equals("1")) {
                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences.edit();
                            editor1.putString("decider", "1");
                            editor1.commit();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            finish();
                        } else if (result.get(8).equals("3")) {
                            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences.edit();
                            editor1.putString("decider", "3");
                            editor1.commit();
                            startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));
                            finish();
                        }

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}


