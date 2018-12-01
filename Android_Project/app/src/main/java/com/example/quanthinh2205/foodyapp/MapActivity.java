package com.example.quanthinh2205.foodyapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private double latitue, longtitude;
    String address;
    String name;
    Shop shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        latitue = -1;
        longtitude = -1;
        Intent intent = getIntent();
        shop = (Shop) intent.getSerializableExtra("Shop");
        address = shop.getStreet()+", "+shop.getCounty();
        name = shop.getName();
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    private void geoLocate(String txtAddresss)
    {
        Geocoder geocoder = new Geocoder(this);

        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(txtAddresss, 1);
        }catch (IOException e){
        }

        if(list.size() > 0){
            Address address = list.get(0);

            latitue = address.getLatitude();
            longtitude = address.getLongitude();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        geoLocate(address);
        map = googleMap;
        map.clear();

        LatLng sydney = new LatLng(latitue,longtitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        map.addMarker(new MarkerOptions().title(name).snippet(address).position(sydney));
    }
}
