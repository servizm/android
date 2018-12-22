package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SendSmsActivity extends AppCompatActivity {



    ImageView imageIlerle;
    TextView btnIlerle;
    EditText txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        imageIlerle = findViewById(R.id.imageIlerle);
        btnIlerle = findViewById(R.id.btnIlerle);
        txtPhone = findViewById(R.id.txtPhone);

        txtPhone.setSelection(txtPhone.getText().length());

        btnIlerle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage();
            }
        });
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        imageIlerle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage() ;
            }
        });

    }

    private void openPage() {
        Intent mainIntent = new Intent(SendSmsActivity.this, EnterSmsActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
