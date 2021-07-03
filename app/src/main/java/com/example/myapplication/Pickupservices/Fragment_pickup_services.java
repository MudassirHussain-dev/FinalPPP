package com.example.myapplication.Pickupservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;

public class Fragment_pickup_services extends Fragment {
    View view;
    ViewPager vp_pickupservicess;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pickup_services, container, false);

        vp_pickupservicess = (ViewPager) view.findViewById(R.id.vp_pickup_services);
        vp_pickupservicess.setAdapter(new PickupservicesPager(getChildFragmentManager()));

        return view;
    }

    private class PickupservicesPager extends FragmentPagerAdapter {
        public PickupservicesPager(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_parcel_pickup_request_by_register();
                case 1:
                    return new Fragment_parcel_pickup_request_by_call();
                default:
                    return new Fragment_parcel_pickup_request_by_register();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            switch (position) {
                case 0:
                    return "By Registration";
                case 1:
                    return "By Call";
                default:
                    return "By Registration";

            }
        }
    }
}
