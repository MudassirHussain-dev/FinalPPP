package com.example.myapplication.Fragment;

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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Contact_Us_Getter_Setter;
import com.squareup.picasso.Picasso;

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
import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_contact_us extends Fragment {
    View view;
    RecyclerView recyclerView;
    Spinner spinner_contact_us_city;
    SweetAlertDialog pDialog;
    String city_name = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contact_us_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        pDialog = new SweetAlertDialog(getActivity());

        spinner_contact_us_city = (Spinner) view.findViewById(R.id.contact_us_city_spinner);

        //  new Backgroundtask().execute(getString(R.string.getContactUs));
        spinner_contact_us_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city_name = spinner_contact_us_city.getSelectedItem().toString();
                ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                new Backgroundtask().execute(getString(R.string.getContactUs));
                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please Check Your own Connection!")
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private class Backgroundtask extends AsyncTask<String, Void, ArrayList<Contact_Us_Getter_Setter>> {
        // String seclectCityContactUs=spinner_contact_us_city.getSelectedItem().toString();


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
        protected ArrayList<Contact_Us_Getter_Setter> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);


                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("CityName", "UTF-8") + "=" + URLEncoder.encode(city_name, "UTF-8") + "&" +
                        URLEncoder.encode("dummy", "UTF-8") + "=" + URLEncoder.encode("dummy", "UTF-8");
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

                ArrayList<Contact_Us_Getter_Setter> arrayList = new ArrayList<>();
                arrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);

                    Contact_Us_Getter_Setter contact_us_getter_setter = new Contact_Us_Getter_Setter();
                    contact_us_getter_setter.setName(finalobject.getString("Name"));
                    contact_us_getter_setter.setPost(finalobject.getString("Post"));
                    contact_us_getter_setter.setFunction(finalobject.getString("Function"));
                    contact_us_getter_setter.setAddress(finalobject.getString("Address"));
                    contact_us_getter_setter.setPhone_number(finalobject.getString("Phone"));
                    contact_us_getter_setter.setImage(finalobject.getString("Image"));

                    arrayList.add(contact_us_getter_setter);
                }

                return arrayList;

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
        protected void onPostExecute(ArrayList<Contact_Us_Getter_Setter> contact_us_getter_setters) {
            super.onPostExecute(contact_us_getter_setters);
            pDialog.dismiss();
            if (contact_us_getter_setters != null) {
                if (contact_us_getter_setters.size() > 0) {
                    Customadapter adapter = new Customadapter(contact_us_getter_setters);
                    recyclerView.setAdapter(adapter);
                }
            }


        }
    }

    private class Customadapter extends RecyclerView.Adapter<Customadapter.ViewHolder> {
        ArrayList<Contact_Us_Getter_Setter> arrayList;

        public Customadapter(ArrayList<Contact_Us_Getter_Setter> contact_us_getter_setters) {
            this.arrayList = contact_us_getter_setters;
        }

        @NonNull
        @Override
        public Customadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.contact_us_row_design, parent, false);
            return new Customadapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Customadapter.ViewHolder holder, int i) {
            String officer_name = arrayList.get(i).getName();
            String officer_post = arrayList.get(i).getPost();
            String officer_function = arrayList.get(i).getFunction();
            String officer_address = arrayList.get(i).getAddress();
            String officer_number = arrayList.get(i).getPhone_number();
            String urll = arrayList.get(i).getImage();

            holder.txt_name.setText(officer_name);
            holder.txt_post.setText(officer_post);
            holder.txt_function.setText(officer_function);
            holder.txt_address.setText(officer_address);
            holder.txt_phone.setText(officer_number);
            String imglink = getString(R.string.root_ip) + urll;

            Picasso.with(getContext()).load(imglink).into(holder.img_contact);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_name, txt_post, txt_phone, txt_address, txt_function;
            CircleImageView img_contact;


            public ViewHolder(@NonNull View view) {
                super(view);

                txt_name = (TextView) view.findViewById(R.id.officer_name);
                txt_phone = (TextView) view.findViewById(R.id.officer_number);
                txt_address = (TextView) view.findViewById(R.id.officer_address);
                txt_post = (TextView) view.findViewById(R.id.txt_post);
                txt_function = (TextView) view.findViewById(R.id.function_name);
                img_contact = (CircleImageView) view.findViewById(R.id.img_contactus);

            }
        }
    }
}






