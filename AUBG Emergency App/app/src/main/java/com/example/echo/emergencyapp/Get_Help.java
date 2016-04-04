package com.example.echo.emergencyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class Get_Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__help);

        View.OnClickListener listnr_map=new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g= new Intent(Get_Help.this, emergency_map.class);
                startActivity(g);
            }
        };
        Button btn_map =(Button) findViewById(R.id.btn_Map);
        btn_map.setOnClickListener(listnr_map);


        View.OnClickListener listnr_violance =new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g= new Intent(Get_Help.this, Violance.class);
                startActivity(g);
            }
        };
        Button btn_violance =(Button) findViewById(R.id.btn_Violance);
        btn_violance.setOnClickListener(listnr_violance);


        View.OnClickListener listnr_weapon=new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g= new Intent(Get_Help.this, Weapon.class);
                startActivity(g);
            }
        };
        Button btn_weapon =(Button) findViewById(R.id.btn_Weapon1);
        btn_weapon.setOnClickListener(listnr_weapon);



    }
}
