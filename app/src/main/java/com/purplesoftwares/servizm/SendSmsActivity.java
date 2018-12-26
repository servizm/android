package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                getSmsCode();
            }
        });
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        imageIlerle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSmsCode() ;
            }
        });
    }

    private void getSmsCode() {
        String phone = txtPhone.getText().toString();
        if (phone.length() < 13) {
            Toast.makeText( getApplicationContext(), "Telefon Numarasını Giriniz", Toast.LENGTH_SHORT).show();
        }
        else {
            openEnterSmsActivity();
        }
    }

    private void openEnterSmsActivity() {
        Intent mainIntent = new Intent(SendSmsActivity.this, EnterSmsActivity.class);
        mainIntent.putExtra("phone",txtPhone.getText().toString());
        startActivity(mainIntent);
        finish();
    }

}
