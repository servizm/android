package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class FirstLoginPage extends AppCompatActivity {

    private TextView txtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login_page);

        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);

        txtPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage();
            }
        });

    }

    private void openPage() {
        Intent mainIntent = new Intent(FirstLoginPage.this, SendSmsActivity.class);
        startActivity(mainIntent);
        finish();
    }

}

