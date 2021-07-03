package com.example.myapplication.Administration.Admin_Panel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.model.Online_Getter_Setter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_view_order extends Fragment {
    View view;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_order, container, false);

        progressDialog = new ProgressDialog(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_pickup_onlineorder);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            new Backgroundonlinepickuptask().execute(getString(R.string.view_online_order));
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Please Check Your own Connection!")
                    .show();
        }

        return view;
    }


    private class Backgroundonlinepickuptask extends AsyncTask<String, Void, ArrayList<Online_Getter_Setter>> {

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Online_Getter_Setter> doInBackground(String... strings) {

            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);


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

                ArrayList<Online_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);

                    Online_Getter_Setter online_getter_setter = new Online_Getter_Setter();
//SELECT `ID`, `Time`, `Date`, `ServiceName`, `FullName`, `CNIC`, `Sender_Phone`,
// `Receiver_Phone`, `Email`, `Pickup_City`, `Transfer_City`, `Sub_City`, `Near_PostOffice`, `FullAddress` FROM `tbl_pickup_order` WHERE 1
                    online_getter_setter.setId(finalobject.getString("ID"));
                    online_getter_setter.setName(finalobject.getString("FullName"));
                    online_getter_setter.setServicename(finalobject.getString("ServiceName"));
                    online_getter_setter.setPickupcity(finalobject.getString("Pickup_City"));
                    online_getter_setter.setTransfercity(finalobject.getString("Transfer_City"));
                    online_getter_setter.setSubcity(finalobject.getString("Sub_City"));
                    online_getter_setter.setDate(finalobject.getString("Date"));
                    online_getter_setter.setTime(finalobject.getString("Time"));
                    online_getter_setter.setFulladdress(finalobject.getString("FullAddress"));
                    online_getter_setter.setSphone(finalobject.getString("Sender_Phone"));
                    online_getter_setter.setRphone(finalobject.getString("Receiver_Phone"));
                    online_getter_setter.setNearpostoffice(finalobject.getString("Near_PostOffice"));
                    online_getter_setter.setEmail(finalobject.getString("Email"));
                    online_getter_setter.setCnic(finalobject.getString("CNIC"));


                    arrayList.add(online_getter_setter);
                }
                return arrayList;

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(ArrayList<Online_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    OnlinepickuporderCostomAdapter adapter = new OnlinepickuporderCostomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class OnlinepickuporderCostomAdapter extends RecyclerView.Adapter<OnlinepickuporderCostomAdapter.ViewHolder> {
        ArrayList<Online_Getter_Setter> arrayList;

        public OnlinepickuporderCostomAdapter(ArrayList<Online_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_pickup_order_byrequest_row, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String orderid = arrayList.get(position).getId();
            final String servicename = arrayList.get(position).getServicename();
            final String name = arrayList.get(position).getName();
            final String time = arrayList.get(position).getTime();
            final String date = arrayList.get(position).getDate();
            final String pickupcity = arrayList.get(position).getPickupcity();
            final String transfercity = arrayList.get(position).getTransfercity();
            final String senderphoneno = arrayList.get(position).getSphone();
            final String receiverphoneno = arrayList.get(position).getRphone();
            final String subcity = arrayList.get(position).getSubcity();
            final String cnic = arrayList.get(position).getCnic();
            final String emal = arrayList.get(position).getEmail();
            final String nearpostoffice = arrayList.get(position).getNearpostoffice();
            final String address = arrayList.get(position).getFulladdress();

            holder.id.setText(orderid);
            holder.servicename.setText(servicename);
            holder.name.setText(name);
            holder.time.setText(time);
            holder.date.setText(date);
            holder.pickupcity.setText(pickupcity);
            holder.transfercity.setText(transfercity);
         /*   bundle.putString("Target_Office", targetoffice);
            bundle.putString("Mobile_no", mobileno);
ID`, `Time`, `Date`, `ServiceName`, `FullName`, `CNIC`, `Sender_Phone`, `Receiver_Phone`, `Email`,
//`Pickup_City`, `Transfer_City`, `Sub_City`, `Near_PostOffice`, `FullAddress`
            Intent intent = new Intent(getActivity(), ParcelDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);*/
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", orderid);
                    bundle.putString("Time", time);
                    bundle.putString("Date", date);
                    bundle.putString("ServiceName", servicename);
                    bundle.putString("FullName", name);
                    bundle.putString("CNIC", cnic);
                    bundle.putString("Sender_Phone", senderphoneno);
                    bundle.putString("Receiver_Phone", receiverphoneno);
                    bundle.putString("Email", emal);
                    bundle.putString("Pickup_City", pickupcity);
                    bundle.putString("Transfer_City", transfercity);
                    bundle.putString("Sub_City", subcity);
                    bundle.putString("Near_PostOffice", nearpostoffice);
                    bundle.putString("FullAddress", address);

                    Intent intent = new Intent(getActivity(), ViewOrderDetailActivity.class);
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
            TextView id, servicename, name, time, date, pickupcity, transfercity;
            LinearLayout linearLayout;

            public ViewHolder(@NonNull View view) {
                super(view);
                id = view.findViewById(R.id.txt_pickuporderid);
                servicename = view.findViewById(R.id.txt_user_pickuporderservicetype);
                name = view.findViewById(R.id.txt_user_pickupordername);
                time = view.findViewById(R.id.txt_user_pickuporderTime);
                date = view.findViewById(R.id.txt_user_pickuporderdate);
                pickupcity = view.findViewById(R.id.txt_user_pickuporderpickupcity);
                transfercity = view.findViewById(R.id.txt_user_pickupordertransfercity);
                linearLayout = view.findViewById(R.id.layout_view_order_detail);
            }
        }
    }
}

