package com.example.myapplication.Administration.Admin_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class ParcelDetailActivity extends AppCompatActivity {
    TextView id,articleno,time,date,bookingoggice,deliveryoffice,destination,mobile,email,name;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_detail);

        id=findViewById(R.id.txt_parcle_ID);
        articleno=findViewById(R.id.txt_parcle_article_number);
        time=findViewById(R.id.txt_booking_parcel_register_time);
        date=findViewById(R.id.txt_booking_parcel_register_date);
        bookingoggice=findViewById(R.id.txt_booking_parcel_register_bookingoffice);
        deliveryoffice=findViewById(R.id.txt_booking_parcel_delivery_office);
        destination=findViewById(R.id.txt_destination_parcel_office);
        email=findViewById(R.id.txt_registaremaildetail);
        mobile=findViewById(R.id.txt_parcel_register_customer_mobileno);
        name = findViewById(R.id.txt_customername);

        bundle=getIntent().getExtras();
        if(bundle!=null){
            id.setText(bundle.getString("ID"));
            articleno.setText(bundle.getString("Track_ID"));
            time.setText(bundle.getString("Datee"));
            date.setText(bundle.getString("Timee"));
            bookingoggice.setText(bundle.getString("Delivery_office"));
            deliveryoffice.setText(bundle.getString("Bookig_office"));
            destination.setText(bundle.getString("Target_Office"));
            mobile.setText(bundle.getString("Mobile_no"));
            name.setText(bundle.getString("CustomerName"));
            email.setText(bundle.getString("Email_Id"));

        }

        }
}
