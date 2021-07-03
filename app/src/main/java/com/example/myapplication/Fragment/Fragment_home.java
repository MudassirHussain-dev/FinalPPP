package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Administration.Admin_Panel.Fragment_viewcomplaint;
import com.example.myapplication.Fragment.FragmentComplaint.Fragment_complaint;
import com.example.myapplication.FragmentSwapping.fragment_first_image;
import com.example.myapplication.FragmentSwapping.fragment_second_image;
import com.example.myapplication.FragmentSwapping.fragment_third_image;
import com.example.myapplication.LocatePostOffice.LocatePostOfficeActivity;
import com.example.myapplication.Pickupservices.Fragment_pickup_services;
import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;


public class Fragment_home extends Fragment implements View.OnClickListener {
    View view;
    LinearLayout LL_tacking, LL_complaint, LL_services, LL_tariff, LL_code, LL_location, LL_pickup, LL_contact, LL_about;
    ViewPager viewPager, vpslider;
    ImageView home_logo, img_trace;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initialization();
        listener();

/*        img_trace = (ImageView)view.findViewById(R.id.img_trace);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.trace_icon);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        img_trace.setImageDrawable(roundedBitmapDrawable);*/

        vpslider = (ViewPager) view.findViewById(R.id.vpslider);

        //Timers
        try {
            Timer tm = new Timer();
            tm.scheduleAtFixedRate(new Mytimer(), 5000, 3000);
        } catch (Exception ex) {

        }


        vpslider.setAdapter(new Mysliderpageradapter(getChildFragmentManager()));
        return view;
    }

    private void listener() {
        LL_tacking.setOnClickListener(this);
        LL_complaint.setOnClickListener(this);
        LL_services.setOnClickListener(this);
        LL_tariff.setOnClickListener(this);
        LL_code.setOnClickListener(this);
        LL_location.setOnClickListener(this);
        LL_pickup.setOnClickListener(this);
        LL_contact.setOnClickListener(this);
        LL_about.setOnClickListener(this);
    }

    private void initialization() {
        LL_tacking = (LinearLayout) view.findViewById(R.id.LL_track);
        LL_complaint = (LinearLayout) view.findViewById(R.id.LL_complaint);
        LL_services = (LinearLayout) view.findViewById(R.id.LL_express_services);
        LL_tariff = (LinearLayout) view.findViewById(R.id.LL_tariff);
        LL_code = (LinearLayout) view.findViewById(R.id.LL_code);
        LL_location = (LinearLayout) view.findViewById(R.id.LL_location);
        LL_pickup = (LinearLayout) view.findViewById(R.id.LL_pickup);
        LL_contact = (LinearLayout) view.findViewById(R.id.LL_contact);
        LL_about = (LinearLayout) view.findViewById(R.id.LL_about);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LL_track:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_Tracking()).commit();
                break;
            case R.id.LL_complaint:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_complaint()).commit();
                break;
            case R.id.LL_express_services:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_express_services()).commit();
                break;
            case R.id.LL_tariff:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_tarrif()).commit();
                break;
            case R.id.LL_code:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_post_codes()).commit();
                break;
            case R.id.LL_location:
                // getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Fragment_viewcomplaint()).commit();
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_pickup_services()).commit();

                // startActivity(new Intent(getActivity(), LocatePostOfficeActivity.class));
                // getFragmentManager().beginTransaction().replace(R.id.frame,new Fragment_location_postoffice()).commit();
                break;
            case R.id.LL_pickup:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_contact_us()).commit();

                // getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Fragment_pickup_services()).commit();
                break;
            case R.id.LL_contact:
                // getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Fragment_contact_us()).commit();
                //   getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,new Fragment_viewcomplaint()).commit();
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_feedback()).commit();

                break;
            case R.id.LL_about:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, new Fragment_about_us()).commit();
                break;
        }
    }

    private class Mysliderpageradapter extends FragmentPagerAdapter {
        public Mysliderpageradapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new fragment_first_image();
                case 1:
                    return new fragment_second_image();
                case 2:
                    return new fragment_third_image();
                default:
                    return new fragment_first_image();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    private class Mytimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (vpslider.getCurrentItem() == 0) {
                                vpslider.setCurrentItem(1);
                            } else if (vpslider.getCurrentItem() == 1) {
                                vpslider.setCurrentItem(2);
                            } else if (vpslider.getCurrentItem() == 2) {
                                vpslider.setCurrentItem(3);
                                vpslider.setCurrentItem(0);
                            }
                        } catch (Exception ex) {

                        }

                    }

                });
            }

        }
    }


}

