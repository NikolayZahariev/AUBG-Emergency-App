package com.example.echo.emergencyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class Weapon extends AppCompatActivity {

    ImageView weapon_security;
    ImageView weapon_scapto1;
    ImageView weapon_scapto2;
    ImageView weapon_scapto3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        weapon_security = (ImageView) findViewById(R.id.img_Weapon_Security);
        weapon_scapto1 = (ImageView) findViewById(R.id.img_Weapon_Scapto1);
        weapon_scapto2 = (ImageView) findViewById(R.id.img_Weapon_Scapto2);
        weapon_scapto3 = (ImageView) findViewById(R.id.img_Weapon_Scapto3);

        //send us to the "Security" activity when the picture of the security guard is clicked
        weapon_security.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Weapon.this, Security.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_1_Hall_Director" activity when the picture of the hall director is clicked
        weapon_scapto1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Weapon.this, Scapto_1_Hall_Director.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_2_Hall_Director" activity when the picture of the hall director is clicked
        weapon_scapto2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Weapon.this, Scapto_2_Hall_Director.class);
                startActivity(i);
            }
        });

        //send us to the "Scapto_3_Hall_Director" activity when the picture of the hall director is clicked
        weapon_scapto3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Weapon.this, Scapto_3_Hall_Director.class);
                startActivity(i);
            }
        });
    }
}
