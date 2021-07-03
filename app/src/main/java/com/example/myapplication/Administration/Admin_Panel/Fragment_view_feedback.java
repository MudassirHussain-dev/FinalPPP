package com.example.myapplication.Administration.Admin_Panel;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Feedback_Getter_Setter;

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

public class Fragment_view_feedback extends Fragment {

    View view;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_feedback, null);
        progressDialog = new ProgressDialog(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_feedback);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            new Backgroundviewfeedbacktask().execute(getString(R.string.view_feedback));
        }else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Please Check Your own Connection!")
                    .show();
        }

        return view;
    }

    private class Backgroundviewfeedbacktask extends AsyncTask<String, Void, ArrayList<Feedback_Getter_Setter>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Feedback_Getter_Setter> doInBackground(String... strings) {
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

                ArrayList<Feedback_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
//SELECT `ID`, `YourName`, `YourEmail`, `YourMobile`, `WriteFeedback` FROM `tbl_feedback` WHERE 1
                    Feedback_Getter_Setter feedback_getter_setter = new Feedback_Getter_Setter();

                    feedback_getter_setter.setId(finalobject.getString("ID"));
                    feedback_getter_setter.setName(finalobject.getString("YourName"));
                    feedback_getter_setter.setEmail(finalobject.getString("YourEmail"));
                    feedback_getter_setter.setMobile(finalobject.getString("YourMobile"));
                    feedback_getter_setter.setWritefeedback(finalobject.getString("WriteFeedback"));

                    arrayList.add(feedback_getter_setter);
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
        protected void onPostExecute(ArrayList<Feedback_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    FeedbackCostomAdapter adapter = new FeedbackCostomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }

    private class FeedbackCostomAdapter extends RecyclerView.Adapter<FeedbackCostomAdapter.ViewHolder> {
        ArrayList<Feedback_Getter_Setter> arrayList;

        public FeedbackCostomAdapter(ArrayList<Feedback_Getter_Setter> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_feedback_row, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String id = arrayList.get(position).getId();
            final String name = arrayList.get(position).getName();
            final String email = arrayList.get(position).getEmail();
            final String mobile = arrayList.get(position).getMobile();
            final String feedbackwirte = arrayList.get(position).getWritefeedback();

            holder.id1.setText(id);
            holder.name.setText(name);
            holder.email.setText(email);
            holder.mobile.setText(mobile);
            holder.feedbackwrite.setText(feedbackwirte);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView id1, name, email, mobile, feedbackwrite;

            public ViewHolder(@NonNull View view) {
                super(view);
                id1 = view.findViewById(R.id.txt_feedbackid);
                name = view.findViewById(R.id.txt_feedbackname);
                email = view.findViewById(R.id.txt_feedbackemail);
                mobile = view.findViewById(R.id.txt_feedbackmobileno);
                feedbackwrite = view.findViewById(R.id.txt_feedbackwrtiedetail);

            }
        }
    }
}
