package com.example.myapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Post_Codes_Getter_Setter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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


public class Fragment_post_codes extends Fragment {
    View view;
    RecyclerView recyclerView;
    Spinner search_city_codes;
    Button btn_postcodes_Search;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_codes, container, false);
        progressDialog = new ProgressDialog(getContext());

        btn_postcodes_Search = (Button) view.findViewById(R.id.btn_pstcodes_search);
        search_city_codes = (Spinner) view.findViewById(R.id.seaching_city);

        recyclerView = (RecyclerView) view.findViewById(R.id.Recyler_view_postcodes);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
       // new Backgroundsearch().execute(getString(R.string.get_posctcoes));


        btn_postcodes_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                new Backgroundsearch().execute(getString(R.string.get_posctcoes));
            }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please Check Your own Connection!")
                            .show();
                    // Toast.makeText(getContext(), "Please Check Your own Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    private class Backgroundsearch extends AsyncTask<String, Void, ArrayList<Post_Codes_Getter_Setter>> {
        String city_names = search_city_codes.getSelectedItem().toString();
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        @Override
        protected ArrayList<Post_Codes_Getter_Setter> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("City_name", "UTF-8") + "=" + URLEncoder.encode(city_names, "UTF-8")
                        + "&" + URLEncoder.encode("dummy", "UTF-8") + "=" + URLEncoder.encode("dummy", "UTF-8");
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

                ArrayList<Post_Codes_Getter_Setter> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
// "City_name"=>$row[1],"Post_codes"=>$row[2],"Post_Office"=>$row[3],"Province"=>$row[4])

                    Post_Codes_Getter_Setter post_codes_getter_setter = new Post_Codes_Getter_Setter();

                    post_codes_getter_setter.setPostcity(finalobject.getString("City_name"));
                    post_codes_getter_setter.setPOst_Office(finalobject.getString("Post_Office"));
                    post_codes_getter_setter.setPost_codes(finalobject.getString("Post_codes"));
                    post_codes_getter_setter.setProvince(finalobject.getString("Province"));
                    post_codes_getter_setter.setRelated_Gpo(finalobject.getString("Related_Gpo"));

                    arrayList.add(post_codes_getter_setter);
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

        protected void onPostExecute(ArrayList<Post_Codes_Getter_Setter> arrayList) {
            super.onPostExecute(arrayList);
            progressDialog.dismiss();
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    PostcodesCustomAdapter adapter = new PostcodesCustomAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }

        private class PostcodesCustomAdapter extends RecyclerView.Adapter<PostcodesCustomAdapter.ViewHolder> {
            ArrayList<Post_Codes_Getter_Setter> arrayList;

            public PostcodesCustomAdapter(ArrayList<Post_Codes_Getter_Setter> arrayList) {
                this.arrayList = arrayList;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.view_postcodes_rows, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                final String postoffice = arrayList.get(position).getPOst_Office();
                final String codes = arrayList.get(position).getPost_codes();
                final String relatedgpo = arrayList.get(position).getRelated_Gpo();
                final String province = arrayList.get(position).getProvince();
                final String city = arrayList.get(position).getPostcity();


                holder.codes.setText(codes);
                holder.postoffice.setText(postoffice);
                holder.provinces.setText(province);
                holder.relatedgpo.setText(relatedgpo);
               // holder.city.setText(city);

            }

            @Override
            public int getItemCount() {
                return arrayList.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView postoffice, codes, relatedgpo, provinces,Related_Gpo,city;

                public ViewHolder(@NonNull View view) {
                    super(view);

                    postoffice = (TextView) view.findViewById(R.id.txt_postoffice);
                    codes = (TextView) view.findViewById(R.id.txt_codes);
                    relatedgpo = (TextView) view.findViewById(R.id.txt_relatedgpo);
                    provinces = (TextView) view.findViewById(R.id.txt_province);
                    Related_Gpo=(TextView)view.findViewById(R.id.txt_relatedgpo);
                    //city=(TextView)view.findViewById(R.id.txt_postcodescity);
                }
            }
        }
    }
}
