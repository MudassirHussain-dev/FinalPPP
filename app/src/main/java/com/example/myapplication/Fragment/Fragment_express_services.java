package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.ExpressServices.Fragment_cod_ex;
import com.example.myapplication.ExpressServices.Fragment_emo_ex;
import com.example.myapplication.ExpressServices.Fragment_ems_ex;
import com.example.myapplication.ExpressServices.Fragment_ems_pep_ex;
import com.example.myapplication.ExpressServices.Fragment_fmo_ex;
import com.example.myapplication.ExpressServices.Fragment_fms_ex;
import com.example.myapplication.ExpressServices.Fragment_sdd_ex;
import com.example.myapplication.ExpressServices.Fragment_umo_ex;
import com.example.myapplication.ExpressServices.Fragment_ums_ex;
import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class Fragment_express_services extends Fragment {
    View view;
    LinearLayout btn_ums_lo;
    LinearLayout btn_ems_lo;
    LinearLayout btn_fms_lo;
    LinearLayout btn_cod_lo;
    LinearLayout btn_ems_pep_lo;
    LinearLayout btn_sdd_lo;
    LinearLayout btn_fmo_lo;
    LinearLayout btn_umo_lo;
    LinearLayout btn_emo_lo;
    ImageView home_logo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_express_services, container, false);
        home_logo=(ImageView)view.findViewById(R.id.home_logo);
        home_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame,new Fragment_home()).commit();
            }
        });
        btn_ums_lo = (LinearLayout) view.findViewById(R.id.btn_ums_lo);
        btn_ems_lo = (LinearLayout) view.findViewById(R.id.btn_ems_lo);
        btn_fms_lo = (LinearLayout) view.findViewById(R.id.btn_fms_lo);
        btn_cod_lo = (LinearLayout) view.findViewById(R.id.btn_cod_lo);
        btn_ems_pep_lo = (LinearLayout) view.findViewById(R.id.btn_ems_pep_lo);
        btn_sdd_lo = (LinearLayout) view.findViewById(R.id.btn_sdd_lo);
        btn_fmo_lo = (LinearLayout) view.findViewById(R.id.btn_fmo_lo);
        btn_umo_lo = (LinearLayout) view.findViewById(R.id.btn_umo_lo);
        btn_emo_lo = (LinearLayout) view.findViewById(R.id.btn_emo_lo);


       btn_ums_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_ums_ex()).commit();
            }
        });
         btn_ems_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_ems_ex()).commit();
            }
        });
        btn_fms_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_fms_ex()).commit();
            }
        });
        btn_cod_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_cod_ex()).commit();
            }
        });
       btn_ems_pep_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_ems_pep_ex()).commit();
            }
        });
         btn_sdd_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_sdd_ex()).commit();
            }
        });
      btn_fmo_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_fmo_ex()).commit();
            }
        });
          btn_umo_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_umo_ex()).commit();
            }
        });
        btn_emo_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_emo_ex()).commit();
            }
        });

        return view;
    }

}
