package com.purplesoftwares.servizm;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText txtName, txtPhone, txtEmail, txtPlace, txtPass;
    private EditText fieldLatitude, fieldLongitude, fieldCountry, fieldCity, fieldPostalCode, fieldAddressLine;

    Button btnSave,btnLocation;
    private ProgressBar setupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPlace = findViewById(R.id.txtPlace);
        setupProgress = findViewById(R.id.reg_progress);
        txtPass = findViewById(R.id.txtPass);

        fieldLatitude= findViewById(R.id.fieldLatitude);
        fieldLongitude= findViewById(R.id.fieldLongitude);
        fieldCountry=findViewById(R.id.fieldCountry);
        fieldCity= findViewById(R.id.fieldCity);
        fieldPostalCode= findViewById(R.id.fieldPostalCode);
        fieldAddressLine= findViewById(R.id.fieldAddressLine);
        btnSave = findViewById(R.id.btnSave);
        btnLocation = findViewById(R.id.btnLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              GPSTracker gpsTracker = new GPSTracker(RegisterActivity.this);
              if (gpsTracker.getIsGPSTrackingEnabled())
              {
                  String stringLatitude = String.valueOf(gpsTracker.latitude);
                  fieldLatitude.setText(stringLatitude);

                  String stringLongitude = String.valueOf(gpsTracker.longitude);
                  fieldLongitude.setText(stringLongitude);

                  String country = gpsTracker.getCountryName(RegisterActivity.this);
                  fieldCountry.setText(country);

                  String city = gpsTracker.getLocality(RegisterActivity.this);
                  fieldCity.setText(city);

                  String postalCode = gpsTracker.getPostalCode(RegisterActivity.this);
                  fieldPostalCode.setText(postalCode);

                  String addressLine = gpsTracker.getAddressLine(RegisterActivity.this);
                  fieldAddressLine.setText(addressLine);
              }
              else
              {
                  gpsTracker.showSettingsAlert();
              }
           }

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> userMap = new HashMap<>();
                String name = txtName.getText().toString();
                String phone = txtPhone.getText().toString();
                final String email = txtEmail.getText().toString();
                String birthDate = txtPlace.getText().toString();
                final String pass = txtPass.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                        && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(birthDate) && !TextUtils.isEmpty(pass)) {
                    setupProgress.setVisibility(View.VISIBLE);
                    userMap.put("name", name);
                    userMap.put("phone", phone);
                    userMap.put("email", email);
                    userMap.put("birthDate", birthDate);


                    String user_id = mAuth.getCurrentUser().getUid();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
                    mAuth.getCurrentUser().linkWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("1", "linkWithCredential:success");
                                        FirebaseUser user = task.getResult().getUser();
                                        //  updateUI(user);
                                    } else {
                                        Log.w("2", "linkWithCredential:failure", task.getException());
                                        // updateUI(null);
                                    }
                                }
                            });

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
    }
}






