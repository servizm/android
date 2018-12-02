package com.purplesoftwares.servizm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LoginActivity extends AppCompatActivity {

    private Button btnFacebookLogin;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnLogout = findViewById(R.id.btnLogout);


        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Selam",Toast.LENGTH_SHORT).show();
            }
        });


    }



}
