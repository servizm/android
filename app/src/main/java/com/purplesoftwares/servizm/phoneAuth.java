package com.purplesoftwares.servizm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneAuth extends AppCompatActivity {

    private EditText txtPhoneNumber;
    private Button btnGetCode, btnLogOut;
    private EditText txtEnterCode;
    private Button btnSignIn;
   // PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String codeSent;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnGetCode = findViewById(R.id.btnGetCode);
        txtEnterCode = findViewById(R.id.txtEnterCode);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnLogOut = findViewById(R.id.btnLogOut);


        mAuth = FirebaseAuth.getInstance();



        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
                sendCode();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

            private void logout() {
                mAuth.signOut();

            }
        });
    }

    private void sendCode() {
        String phoneNumber = txtPhoneNumber.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

      private void verifySignInCode(){
         String code = txtEnterCode.getText().toString();
         PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
         signInWithPhoneAuthCredential(credential);
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e)  {
            Toast.makeText(phoneAuth.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            Toast.makeText(getApplicationContext(),
                                    "Login Successfull", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }



    }

