package com.example.petfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_Pet_List extends Fragment {

    private static final String TAG = "Fragment_Pet_List";
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private PetDatabaseHelper databaseHelper;

    public Fragment_Pet_List() {
        // Required empty public constructor
    }

    public static Fragment_Pet_List newInstance() {
        return new Fragment_Pet_List();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize DatabaseHelper
        databaseHelper = new PetDatabaseHelper(requireContext());
        // Force database creation/update
        databaseHelper.getWritableDatabase().close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__pet__list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configure RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Load pets
        loadPets();
    }

    private void loadPets() {
        List<Pet> pets = databaseHelper.getAllPets();
        Log.d(TAG, "Number of pets loaded: " + pets.size());

        petAdapter = new PetAdapter(pets, new PetAdapter.OnItemClickListener() {
            @Override
            public void onMessageClick(Pet pet) {
                showToast("Contact: " + pet.getReporterName());
            }

            @Override
            public void onCommentClick(Pet pet) {
                showToast("Comment on: " + pet.getName());
            }

            @Override
            public void onShareClick(Pet pet) {
                sharePet(pet);
            }

            @Override
            public void onLikeClick(Pet pet) {
                showToast("Like: " + pet.getName());
            }

            @Override
            public void onSaveClick(Pet pet) {
                showToast("Saved: " + pet.getName());
            }
        });

        recyclerView.setAdapter(petAdapter);
    }

    private void sharePet(Pet pet) {
        try {
            // Create share text
            String shareText = "Check out this pet I found on PetFinder!\n\n" +
                    "Name: " + pet.getName() + "\n" +
                    "Type: " + pet.getBreed() + "\n" +
                    "Breed: " + pet.getBreed() + "\n" +
                    "Description: " + pet.getDescription() + "\n\n" +
                    "Contact: " + pet.getReporterName() + " - " + pet.getId();

            // Get image resource ID
            int imageResId = requireContext().getResources().getIdentifier(
                    pet.getImageUrl(),
                    "drawable",
                    requireContext().getPackageName()
            );

            // Share only text if no image
            if (imageResId == 0) {
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.setType("text/plain");
                textShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Found pet: " + pet.getName());
                textShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(textShareIntent, "Share pet"));
                return;
            }

            // Get image URI using resource
            Uri imageUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + imageResId);

            // Create share Intent with image and text
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Found pet: " + pet.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Show share dialog
            startActivity(Intent.createChooser(shareIntent, "Share pet"));

        } catch (Exception e) {
            Log.e(TAG, "Error sharing pet: " + e.getMessage());

            // Fallback: share only text
            Intent fallbackIntent = new Intent(Intent.ACTION_SEND);
            fallbackIntent.setType("text/plain");
            fallbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Found pet: " + pet.getName());
            fallbackIntent.putExtra(Intent.EXTRA_TEXT, "Check out this pet on PetFinder!\nName: " + pet.getName());
            startActivity(Intent.createChooser(fallbackIntent, "Share pet"));
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null); // Prevent memory leaks
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}