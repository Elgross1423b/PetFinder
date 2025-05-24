package com.example.petfinder;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (drawerLayout == null) {
            throw new IllegalStateException("DrawerLayout no encontrado. Verifica el ID 'drawer_layout' en activity_main.xml");
        }
        if (toolbar == null) {
            throw new IllegalStateException("Toolbar no encontrada. Verifica el ID 'toolbar' en activity_main.xml");
        }
        if (navigationView == null) {
            throw new IllegalStateException("NavigationView no encontrado. Verifica el ID 'nav_view' en activity_main.xml");
        }
        if (fab == null) {
            throw new IllegalStateException("FloatingActionButton no encontrado. Verifica el ID 'fab' en activity_main.xml");
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }*/
    }

}