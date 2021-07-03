package com.example.myapplication.Administration.Admin_Panel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.View_Parcel_Getter_Setter;

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
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_view_parcel extends Fragment {
    View view;
    RecyclerView recyclerView;
    // ProgressDialog progressDialog;
    SearchView searchView;
    CostomAdapter adapter;
    SweetAlertDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_parcle, null);

        //progressDialog = new ProgressDialog(getContext());
        pDialog = new SweetAlertDialog(getContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.Recyler_viewParcle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        searchView = (SearchView) view.findViewById(R.id.viewparcel_serachview);
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
        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            new Backgroundviewparcletask().execute(getString(R.string.view_parcel_registerd));
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Please Check Your own Connection!")
                    .show();
        }
        return view;
    }

    private class Backgroundviewparcletask extends AsyncTask<String, Void, ArrayList<View_Parcel_Getter_Setter>> {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREF_FILE), Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("Email", null);
        String usertype = sharedPreferences.getString("UserType", null);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected ArrayList<View_Parcel_Getter_Setter> doInBackground(String... strings) {
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

                ArrayList<View_Parcel_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
// `ID`, `Track_ID`, `Datee`, `Timee`, `Delivery_office`, `Bookig_office`, `Target_Office`, `Description` FROM `tbl_tracking
                    View_Parcel_Getter_Setter view_parcel_getter_setter = new View_Parcel_Getter_Setter();
                    view_parcel_getter_setter.setID(finalobject.getString("ID"));
                    view_parcel_getter_setter.setArticleno(finalobject.getString("Track_ID"));
                    view_parcel_getter_setter.setBooffice(finalobject.getString("Bookig_office"));
                    view_parcel_getter_setter.setDestoffice(finalobject.getString("Target_Office"));
                    view_parcel_getter_setter.setBokingtime(finalobject.getString("Timee"));
                    view_parcel_getter_setter.setBokingdate(finalobject.getString("Datee"));
                    view_parcel_getter_setter.setMobile(finalobject.getString("Mobile_no"));
                    view_parcel_getter_setter.setDeoffice(finalobject.getString("Delivery_office"));
                    view_parcel_getter_setter.setEmail(finalobject.getString("Email_Id"));
                    view_parcel_getter_setter.setCustomername(finalobject.getString("CustomerName"));


                    arrayList.add(view_parcel_getter_setter);
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

        protected void onPostExecute(ArrayList<View_Parcel_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            pDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    adapter = new CostomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class CostomAdapter extends RecyclerView.Adapter<CostomAdapter.ViewHolder> implements Filterable {
        ArrayList<View_Parcel_Getter_Setter> arrayList;
        ArrayList<View_Parcel_Getter_Setter> parcelSearch;

        public CostomAdapter(ArrayList<View_Parcel_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
            parcelSearch = new ArrayList<>(arrayList);
        }

        @NonNull
        @Override
        public CostomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_parcel_register_rows, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CostomAdapter.ViewHolder holder, int position) {
            final String id = arrayList.get(position).getID();
            final String aticlenumber = arrayList.get(position).getArticleno();
            final String timee = arrayList.get(position).getBokingtime();
            final String datee = arrayList.get(position).getBokingdate();
            final String deliveryoffice = arrayList.get(position).getDeoffice();
            final String bookingoffice = arrayList.get(position).getBooffice();
            final String targetoffice = arrayList.get(position).getDestoffice();
            final String mobileno = arrayList.get(position).getMobile();
            final String email = arrayList.get(position).getEmail();
            final String name = arrayList.get(position).getCustomername();


            holder.id.setText(id);
            holder.articlenumber.setText(aticlenumber);
            holder.destination.setText(mobileno);
            holder.email.setText(email);
            holder.layout_parceldetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", id);
                    bundle.putString("Track_ID", aticlenumber);
                    bundle.putString("Datee", datee);
                    bundle.putString("Timee", timee);
                    bundle.putString("Delivery_office", deliveryoffice);
                    bundle.putString("Bookig_office", bookingoffice);
                    bundle.putString("Target_Office", targetoffice);
                    bundle.putString("Mobile_no", mobileno);
                    bundle.putString("Email_Id", email);
                    bundle.putString("CustomerName", name);

                    Intent intent = new Intent(getActivity(), ParcelDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public Filter getFilter() {

            return parcelfilter;
        }

        private Filter parcelfilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<View_Parcel_Getter_Setter> view_parcel_getter_setters = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    view_parcel_getter_setters.addAll(parcelSearch);
                } else {
                    String writefilterpattern = constraint.toString().toLowerCase().trim();
                    for (View_Parcel_Getter_Setter item : parcelSearch) {
                        if (item.getArticleno().toLowerCase().contains(writefilterpattern)) {
                            view_parcel_getter_setters.add(item);
                        }else if (item.getArticleno().toLowerCase().contains(writefilterpattern)) {
                            view_parcel_getter_setters.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = view_parcel_getter_setters;
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
            TextView id, articlenumber, destination, email;
            LinearLayout layout_parceldetail;

            public ViewHolder(@NonNull View view) {
                super(view);
                id = view.findViewById(R.id.txt_parcle_ID_one);
                articlenumber = view.findViewById(R.id.txt_parcle_article_number_one);
                destination = view.findViewById(R.id.txt_destination_parcel_office_one);
                email = view.findViewById(R.id.txt_registerby_withemail);
                layout_parceldetail = view.findViewById(R.id.layout_parceldetail);

            }
        }
    }
}