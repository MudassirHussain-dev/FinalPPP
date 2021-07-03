package com.example.myapplication.Administration.Registrar_Panel;

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

public class Fragment_Swapping_RegisteredParcel extends Fragment {
    View view;
    ViewPager vp_admin_parcel_registered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_swapping_registeredparcel, null);
        vp_admin_parcel_registered = (ViewPager) view.findViewById(R.id.vp_admin_registedparcel);
        vp_admin_parcel_registered.setAdapter(new Swapping_tracker_registered_parcel(getChildFragmentManager()));

        return view;
    }

    private class Swapping_tracker_registered_parcel extends FragmentPagerAdapter {
        public Swapping_tracker_registered_parcel(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_admin_tracker_parcel();
                case 1:
                    return new Fragment_admin_registered_parcel();
                default:
                    return new Fragment_admin_tracker_parcel();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
