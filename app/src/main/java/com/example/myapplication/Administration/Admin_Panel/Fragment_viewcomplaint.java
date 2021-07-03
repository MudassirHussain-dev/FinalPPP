package com.example.myapplication.Administration.Admin_Panel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.View_Complaint_Getter_Setter;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_viewcomplaint extends Fragment {
    View view;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_complaaint, null);

        progressDialog = new ProgressDialog(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.Recyler_complaint);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {

            new viewcomplainttask().execute(getString(R.string.get_view_compliant));
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Please Check Your own Connection!")
                    .show();
        }
        return view;
    }

    private class viewcomplainttask extends AsyncTask<String, Void, ArrayList<View_Complaint_Getter_Setter>> {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREF_FILE), Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", null);
        String usertype = sharedPreferences.getString("UserType", null);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<View_Complaint_Getter_Setter> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("Usertype", "UTF-8") + "=" + URLEncoder.encode(usertype, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = "";
                StringBuffer buffer = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalstring = buffer.toString();
                JSONObject jsonObject = new JSONObject(finalstring);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                ArrayList<View_Complaint_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);

                    View_Complaint_Getter_Setter view_complaint_getter_setter = new View_Complaint_Getter_Setter();

                    view_complaint_getter_setter.setId(finalobject.getString("id"));
                    view_complaint_getter_setter.setServiceName(finalobject.getString("service_name"));
                    view_complaint_getter_setter.setArticleNumber(finalobject.getString("article_number"));
                    view_complaint_getter_setter.setComplaintName(finalobject.getString("cName"));
                    view_complaint_getter_setter.setAddEmail(finalobject.getString("adEmail"));
                    view_complaint_getter_setter.setAddMobile(finalobject.getString("adMobile"));
                    view_complaint_getter_setter.setAddAddress(finalobject.getString("adAddress"));
                    view_complaint_getter_setter.setSenderAddress(finalobject.getString("s_address"));
                    view_complaint_getter_setter.setSenderEmail(finalobject.getString("s_email"));
                    view_complaint_getter_setter.setSenderCity(finalobject.getString("s_city"));
                    view_complaint_getter_setter.setBookingoffice(finalobject.getString("booking_office"));
                    view_complaint_getter_setter.setComplaintcity(finalobject.getString("cCity"));
                    view_complaint_getter_setter.setSenderName(finalobject.getString("s_name"));
                    view_complaint_getter_setter.setAddName(finalobject.getString("adName"));
                    view_complaint_getter_setter.setAddCity(finalobject.getString("adCity"));
                    view_complaint_getter_setter.setDeliveryoffice(finalobject.getString("deliviery_officee"));
                    view_complaint_getter_setter.setSenderMobile(finalobject.getString("s_mobile"));
                    view_complaint_getter_setter.setComplaintmobile(finalobject.getString("cMobile"));
                    view_complaint_getter_setter.setBookingtime(finalobject.getString("booking_time"));
                    view_complaint_getter_setter.setBookingdate(finalobject.getString("booking_date"));

                    arrayList.add(view_complaint_getter_setter);
                }
                return arrayList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<View_Complaint_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    ComtomerAdapter adapter = new ComtomerAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class ComtomerAdapter extends RecyclerView.Adapter<ComtomerAdapter.ViewHolder> {
        ArrayList<View_Complaint_Getter_Setter> arrayList;

        public ComtomerAdapter(ArrayList<View_Complaint_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.complaint_rows, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String id = arrayList.get(position).getId();
            final String serviceName = arrayList.get(position).getServiceName();
            final String articl_no = arrayList.get(position).getArticleNumber();
            final String complainterName = arrayList.get(position).getComplaintName();
            final String cosMobile = arrayList.get(position).getComplaintmobile();
            final String bbokingdate = arrayList.get(position).getBookingdate();
            final String bookingtime = arrayList.get(position).getBookingtime();
            final String boling_office = arrayList.get(position).getBookingoffice();
            final String deoffice = arrayList.get(position).getDeliveryoffice();
            final String comCity = arrayList.get(position).getComplaintcity();
            final String send_name = arrayList.get(position).getSenderName();
            final String sendermobile = arrayList.get(position).getSenderMobile();
            final String senderEmail = arrayList.get(position).getSenderEmail();
            final String sendercity = arrayList.get(position).getSenderCity();
            final String senders_addresss = arrayList.get(position).getSenderAddress();
            final String addnamme = arrayList.get(position).getAddName();
            final String addmobile = arrayList.get(position).getAddMobile();
            final String addemail = arrayList.get(position).getAddEmail();
            final String addcity = arrayList.get(position).getAddCity();
            final String addAddress = arrayList.get(position).getAddAddress();


            holder.servicename.setText(serviceName);
            holder.cid.setText(id);
            holder.articleNumber.setText(articl_no);
            holder.bookingoffice.setText(boling_office);
            holder.senders_addresss.setText(senders_addresss);
            holder.send_name.setText(send_name);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();

                    bundle.putString("id", id);
                    bundle.putString("service_name", serviceName);
                    bundle.putString("article_number", articl_no);
                    bundle.putString("cName", complainterName);
                    bundle.putString("adEmail", addemail);
                    bundle.putString("adMobile", addmobile);
                    bundle.putString("adAddress", addAddress);
                    bundle.putString("s_address", senders_addresss);
                    bundle.putString("s_email", senderEmail);
                    bundle.putString("s_city", sendercity);
                    bundle.putString("booking_office", boling_office);
                    bundle.putString("cCity", comCity);
                    bundle.putString("s_name", send_name);
                    bundle.putString("adName", addnamme);
                    bundle.putString("adCity", addcity);
                    bundle.putString("deliviery_officee", deoffice);
                    bundle.putString("s_mobile", sendermobile);
                    bundle.putString("cMobile", cosMobile);
                    bundle.putString("booking_time", bookingtime);
                    bundle.putString("booking_date", bbokingdate);

                    Intent intent = new Intent(getActivity(), ComplaintsDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView servicename, articleNumber, send_name, senders_addresss, bookingoffice, cid;
            LinearLayout linearLayout;

            public ViewHolder(@NonNull View view) {
                super(view);

                servicename = view.findViewById(R.id.txt_complaint_service_number_one);
                articleNumber = view.findViewById(R.id.txt_complaint_article_number_one);
                send_name = view.findViewById(R.id.txt_sender_Name_one);
                senders_addresss = view.findViewById(R.id.txt_sender_address_one);
                bookingoffice = view.findViewById(R.id.txt_booking_complaint_office_one);
                cid = view.findViewById(R.id.txt_complaint_ID_one);

                linearLayout = view.findViewById(R.id.my_layout);
            }
        }
    }
}
