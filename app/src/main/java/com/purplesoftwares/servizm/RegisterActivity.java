package com.purplesoftwares.servizm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private EditText txtName,txtPhone,txtEmail,txtPlace,txtPass;
    private Button btnSave;
    private ProgressBar setupProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapBoxKey));
        setContentView(R.layout.activity_register);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync((OnMapReadyCallback) mapboxMap -> {
            RegisterActivity.this.mapboxMap = mapboxMap;
            RegisterActivity.this.mapboxMap.addSource();



        };


        Toolbar setupToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(setupToolbar);
        setupToolbar.setTitle("Kullanıcı Bilgileri");

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPlace = findViewById(R.id.txtPlace);
        setupProgress = findViewById(R.id.reg_progress);
        txtPass = findViewById(R.id.txtPass);

        btnSave = findViewById(R.id.btnSave);


        btnSave.setOnClickListener(v -> {

            Map<String, String> userMap = new HashMap<>();

            String name = txtName.getText().toString();
            String phone = txtPhone.getText().toString();
            final String email = txtEmail.getText().toString();
            String birthDate = txtPlace.getText().toString();
            final String pass = txtPass.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                    && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(birthDate) && !TextUtils.isEmpty(pass))
            {
                setupProgress.setVisibility(View.VISIBLE);
                userMap.put("name",name);
                userMap.put("phone",phone);
                userMap.put("email",email);
                userMap.put("birthDate",birthDate);


                String user_id =mAuth.getCurrentUser().getUid();
                AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
                mAuth.getCurrentUser().linkWithCredential(credential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("1", "linkWithCredential:success");
                                FirebaseUser user = task.getResult().getUser();
                              //  updateUI(user);
                            } else {
                               Log.w("2", "linkWithCredential:failure", task.getException());
                                // updateUI(null);
                            }
                            // ...
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
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "user_location_permission_explanation", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(this, "user_location_permission_not_granted", Toast.LENGTH_LONG).show();
            finish();        }

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        RegisterActivity.this.mapboxMap = mapboxMap;
        enableLocationComponent();
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate
            locationComponent.activateLocationComponent(this);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
}
