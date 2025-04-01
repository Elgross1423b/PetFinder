package com.example.petfinder;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<Pet> petList;
    private PetDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new PetDatabaseHelper(this);

        petList = databaseHelper.getAllPets();
        petAdapter = new PetAdapter(petList, new PetAdapter.OnItemClickListener() {
            @Override
            public void onMessageClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Mensaje a: " + pet.getReporterName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCommentClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Comentario en publicación de: " + pet.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Compartiendo publicación de: " + pet.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLikeClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Te gusta: " + pet.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(petAdapter);
    }
}