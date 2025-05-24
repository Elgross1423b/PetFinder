package com.example.petfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mainMaps_Fragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText latText, lngText;
    private Button btnShowLocation;

    public mainMaps_Fragment() {
        // Required empty public constructor
    }

    public static mainMaps_Fragment newInstance(String param1, String param2) {
        mainMaps_Fragment fragment = new mainMaps_Fragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_maps_, container, false);

        // Referencias a los elementos de la UI
        latText = view.findViewById(R.id.latText);
        lngText = view.findViewById(R.id.lngText);
        btnShowLocation = view.findViewById(R.id.btnShowLocation);

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
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

        return view;
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