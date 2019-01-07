package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterSmsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView txtCodeInfo,txtCode;
    ImageView imageTamam;
    TextView btnTamam;
    Button btnResend, btnback;
    String phone;
    String codeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sms);

        mAuth = FirebaseAuth.getInstance();

        txtCodeInfo = findViewById(R.id.txtCodeInfo);
        txtCode = findViewById(R.id.txtCode);
        btnTamam = findViewById(R.id.btnTamam);
        imageTamam = findViewById(R.id.imageTamam);
        btnResend = findViewById(R.id.btnResend);
        btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(v -> onBackPressed());

        btnResend.setOnClickListener(v -> resendSms());

        btnTamam.setOnClickListener(v -> validateSms());

        imageTamam.setOnClickListener(v -> validateSms());

    }

    @Override
    protected void onStart() {
        super.onStart();
        String caption = txtCodeInfo.getText().toString();
        Intent myIntent = getIntent();
        phone = myIntent.getStringExtra("phone");
        caption = phone +" "+ caption;
        txtCodeInfo.setText(caption);

        resendSms();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent setupIntent = new Intent(EnterSmsActivity.this, RegisterActivity.class);
                        startActivity(setupIntent);
                        finish();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(getApplicationContext(),
                                    "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void validateSms() {
        String code = txtCode.getText().toString();
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void resendSms()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                 60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e)  {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };


}
