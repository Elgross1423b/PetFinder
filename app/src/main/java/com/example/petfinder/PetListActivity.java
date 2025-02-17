package com.example.petfinder;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PetListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.recyclerView);
        petList = new ArrayList<>();
        petAdapter = new PetAdapter(petList, new PetAdapter.OnItemClickListener() {
            @Override
            public void onMessageClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Mensaje a: " + pet.getName(), Toast.LENGTH_SHORT).show();
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
            public void onSaveClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Publicación guardada: " + pet.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLikeClick(Pet pet) {
                Toast.makeText(PetListActivity.this, "Te gusta: " + pet.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(petAdapter);
        fetchPets();
    }

    private void fetchPets() {
        String url = "http://10.0.2.2/petfinder/get_pets.php";  // URL para obtener mascotas

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray petsArray = new JSONArray(response);

                        for (int i = 0; i < petsArray.length(); i++) {
                            JSONObject petObject = petsArray.getJSONObject(i);
                            Pet pet = new Pet(
                                    petObject.getInt("id"),
                                    petObject.getString("NAME"),
                                    petObject.getString("breed"),
                                    petObject.getString("age"),
                                    petObject.getString("description"),  // Asegúrate de tener esta clave en el JSON
                                    petObject.getString("reporterName"),  // Asegúrate de tener esta clave en el JSON
                                    petObject.getString("imageUrl")      // Asegúrate de tener esta clave en el JSON
                            );

                            petList.add(pet);
                        }

                        petAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(PetListActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(PetListActivity.this, "Volley error", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
