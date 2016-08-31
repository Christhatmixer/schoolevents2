package com.theelitelabel.schoolevents2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by cfarl_000 on 8/28/2016.
 */
public class map extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        Intent intent = getIntent();
        float latitude = intent.getFloatExtra("lat",0);
        float longitude = intent.getFloatExtra("longitude",0);
        String name = intent.getStringExtra("name");
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(name));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude) , 15.0f));

    }
}