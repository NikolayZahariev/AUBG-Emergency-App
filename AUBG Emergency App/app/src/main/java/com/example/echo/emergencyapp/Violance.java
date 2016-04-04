package com.example.echo.emergencyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class Violance extends AppCompatActivity {

    ImageView img_violance_security;
    ImageView img_violance_scapto1;
    ImageView img_violance_scapto2;
    ImageView img_violance_scapto3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violance);

        img_violance_security = (ImageView) findViewById(R.id.img_Violance_Security);
        img_violance_scapto1 = (ImageView) findViewById(R.id.img_Violance_Scapto1);
        img_violance_scapto2 = (ImageView) findViewById(R.id.img_Violance_Scapto2);
        img_violance_scapto3 = (ImageView) findViewById(R.id.img_Violance_Scapto3);

        //send us to the "Security" activity when the picture of the security guard is clicked
        img_violance_security.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Violance.this, Security.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_1_Hall_Director" activity when the picture of the hall director is clicked
        img_violance_scapto1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Violance.this, Scapto_1_Hall_Director.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_2_Hall_Director" activity when the picture of the hall director is clicked
        img_violance_scapto2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Violance.this, Scapto_2_Hall_Director.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_3_Hall_Director" activity when the picture of the hall director is clicked
        img_violance_scapto3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Violance.this, Scapto_3_Hall_Director.class);
                startActivity(i);
            }
        });
    }
}