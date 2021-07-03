package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ForgetActivity extends AppCompatActivity {
    TextInputEditText fname, fcnic,mobileno;
    Button btn_forgetpassword;
    ProgressDialog progressDialog;
    String firtName, cnic,mobile;
    //StringRequest stringRequest;
    //String URL = "http://www.dabonjour.com/Mudassir/forget.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        progressDialog = new ProgressDialog(this);

        fname = (TextInputEditText) findViewById(R.id.et_ffirtName);
        fcnic = (TextInputEditText) findViewById(R.id.et_fcnic);
        mobileno = (TextInputEditText)findViewById(R.id.et_fphone);
        btn_forgetpassword = (Button) findViewById(R.id.btn_forgetPassword);


        btn_forgetpassword();
    }

    private void btn_forgetpassword() {
        btn_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firtName = fname.getText().toString().trim();
                cnic = fcnic.getText().toString().trim();
                mobile= mobileno.getText().toString();
                if (firtName.length() > 0 && cnic.length() ==13) {
                  //  new Backgroundaccountcheck().execute(getString(R.string.checking_account));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Complete All fields",Toast.LENGTH_SHORT).show();
                }


            }

/*                firtName = fname.getText().toString();
                cnic = fcnic.getText().toString();
                stringRequest = new StringRequest(StringRequest.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Success")){
                            Toast.makeText(ForgetActivity.this, "Email Successfully.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ForgetActivity.this, "Failed Request", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgetActivity.this, "Please check Your connection", Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();
                        param.put("FName",firtName);
                        param.put("CNIC",cnic);
                        return super.getParams();
                    }
                };
            }*/
        });
    }
}
