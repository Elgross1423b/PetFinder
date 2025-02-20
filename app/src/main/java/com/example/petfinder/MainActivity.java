package com.example.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button viewLostPetsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el botón para ir a la actividad de la lista de mascotas perdidas
        viewLostPetsButton = findViewById(R.id.viewLostPetsButton);
        viewLostPetsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PetListActivity.class);
            startActivity(intent);
        });

    }
}
