package com.example.myapplication.Pickupservices;

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
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Pickuporderbycall_Getter_Setter;

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
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_parcel_pickup_request_by_call extends Fragment {
    View view;
    RecyclerView recyclerView;
    SearchView searchView;
    PickupCostomAdapter adapter;
    //ProgressDialog progressDialog;
    SweetAlertDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call_order_pickkupservices, null);

        //  progressDialog= new ProgressDialog(getContext());
        pDialog = new SweetAlertDialog(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.Recyler_view_pickup_services);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        new BackgroundPickuporderbycall().execute(getString(R.string.view_pickup_order_by_call));

        searchView = (SearchView) view.findViewById(R.id.pickup_serachview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    adapter.getFilter().filter(newText);
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please Check Your own Connection!")
                            .show();
                }
                return false;
            }
        });
        return view;
    }

    private class BackgroundPickuporderbycall extends AsyncTask<String, Void, ArrayList<Pickuporderbycall_Getter_Setter>> {
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
        protected ArrayList<Pickuporderbycall_Getter_Setter> doInBackground(String... strings) {
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

                ArrayList<Pickuporderbycall_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
//SELECT `ID`, `City`, `Contact_Person`, `Landline_no`, `Mobile_no`, `Email`, `Location` FROM `tbl_pickup_parcel` WHERE 1
                    Pickuporderbycall_Getter_Setter pickuporderbycall_getter_setter = new Pickuporderbycall_Getter_Setter();

                    pickuporderbycall_getter_setter.setCity(finalobject.getString("City"));
                    pickuporderbycall_getter_setter.setName(finalobject.getString("Contact_Person"));
                    pickuporderbycall_getter_setter.setLandline(finalobject.getString("Landline_no"));
                    pickuporderbycall_getter_setter.setMobile(finalobject.getString("Mobile_no"));
                    pickuporderbycall_getter_setter.setEmail(finalobject.getString("Email"));
                    pickuporderbycall_getter_setter.setLocation(finalobject.getString("Location"));

                    arrayList.add(pickuporderbycall_getter_setter);
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

        @Override
        protected void onPostExecute(ArrayList<Pickuporderbycall_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            pDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    adapter = new PickupCostomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class PickupCostomAdapter extends RecyclerView.Adapter<PickupCostomAdapter.ViewHolder> implements Filterable {
        ArrayList<Pickuporderbycall_Getter_Setter> arrayList;
        ArrayList<Pickuporderbycall_Getter_Setter> listforsearch;

        public PickupCostomAdapter(ArrayList<Pickuporderbycall_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
            listforsearch = new ArrayList<>(arrayList);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_pickup_services_rows, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String city = arrayList.get(position).getCity();
            final String name = arrayList.get(position).getName();
            final String landlineno = arrayList.get(position).getLandline();
            final String mobile = arrayList.get(position).getMobile();
            final String email = arrayList.get(position).getEmail();
            final String location = arrayList.get(position).getLocation();

            holder.city.setText(city);
            holder.name.setText(name);
            holder.landline.setText(landlineno);
            holder.mobile.setText(mobile);
            holder.email.setText(email);
            holder.location.setText(location);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public Filter getFilter() {
            return examplefilter;
        }

        private Filter examplefilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Pickuporderbycall_Getter_Setter> firstlist = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    firstlist.addAll(listforsearch);
                } else {
                    String filterpattern = constraint.toString().toLowerCase().trim();

                    for (Pickuporderbycall_Getter_Setter item : listforsearch) {
                        if (item.getCity().toLowerCase().contains(filterpattern)) {
                            firstlist.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = firstlist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                arrayList.clear();
                arrayList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView city, name, landline, mobile, location, email;

            public ViewHolder(@NonNull View view) {
                super(view);
                city = (TextView) view.findViewById(R.id.txt_pickup_bycall_city);
                name = (TextView) view.findViewById(R.id.txt_pickup_bycall_name);
                landline = (TextView) view.findViewById(R.id.txt_pickup_bycall_landno);
                mobile = (TextView) view.findViewById(R.id.txt_pickup_bycall_mobile);
                location = (TextView) view.findViewById(R.id.txt_pickup_bycall_location);
                email = (TextView) view.findViewById(R.id.txt_pickup_bycall_email);

            }
        }
    }
}
