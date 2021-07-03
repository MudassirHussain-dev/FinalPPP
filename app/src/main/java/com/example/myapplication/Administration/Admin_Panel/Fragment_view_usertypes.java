package com.example.myapplication.Administration.Admin_Panel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Usertype_Getter_Setter;

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

public class Fragment_view_usertypes extends Fragment {
    View view;
    RecyclerView recyclerView;
    String user_id;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_usertype, null);
        progressDialog = new ProgressDialog(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_usertype);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        new Backgroundusertypetask().execute(getString(R.string.view_usertype));
        return view;
    }

    private class Backgroundusertypetask extends AsyncTask<String, Void, ArrayList<Usertype_Getter_Setter>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Usertype_Getter_Setter> doInBackground(String... strings) {
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

                ArrayList<Usertype_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
//SELECT `ID`, `FName`, `LName`, `Phone`, `Email`, `City`, `Address`, `CNIC`, `Password`, `UserType` FROM `tbl_reg` WHERE 1
                    Usertype_Getter_Setter usertype_getter_setter = new Usertype_Getter_Setter();
                    usertype_getter_setter.setFirstname(finalobject.getString("FName"));
                    usertype_getter_setter.setLastname(finalobject.getString("LName"));
                    usertype_getter_setter.setPhone(finalobject.getString("Phone"));
                    usertype_getter_setter.setEmail(finalobject.getString("Email"));
                    usertype_getter_setter.setCity(finalobject.getString("City"));
                    usertype_getter_setter.setAddress(finalobject.getString("Address"));
                    usertype_getter_setter.setCnic(finalobject.getString("CNIC"));
                    usertype_getter_setter.setPassword(finalobject.getString("Password"));
                    usertype_getter_setter.setUsertype(finalobject.getString("UserType"));
                    usertype_getter_setter.setId(finalobject.getString("ID"));


                    arrayList.add(usertype_getter_setter);
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

        protected void onPostExecute(ArrayList<Usertype_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    UsertypeCostomAdapter adapter = new UsertypeCostomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class UsertypeCostomAdapter extends RecyclerView.Adapter<UsertypeCostomAdapter.ViewHolder> {
        ArrayList<Usertype_Getter_Setter> arrayList;

        public UsertypeCostomAdapter(ArrayList<Usertype_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_usertype_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final String fname1 = arrayList.get(position).getFirstname();
            final String lname = arrayList.get(position).getLastname();
            final String phone = arrayList.get(position).getPhone();
            final String email = arrayList.get(position).getEmail();
            final String city = arrayList.get(position).getCity();
            final String address = arrayList.get(position).getAddress();
            final String cnic = arrayList.get(position).getCnic();
            final String password = arrayList.get(position).getPassword();
            final String usertype = arrayList.get(position).getUsertype();
            final String id = arrayList.get(position).getId();


            holder.fname.setText(fname1);
            holder.lname.setText(lname);
            holder.phone.setText(phone);
            holder.email.setText(email);
            holder.city.setText(city);
            holder.address.setText(address);
            holder.cnic.setText(cnic);
            holder.password.setText(password);
            holder.usertype.setText(usertype);
            holder.id.setText(id);
           /* holder.layourusertype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SELECT `ID`, `FName`, `LName`, `Phone`, `Email`, `City`, `Address`, `CNIC`, `Password`, `UserType` FROM `tbl_reg` WHERE 1
                    Bundle bundle = new Bundle();
                    bundle.putString("ID",id);
                    bundle.putString("FName",fname1);
                    bundle.putString("LName",lname);
                    bundle.putString("Phone",phone);
                    bundle.putString("Email",email);
                    bundle.putString("City",city);
                    bundle.putString("Address",address);
                    bundle.putString("CNIC",cnic);
                    bundle.putString("Password",password);
                    bundle.putString("UserType",usertype);

                    Intent intent = new Intent(getActivity(), Update_UserTypeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });*/

            holder.layourusertype.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("Confirmation");
                    builder.setTitle("DIalog");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        user_id =      holder.id.getText().toString();
                            new Backgrouddeleteusertypetask().execute(getString(R.string.deleteusrtype));
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView fname, lname, phone, email, city, address, cnic, password, usertype,id;
            LinearLayout layourusertype;

            public ViewHolder(@NonNull View view) {
                super(view);
                fname = view.findViewById(R.id.txt_user_firstname);
                lname = view.findViewById(R.id.txt_usertype_secondname);
                phone = view.findViewById(R.id.txt_usertype_phone);
                email = view.findViewById(R.id.txt_usertype_email);
                address = view.findViewById(R.id.txt_usertype_address);
                city = view.findViewById(R.id.txt_usertype_city);
                cnic = view.findViewById(R.id.txt_usertype_cnic);
                password = view.findViewById(R.id.txt_usertype_password);
                usertype = view.findViewById(R.id.txt_usertype_no);
                layourusertype = view.findViewById(R.id.layoutusertype);
                id = view.findViewById(R.id.txt_usertype_ID);
            }
        }
    }

    private class Backgrouddeleteusertypetask extends AsyncTask<String, Void, String> {

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

                //OutPut Stream methods(only three)
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(user_id,"UTF-8")
                        + "&" + URLEncoder.encode("dummy", "UTF-8") + "=" + URLEncoder.encode("dum", "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
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
                Toast.makeText(getActivity().getApplicationContext(), result + "", Toast.LENGTH_SHORT).show();
                new Backgroundusertypetask().execute(getString(R.string.view_usertype));

            }
        }
    }
}





