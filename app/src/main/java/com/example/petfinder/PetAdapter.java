package com.example.petfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private final OnItemClickListener listener;

    // Interfaz para manejar los clicks en los botones
    public interface OnItemClickListener {
        void onMessageClick(Pet pet);
        void onCommentClick(Pet pet);
        void onShareClick(Pet pet);
        void onLikeClick(Pet pet);
    }

    public PetAdapter(List<Pet> petList, OnItemClickListener listener) {
        this.petList = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.bind(pet, listener);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    // Método para actualizar la lista de mascotas
    public void updatePets(List<Pet> newPets) {
        petList.clear();
        petList.addAll(newPets);
        notifyDataSetChanged();
    }

    // ViewHolder class
    static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petImageView;
        TextView nameTextView, breedTextView, ageTextView;
        TextView descriptionTextView, reporterNameTextView, dateTextView;
        Button likeButton, messageButton, shareButton;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar vistas
            petImageView = itemView.findViewById(R.id.petImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            breedTextView = itemView.findViewById(R.id.breedTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            reporterNameTextView = itemView.findViewById(R.id.reporterNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            likeButton = itemView.findViewById(R.id.likeButton);
            messageButton = itemView.findViewById(R.id.messageButton);
            shareButton = itemView.findViewById(R.id.shareButton);
        }

        public void bind(final Pet pet, final OnItemClickListener listener) {
            // Configurar los datos de la mascota en las vistas
            nameTextView.setText(pet.getName());
            breedTextView.setText(pet.getBreed());
            ageTextView.setText(pet.getAge());
            descriptionTextView.setText(pet.getDescription());
            reporterNameTextView.setText(pet.getReporterName());
            dateTextView.setText("Hoy"); // Puedes cambiar esto según tus datos

            // Configurar la imagen
            try {
                int imageResId = itemView.getContext().getResources()
                        .getIdentifier(pet.getImageUrl(), "drawable",
                                itemView.getContext().getPackageName());

                if (imageResId != 0) {
                    petImageView.setImageResource(imageResId);
                } else {
                    // Imagen por defecto si no se encuentra
                    petImageView.setImageResource(R.drawable.ic_pet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                petImageView.setImageResource(R.drawable.ic_pet);
            }

            // Configurar los listeners de los botones
            likeButton.setOnClickListener(v -> listener.onLikeClick(pet));
            messageButton.setOnClickListener(v -> listener.onCommentClick(pet));
            shareButton.setOnClickListener(v -> listener.onShareClick(pet));

            // También puedes configurar el clic en toda la tarjeta si lo deseas
            itemView.setOnClickListener(v -> listener.onMessageClick(pet));
        }
    }
}