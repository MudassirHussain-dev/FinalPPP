package com.example.myapplication.Administration.Admin_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class ComplaintsDetailActivity extends AppCompatActivity {
    Bundle bundle;
    TextView id,serviceName,artino,cName,cMobile,bookingdate,bookingtime,bookingoffice,deliveryoffice,ccity,senderName,smobile,semail
            ,sendercity,senderaddress,addname,addmobile,addEmail,addcity,adAddresss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_detail);


        id=findViewById(R.id.txt_complaintID);
        serviceName=findViewById(R.id.txt_complaint_service_number);
        artino=findViewById(R.id.txt_complaint_artical_number);
        cName=findViewById(R.id.txt_complaintName);
        cMobile=findViewById(R.id.txt_complaint_Mobile);
        bookingdate=findViewById(R.id.txt_booking_complaint_date);
        bookingtime=findViewById(R.id.txt_booking_complaint_time);
        bookingoffice=findViewById(R.id.txt_booking_complaint_office);
        deliveryoffice=findViewById(R.id.txt_delivery_complaint_office);
        ccity=findViewById(R.id.txt_complainter_city_complaint_office);
        senderName=findViewById(R.id.txt_sender_Name);
        smobile=findViewById(R.id.txt_sender_Mobile);
        semail=findViewById(R.id.txt_sender_email);
        sendercity=findViewById(R.id.txt_sender_city);
        senderaddress=findViewById(R.id.txt_sender_address);
        addname=findViewById(R.id.txt_add_Name);
        addmobile=findViewById(R.id.txt_add_Mobile);
        addEmail=findViewById(R.id.txt_add_email);
        addcity=findViewById(R.id.txt_add_city);
        adAddresss=findViewById(R.id.txt_add_address);

        bundle=getIntent().getExtras();
        if(bundle!=null){

            id.setText(bundle.getString("id"));
            serviceName.setText(bundle.getString("service_name"));
            artino.setText(bundle.getString("article_number"));
            cName.setText(bundle.getString("cName"));
            addEmail.setText(bundle.getString("adEmail"));
            addmobile.setText(bundle.getString("adMobile"));
            adAddresss.setText(bundle.getString("adAddress"));
            senderaddress.setText(bundle.getString("s_address"));
            semail.setText(bundle.getString("s_email"));
            sendercity.setText(bundle.getString("s_city"));
            bookingoffice.setText(bundle.getString("booking_office"));
            ccity.setText(bundle.getString("cCity"));
            senderName.setText(bundle.getString("s_name"));
            addname.setText(bundle.getString("adName"));
            addcity.setText(bundle.getString("adCity"));
            deliveryoffice.setText(bundle.getString("deliviery_officee"));
            smobile.setText(bundle.getString("s_mobile"));
            cMobile.setText(bundle.getString("cMobile"));
            bookingtime.setText(bundle.getString("booking_time"));
            bookingdate.setText(bundle.getString("booking_date"));






        }


    }
}
