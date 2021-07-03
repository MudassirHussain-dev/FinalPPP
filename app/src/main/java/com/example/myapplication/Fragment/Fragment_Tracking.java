package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.baoyachi.stepview.VerticalStepView;
import com.example.myapplication.R;

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
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_Tracking extends Fragment {
    View view;
    Button btntrack;
    EditText et_article;
    ProgressDialog progressDialog;
    //AlertDialog dialog;
    ImageView home_logo;
    ArrayList<String> list0;
    Spinner spinner_sevice_name;
    private static final String RegisterURl = "http://www.dabonjour.com/Mudassir/get_all_tracking.php";
    SweetAlertDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tracking, container, false);

        btntrack = (Button) view.findViewById(R.id.btn_track);
        spinner_sevice_name = (Spinner) view.findViewById(R.id.spinner_services);
        et_article = (EditText) view.findViewById(R.id.et_article_number);

        home_logo = (ImageView) view.findViewById(R.id.home_logo);


        //progressDialog = new ProgressDialog(getContext());
        pDialog = new SweetAlertDialog(getContext());

        btntrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_article.getText().toString().trim().length() > 0) {
                    ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected() == true) {
                        // Toast.makeText(getContext(), "Network Available", Toast.LENGTH_LONG).show();
                        new Bakcgroudtask().execute(RegisterURl);

                    } else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please Check Your own Connection!")
                                .show();
                        // Toast.makeText(getContext(), "Network Not Available", Toast.LENGTH_LONG).show();

                    }


                } else {
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please Enter Article Number!")
                            .show();
                    // Toast.makeText(getActivity(), "Please Enter Article Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }


    private class Bakcgroudtask extends AsyncTask<String, Void, ArrayList> {
        String article_number = et_article.getText().toString();
        String service_name = spinner_sevice_name.getSelectedItem().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* SweetAlertDialog*/ pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();
           /* progressDialog.setMessage("Please waite...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show()*/
            ;

        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                //OutPut Stream methods(only three)
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("Track_ID", "UTF-8") + "=" + URLEncoder.encode(service_name + article_number, "UTF-8")
                        + "&" + URLEncoder.encode("dummy", "UTF-8") + "=" + URLEncoder.encode("dum", "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //input stream methods...((3) and Line & While and also JASON and also string )

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuffer stringBuffer = new StringBuffer();

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                //now tech and Jason
                String finalString = stringBuffer.toString();
                list0 = new ArrayList<>();
                //ArrayList arrayList = new ArrayList<>();
                list0.clear();
                if (finalString.trim().charAt(0) == '{') {
                    JSONObject jsonObject = new JSONObject(finalString);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject finalobject = jsonArray.getJSONObject(i);
                        //Delivery_office`, `Bookig_office`, `Description`
                        list0.add("Parcel ID:  " + finalobject.getString("Track_ID") + "\n" + " Date-Time:  " + finalobject.getString("Datee") + " || " + finalobject.getString("Timee") +
                                "\n" + "Current_Postion:  " + finalobject.getString("Delivery_office") /*+ "\n"+"Sender City"+ finalobject.getString("Bookig_office")*/);
                    }
                    return list0;
                } else {
                    list0.add("Invalid!");
                    return list0;
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
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            // Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();
            if (result != null) {

                getverticaldetail();

            }

        }


    }

    private void getverticaldetail() {
        VerticalStepView mSetpview0 = (VerticalStepView) view.findViewById(R.id.step_viewvertical);
       /*     list0 = new ArrayList<>();
            list0.add("Step 1");
            list0.add("Step 2");
            list0.add("Step 3");
            list0.add("Step 4");
            list0.add("Step 5");
            list0.add("Step 6");
            list0.add("Step 7");
            list0.add("Step 8");
            list0.add("Step 9,hello your order is completed");
            list0.add("Step 10,hello your order is completed");*/
        mSetpview0.setStepsViewIndicatorComplectingPosition(list0.size())
                .reverseDraw(false)//default is true
                .setStepViewTexts(list0)
                .setLinePaddingProportion(0.85f)//indicator
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.white))//StepsViewIndicator
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.white1))//StepsViewIndicator
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.white))//StepsView text
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))//StepsView text
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_city))//StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))//StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//StepsViewIndicator AttentionIcon

    }
}



