package com.example.petfinder;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private static final String TAG = "PetListActivity";
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private PetDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        // Inicializar DatabaseHelper
        databaseHelper = new PetDatabaseHelper(this);

        // Forzar creación/actualización de BD
        databaseHelper.getWritableDatabase().close();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar mascotas
        loadPets();
    }

    private void loadPets() {
        List<Pet> pets = databaseHelper.getAllPets();
        Log.d(TAG, "Número de mascotas cargadas: " + pets.size());

        petAdapter = new PetAdapter(pets, new PetAdapter.OnItemClickListener() {
            @Override public void onMessageClick(Pet pet) {
                showToast("Contactar a: " + pet.getReporterName());
            }
            @Override public void onCommentClick(Pet pet) {
                showToast("Comentar sobre: " + pet.getName());
            }
            @Override public void onShareClick(Pet pet) {
                showToast("Compartir: " + pet.getName());
            }
            @Override public void onLikeClick(Pet pet) {
                showToast("Like a: " + pet.getName());
            }
        });

        recyclerView.setAdapter(petAdapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}