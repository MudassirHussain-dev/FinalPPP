package com.example.myapplication.LocatePostOffice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocatePostOfficeActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    EditText et_searchLocation;
    double latitude,longitude;

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private int ProximitryRadius=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_post_office);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View v) {
        String hospital ="hospital",school = "school",restuarant="restuarant";
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();


        switch (v.getId()) {
            case R.id.iv_searchAddress:
                et_searchLocation = (EditText) findViewById(R.id.et_locationSearch);

                String address = et_searchLocation.getText().toString();

                List<Address> addressList = null;

                MarkerOptions userMarkerOption = new MarkerOptions();

                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(address, 6);
                        if(addressList!=null){
                            for (int i = 0; i < addressList.size(); i++) {
                                Address useraddress = addressList.get(i);

                                LatLng latLng = new LatLng(useraddress.getLatitude(),useraddress.getLongitude());
                                userMarkerOption.position(latLng);
                                userMarkerOption.title(address);

                                userMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                // currentUserLocationMarker = mMap.addMarker(userMarkerOption);
                                mMap.addMarker(userMarkerOption);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                            }
                        }else{
                            Toast.makeText(this, "Location Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(this, "Please Write any Location Name", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.office_nearby:
                mMap.clear();
                String url = getUrl(latitude,longitude,hospital);
                transferData[0] = mMap;
                transferData[1] = url;
                getNearbyPlaces.execute(getNearbyPlaces);
                Toast.makeText(this, "Searching for Nearby Pakistan Post Office.....", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Pakistan Post Office....", Toast.LENGTH_SHORT).show();
                break;
        /*    case R.id.school_nearby:
                mMap.clear();
                String url = getUrl(latitude,longitude,school);
                transferData[0] = mMap;
                transferData[1] = url;
                getNearbyPlaces.execute(getNearbyPlaces);
                Toast.makeText(this, "Searching for Nearby School .....", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby School....", Toast.LENGTH_SHORT).show();
                break;*/
          /*  case R.id.restaurant_nearby:
                mMap.clear();
                String ur2 = getUrl(latitude,longitude,hospital);
                transferData[0] = mMap;
                transferData[1] = url;
                getNearbyPlaces.execute(getNearbyPlaces);
                Toast.makeText(this, "Searching for Nearby Restuarant .....", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Restuarant....", Toast.LENGTH_SHORT).show();
                break;*/

        }
    }
    private String getUrl(double latitude,double longitude, String nearbyPlaces){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location="+latitude+","+longitude);
        googleURL.append("&radius="+ProximitryRadius);
        googleURL.append("&type="+nearbyPlaces);
        googleURL.append("&sensor=true");
        googleURL.append("&key="+"AIzaSyD6gwGB1zIWZacxWS-7zNSFfP5lquj85pg");
        Log.d("LoacatePostOffice","url="+googleURL.toString());

    return googleURL.toString();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            builGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            builGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied.....", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void builGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            //currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User Current Location");

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
