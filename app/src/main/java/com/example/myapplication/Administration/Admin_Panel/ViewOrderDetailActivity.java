package com.example.myapplication.Administration.Admin_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class ViewOrderDetailActivity extends AppCompatActivity {
    TextView id, servicename, name, cnic, senderno, receiverno, email, pickupcity, transfercity, subcity, time, date, nearpostoffice, fulladdress;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail);
        id = findViewById(R.id.txt_pickupdetailorderid);
        servicename = findViewById(R.id.txt_user_pickuporderdetailservicetype);
        name = findViewById(R.id.txt_user_pickupdetailordername);
        cnic = findViewById(R.id.txt_user_pickupdetailcnic);
        senderno = findViewById(R.id.txt_user_pickupdetailsenderphoneno);
        receiverno = findViewById(R.id.txt_user_pickupdetailreceiverphoneno);
        email = findViewById(R.id.txt_user_pickupdetailemail);
        pickupcity = findViewById(R.id.txt_user_pickupdetailorderpickupcity);
        transfercity = findViewById(R.id.txt_user_pickuporderdetailtransfercity);
        subcity = findViewById(R.id.txt_user_pickupdetailsubcity);
        time = findViewById(R.id.txt_user_pickupdetailorderTime);
        date = findViewById(R.id.txt_user_pickupdetailorderdate);
        nearpostoffice = findViewById(R.id.txt_user_pickupdetailnearpostoffice);
        fulladdress = findViewById(R.id.txt_user_pickupdetailpickupfulladdress);
        //    ID`, `Time`, `Date`, `ServiceName`, `FullName`, `CNIC`, `Sender_Phone`, `Receiver_Phone`, `Email`,
//`Pickup_City`, `Transfer_City`, `Sub_City`, `Near_PostOffice`, `FullAddress`

        bundle = getIntent().getExtras();
        if (bundle != null) {

            id.setText(bundle.getString("ID"));
            servicename.setText(bundle.getString("ServiceName"));
            name.setText(bundle.getString("FullName"));
            cnic.setText(bundle.getString("CNIC"));
            senderno.setText(bundle.getString("Sender_Phone"));
            receiverno.setText(bundle.getString("Receiver_Phone"));
            email.setText(bundle.getString("Email"));
            pickupcity.setText(bundle.getString("Pickup_City"));
            transfercity.setText(bundle.getString("Transfer_City"));
            subcity.setText(bundle.getString("Sub_City"));
            time.setText(bundle.getString("Time"));
            date.setText(bundle.getString("Date"));
            nearpostoffice.setText(bundle.getString("Near_PostOffice"));
            fulladdress.setText(bundle.getString("FullAddress"));
        }
    }
}
