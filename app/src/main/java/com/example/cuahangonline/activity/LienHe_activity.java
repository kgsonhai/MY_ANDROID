package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.cuahangonline.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LienHe_activity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he_activity);

        ActionToolBar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng sydney = new LatLng(15.979616964200044, 108.25078229895544);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Shop thời trang")
                .snippet("Đây chỉ là nhà trọ thôi nhé !!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void ActionToolBar() {
        toolbar = findViewById(R.id.toolbarLienHe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}