package com.example.petfinder;

import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityTest2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Debe ir antes de setContentView
        setContentView(R.layout.activity_main_test2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom // Opcional: Si no quieres que el BottomNavigationView se superponga con la barra de navegación
            );
            return insets;
        });



        // Referencia al BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView3);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();


            // Selecciona el fragment según el ítem del menú
            if (itemId == R.id.nav_publicaciones) {
                selectedFragment = new Fragment_Pet_List(); // Reemplaza con tu Fragment
            } else if (itemId == R.id.nav_mapas) {
                selectedFragment = new mainMaps_Fragment(); // Reemplaza con tu Fragment
            } /*else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment(); // Reemplaza con tu Fragment
            }*/

            // Carga el fragment seleccionado
            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Carga el fragment inicial (opcional)
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home); // Establece el ítem inicial
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Pet_List()).commit();
        }
    }
}