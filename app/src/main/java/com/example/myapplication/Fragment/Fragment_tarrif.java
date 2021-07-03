package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragment_tarrif extends Fragment {
    View view;
    Spinner spinner_caculate_tarrif;
    ArrayList arr_caculate_tarrif;
    ArrayAdapter adapter_arr_caculate_tarrif;
    Button btn_tarrif_caculate;
    EditText et_calculate_tarrif;
    ImageView home_logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tarrif, container, false);

        // showdata();

        btn_tarrif_caculate = (Button) view.findViewById(R.id.btn_tarrif_caculate);
        et_calculate_tarrif = (EditText) view.findViewById(R.id.et_calculate_tarrif);
        spinner_caculate_tarrif = (Spinner) view.findViewById(R.id.spinner_calculate_tarrif);

/*        arr_caculate_tarrif=new ArrayList();
        arr_caculate_tarrif.add("UMS");
        arr_caculate_tarrif.add("EMS");
        arr_caculate_tarrif.add("FMS");
        arr_caculate_tarrif.add("COD");
        arr_caculate_tarrif.add("EMS_PES");
        arr_caculate_tarrif.add("SDD");
        arr_caculate_tarrif.add("FMO");
        arr_caculate_tarrif.add("EMO");
         adapter_arr_caculate_tarrif=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,arr_caculate_tarrif);
        spinner_caculate_tarrif.setAdapter(adapter_arr_caculate_tarrif);
        */

        btn_tarrif_caculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {

                String spinner_tarrif_string = spinner_caculate_tarrif.getSelectedItem().toString();
                if (et_calculate_tarrif.getText().toString().trim().length() > 0) {
                    int weight = Integer.valueOf(et_calculate_tarrif.getText().toString());

                    if (spinner_tarrif_string.equals("UMS")) {
                        if (weight >= 1 && weight <= 500) {

                            getalertdialog("Rs:50");
                            //Toast.makeText(getActivity(), "Rs:59", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 501 && weight <= 1000) {
                            getalertdialog("Rs:70");
                            // Toast.makeText(getActivity(), "Rs:70", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 1001 && weight <= 1500) {
                            getalertdialog("Rs:90");
                            //Toast.makeText(getActivity(), "Rs:90", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 1501 && weight <= 2000) {
                            getalertdialog("Rs:110");
                            //Toast.makeText(getActivity(), "Rs:110", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 2001 && weight <= 2500) {
                            getalertdialog("Rs:130");
                            //Toast.makeText(getActivity(), "Rs:130", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 2501 && weight <= 3000) {
                            getalertdialog("Rs:150");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 3001 && weight <= 3500) {
                            getalertdialog("Rs:170");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 3501 && weight <= 4000) {
                            getalertdialog("Rs:190");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 4001 && weight <= 4500) {
                            getalertdialog("Rs:210");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 4501 && weight <= 5000) {
                            getalertdialog("Rs:230");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 5001 && weight <= 5500) {
                            getalertdialog("Rs:250");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 5501 && weight <= 6000) {
                            getalertdialog("Rs:270");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 6001 && weight <= 6500) {
                            getalertdialog("Rs:290");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 6501 && weight <= 7000) {
                            getalertdialog("Rs:310");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 7001 && weight <= 7500) {
                            getalertdialog("Rs:330");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 7501 && weight <= 8000) {
                            getalertdialog("Rs:350");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 8001 && weight <= 8500) {
                            getalertdialog("Rs:370");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 8501 && weight <= 9000) {
                            getalertdialog("Rs:390");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 9001 && weight <= 9500) {
                            getalertdialog("Rs:410");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 9501 && weight <= 10000) {
                            getalertdialog("Rs:430");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 10001 && weight <= 10500) {
                            getalertdialog("Rs:450");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 10501 && weight <= 11000) {
                            getalertdialog("Rs:470");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 11001 && weight <= 11500) {
                            getalertdialog("Rs:490");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 11501 && weight <= 12000) {
                            getalertdialog("Rs:510");
                            // Toast.makeText(getActivity(), "Rs:70", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 12001 && weight <= 12500) {
                            getalertdialog("Rs:530");
                            //Toast.makeText(getActivity(), "Rs:90", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 12501 && weight <= 13000) {
                            getalertdialog("Rs:550");
                            //Toast.makeText(getActivity(), "Rs:110", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 13001 && weight <= 13500) {
                            getalertdialog("Rs:570");
                            //Toast.makeText(getActivity(), "Rs:130", Toast.LENGTH_SHORT).show();
                        } else if (weight >= 13501 && weight <= 14000) {
                            getalertdialog("Rs:590");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 14001 && weight <= 14500) {
                            getalertdialog("Rs:610");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 14501 && weight <= 15000) {
                            getalertdialog("Rs:630");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 15001 && weight <= 15500) {
                            getalertdialog("Rs:650");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 15501 && weight <= 16000) {
                            getalertdialog("Rs:670");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 16001 && weight <= 16500) {
                            getalertdialog("Rs:690");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 16501 && weight <= 17000) {
                            getalertdialog("Rs:710");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 17001 && weight <= 17500) {
                            getalertdialog("Rs:730");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 17501 && weight <= 18000) {
                            getalertdialog("Rs:750");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 18001 && weight <= 18500) {
                            getalertdialog("Rs:770");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 18501 && weight <= 19000) {
                            getalertdialog("Rs:790");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 19001 && weight <= 19500) {
                            getalertdialog("Rs:810");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 19501 && weight <= 20000) {
                            getalertdialog("Rs:830");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 20001 && weight <= 20500) {
                            getalertdialog("Rs:850");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 20501 && weight <= 21000) {
                            getalertdialog("Rs:870");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 21001 && weight <= 21500) {
                            getalertdialog("Rs:890");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 21501 && weight <= 22000) {
                            getalertdialog("Rs:910");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 22001 && weight <= 22500) {
                            getalertdialog("Rs:930");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 22501 && weight <= 23000) {
                            getalertdialog("Rs:950");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 23001 && weight <= 23500) {
                            getalertdialog("Rs:970");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 23501 && weight <= 24000) {
                            getalertdialog("Rs:990");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 24001 && weight <= 24500) {
                            getalertdialog("Rs:1,010");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 24501 && weight <= 25000) {
                            getalertdialog("Rs:1,030");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 25001 && weight <= 25500) {
                            getalertdialog("Rs:1,050");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 25501 && weight <= 26000) {
                            getalertdialog("Rs:1,070");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 26001 && weight <= 26500) {
                            getalertdialog("Rs:1,090");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 26501 && weight <= 27000) {
                            getalertdialog("Rs:1,110");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 27001 && weight <= 27500) {
                            getalertdialog("Rs:1,130");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 27501 && weight <= 28000) {
                            getalertdialog("Rs:1,150");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 28001 && weight <= 28500) {
                            getalertdialog("Rs:1,170");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 28501 && weight <= 29000) {
                            getalertdialog("Rs:1,190");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 29001 && weight <= 29500) {
                            getalertdialog("Rs:1,210");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >= 29501 && weight <= 30000) {
                            getalertdialog("Rs:1,230");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }else if (weight >=30001 ) {
                            getalertdialog("Limitation");
                            //Toast.makeText(getActivity(), "Rs:150", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                   // Toast.makeText(getActivity(), "Please Enter Wieght", Toast.LENGTH_SHORT).show();
                    new SweetAlertDialog(getContext())
                            .setTitleText("Please Enter Wieght!")
                            .show();
                }}else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please Check Your own Connection!")
                            .show();
                    //Toast.makeText(getContext(), "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        get_all_Servicecat();
        return view;
    }

    private void getalertdialog(String s) {
        new SmartDialogBuilder(getActivity())
                .setTitle("Price")
                .setSubTitle(s)
                .setCancalable(false)
                .setNegativeButtonHide(true) //hide cancel button
                .setPositiveButton("OK", new SmartDialogClickListener() {
                    @Override
                    public void onClick(SmartDialog smartDialog) {
                       // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        smartDialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new SmartDialogClickListener() {
            @Override
            public void onClick(SmartDialog smartDialog) {
              // Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                smartDialog.dismiss();

            }
        }).build().show();
    }

/*    private void showdata() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Dialog on Android");
        dialog.setMessage("" );
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }*/

    private void get_all_Servicecat() {
        new Backgroundtasktarif().execute(getString(R.string.get_All_service));
    }

    private class Backgroundtasktarif extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            URL url = null;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);


                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                // arr_caculate_tarrif.clear();
                String finalString = stringBuffer.toString();
                arr_caculate_tarrif = new ArrayList<>();
                if (finalString.trim().charAt(0) == '{') {
                    JSONObject jsonObject = new JSONObject(finalString);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject finalJasonObject = jsonArray.getJSONObject(i);
                        arr_caculate_tarrif.add(finalJasonObject.getString("Services"));
                    }
                    return arr_caculate_tarrif;
                } else {
                    arr_caculate_tarrif.add("Invalid!");
                    return arr_caculate_tarrif;
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
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            if (result != null) {
                if (result.size() > 0) {
                    if (result.size() == 1) {
                        Toast.makeText(getActivity(), result + "", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter_arr_caculate_tarrif = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arr_caculate_tarrif);
                        spinner_caculate_tarrif.setAdapter(adapter_arr_caculate_tarrif);
                    }
                } else {
                    Toast.makeText(getActivity(), "Network Error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Network Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
