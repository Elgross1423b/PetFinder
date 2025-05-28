package com.example.petfinder;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.firebase.database.*;

public class mainMaps_Fragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseRef;
    private Marker currentMarker;
    private LatLng lastLocation = null;
    private boolean isTrackingEnabled = false;

    private Switch trackingSwitch;

    public mainMaps_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_maps_, container, false);

        // Referencia a Switch
        trackingSwitch = view.findViewById(R.id.trackingSwitch);
        trackingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isTrackingEnabled = isChecked;
        });

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Referencia a Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("tracker");

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng initialLocation = new LatLng(19.3453, -99.1711);
        currentMarker = mMap.addMarker(new MarkerOptions()
                .position(initialLocation)
                .title("Esperando coordenadas..."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f));

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double lat = snapshot.child("lat").getValue(Double.class);
                Double lng = snapshot.child("lng").getValue(Double.class);

                if (lat != null && lng != null) {
                    LatLng newLocation = new LatLng(lat, lng);

                    if (currentMarker != null) currentMarker.remove();
                    currentMarker = mMap.addMarker(new MarkerOptions()
                            .position(newLocation)
                            .title("Ubicación actual"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15f));

                    // Rastro solo si el switch está activado y ha recorrido más de 5 metros
                    if (isTrackingEnabled && lastLocation != null) {
                        float[] results = new float[1];
                        android.location.Location.distanceBetween(
                                lastLocation.latitude, lastLocation.longitude,
                                newLocation.latitude, newLocation.longitude, results);

                        if (results[0] > 5f) {
                            mMap.addPolyline(new PolylineOptions()
                                    .add(lastLocation, newLocation)
                                    .color(0xFFFF4081) // color rosa pastel
                                    .width(8f));
                        }
                    }
                    lastLocation = newLocation;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
