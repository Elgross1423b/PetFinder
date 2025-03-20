package com.example.petfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText latText, lngText;
    private Button btnShowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Referencias a los elementos de la UI
        latText = findViewById(R.id.latText);
        lngText = findViewById(R.id.lngText);
        btnShowLocation = findViewById(R.id.btnShowLocation);

        // Obtener el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Botón para actualizar la ubicación en el mapa
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMapLocation();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Ubicación inicial (Ciudad de México)
        LatLng initialLocation = new LatLng(19.3453, -99.1711);
        mMap.addMarker(new MarkerOptions().position(initialLocation).title("Ciudad de México"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f));
    }

    private void updateMapLocation() {
        if (mMap != null) {
            try {
                double lat = Double.parseDouble(latText.getText().toString());
                double lng = Double.parseDouble(lngText.getText().toString());
                LatLng newLocation = new LatLng(lat, lng);
                mMap.clear();  // Limpiar marcadores anteriores
                mMap.addMarker(new MarkerOptions().position(newLocation).title("Nueva Ubicación"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 12f));
            } catch (NumberFormatException e) {
                latText.setError("Ingresa una latitud válida");
                lngText.setError("Ingresa una longitud válida");
            }
        }
    }
}
