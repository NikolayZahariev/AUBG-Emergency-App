package com.example.echo.emergencyapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Scapto_2_Hall_Director extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scapto_2__hall__director);
        findViewById(R.id.btn_Scapto2_Call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone("(+359 73)888542");    //the work phone of the Scapto 2 hall director is dialed
            }
        });
    }

    //dials the work phone of the Scapto 2 hall director when the "Call" button is clicked
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}