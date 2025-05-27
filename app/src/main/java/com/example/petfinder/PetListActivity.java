package com.example.petfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import android.content.Intent;
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
                sharePet(pet);
            }
            @Override public void onLikeClick(Pet pet) {
                showToast("Like a: " + pet.getName());
            }
            @Override public void onSaveClick(Pet pet) {
                showToast("Guardado: " + pet.getName());
                // O cualquier otra acción que desees implementar
            }
        });


        recyclerView.setAdapter(petAdapter);
    }

    private void sharePet(Pet pet) {
        try {
            // 1. Crear el texto que se compartirá
            String shareText = "¡Mira esta mascota que encontré en PetFinder!\n\n" +
                    "Nombre: " + pet.getName() + "\n" +
                    "Tipo: " + pet.getBreed() + "\n" +
                    "Raza: " + pet.getBreed() + "\n" +
                    "Descripción: " + pet.getDescription() + "\n\n" +
                    "Contacto: " + pet.getReporterName() + " - " + pet.getId();

            // 2. Obtener el ID del recurso de la imagen (asumiendo que pet.getImageName() devuelve "dog1", "cat1", etc.)
            int imageResId = getResources().getIdentifier(
                    pet.getImageUrl(),
                    "drawable",
                    getPackageName()
            );

            // 3. Si no hay imagen, compartir solo texto
            if (imageResId == 0) {
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.setType("text/plain");
                textShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mascota encontrada: " + pet.getName());
                textShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(textShareIntent, "Compartir mascota"));
                return;
            }

            // 4. Obtener la URI de la imagen usando FileProvider
            Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + imageResId);

            // 5. Crear el Intent para compartir con imagen y texto
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mascota encontrada: " + pet.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // 6. Mostrar el diálogo de compartir
            startActivity(Intent.createChooser(shareIntent, "Compartir mascota"));

        } catch (Exception e) {
            Log.e(TAG, "Error al compartir mascota: " + e.getMessage());

            // Fallback: compartir solo texto si hay error
            Intent fallbackIntent = new Intent(Intent.ACTION_SEND);
            fallbackIntent.setType("text/plain");
            fallbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Mascota encontrada: " + pet.getName());
            fallbackIntent.putExtra(Intent.EXTRA_TEXT, "¡Mira esta mascota en PetFinder!\nNombre: " + pet.getName());
            startActivity(Intent.createChooser(fallbackIntent, "Compartir mascota"));
        }
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