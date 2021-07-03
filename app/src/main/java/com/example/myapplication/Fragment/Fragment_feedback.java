package com.example.myapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_feedback extends Fragment {
    View view;
    EditText yourname, youremailid, yourphoneno, writefeedback;
    Button btn_feedbacksumbit;
    ProgressDialog progressDialog;
    String name, email, mobile, feedbackwrite;
    SweetAlertDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feedback, null);
        pDialog = new SweetAlertDialog(getActivity());
        yourname = (EditText) view.findViewById(R.id.et_feedbackyouname);
        youremailid = (EditText) view.findViewById(R.id.et_feedbackemailid);
        yourphoneno = (EditText) view.findViewById(R.id.et_feedbackmobileno);
        writefeedback = (EditText) view.findViewById(R.id.et_feedbackwritethefeedback);

        btn_feedbacksumbit = (Button) view.findViewById(R.id.btn_feedbacksubmit);


        btn_feedbacksumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = yourname.getText().toString();
                email = youremailid.getText().toString();
                mobile = yourphoneno.getText().toString();
                feedbackwrite = writefeedback.getText().toString();
                if (name.length() > 4 && email.length() > 0 && (email.contains("@gmail.com") || email.contains("@hotmail.com") || email.contains("@yahoo.com")) && mobile.length() == 11 && feedbackwrite.length() > 0) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {

                        new Backgroundinsertfeedbacktask().execute(getString(R.string.insert_feedback));
                    } else
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                } else {
                    SweetAlertDialog builder1 = new SweetAlertDialog(getContext());
                    if (name.length() < 4) {
                        builder1.setContentText("Invalid Your Name!");

                    } else if (mobile.length() != 11 || !mobile.startsWith("03")) {
                        builder1.setContentText("Invalid Phone Number");
                    } else if (feedbackwrite.length() < 0) {
                        builder1.setContentText("Please Write the Feedback");
                    }else if (!email.contains("@gmail.com") || !email.contains("@hotmail.com") || !email.contains("@yahoo.com")) {
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

    private class Backgroundinsertfeedbacktask extends AsyncTask<String, Void, String> {
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
//    SELECT `ID`, `YourName`, `YourEmail`, `YourMobile`, `WriteFeedback` FROM `tbl_feedback` WHERE 1

                //Now Start the Output Stream.............
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("YourName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("YourEmail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("YourMobile", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8")
                        + "&" + URLEncoder.encode("WriteFeedback", "UTF-8") + "=" + URLEncoder.encode(feedbackwrite, "UTF-8");
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
            pDialog.dismiss();
            clearfeedabk();
            if (result != null) {
                //Toast.makeText(getActivity().getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(getActivity())
                        .setTitleText("" + result)
                        .show();

           /*     Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();*/
            }
        }
    }

    private void clearfeedabk() {
        yourname.setText("");
        yourphoneno.setText("");
        youremailid.setText("");
        writefeedback.setText("");
    }
}
