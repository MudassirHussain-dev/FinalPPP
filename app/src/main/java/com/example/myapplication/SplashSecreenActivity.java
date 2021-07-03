package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.Administration.Admin_Panel.AdminActivity;
import com.example.myapplication.Administration.Registrar_Panel.RegistrarActivity;

public class SplashSecreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_secreen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    String decider ;
                    SharedPreferences preferences = getSharedPreferences("login",MODE_PRIVATE);
                    decider =  preferences.getString("decider","0");
                    if (decider.equals("0"))
                    {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        finish();
                    }
                    else if (decider.equals("2"))
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    }
                    else if (decider.equals("3"))
                    {
                        startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));
                        finish();

                    }
                    else if (decider.equals("1"))
                    {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        finish();

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });thread.start();
    }
}
