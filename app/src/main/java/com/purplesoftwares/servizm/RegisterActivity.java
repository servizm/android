package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


     FirebaseAuth mAuth;
     FirebaseAuth firebaseAuth;
     FirebaseFirestore db;



     EditText txtName,txtPhone,txtEmail, txtBirthDate;
     Button btnSave,btnLogout;
     ProgressBar setupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar setupToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(setupToolbar);
        setupToolbar.setTitle("Kullanıcı Bilgileri");

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtBirthDate = findViewById(R.id.txtBirthDate);
        setupProgress = findViewById(R.id.reg_progress);


        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> userMap = new HashMap<>();

                String name = txtName.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String birthDate = txtBirthDate.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                        && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(birthDate))
                {
                    setupProgress.setVisibility(View.VISIBLE);
                    userMap.put("name",name);
                    userMap.put("phone",phone);
                    userMap.put("email",email);
                    userMap.put("birthDate",birthDate);
                    String user_id =mAuth.getCurrentUser().getUid();

                    db.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();

                            } else {

                                String error = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

                            }

                            setupProgress.setVisibility(View.INVISIBLE);
                        }

                    });
                }
            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

            }
        });




    }
}
