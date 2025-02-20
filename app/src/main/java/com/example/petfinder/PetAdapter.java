package com.example.petfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private final List<Pet> petList;
    private final OnItemClickListener listener;

    public PetAdapter(List<Pet> petList, OnItemClickListener listener) {
        this.petList = petList;
        this.listener = listener;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.name.setText(pet.getName());
        holder.breed.setText(pet.getBreed());
        holder.age.setText(pet.getAge());
        holder.description.setText(pet.getDescription());
        holder.reporterName.setText(pet.getReporterName());

        // Cargar la imagen (ejemplo con Glide o Picasso)
        // Glide.with(holder.itemView.getContext()).load(pet.getImageUrl()).into(holder.petImage);

        // Asignar los listeners a los botones
        holder.messageButton.setOnClickListener(v -> listener.onMessageClick(pet));
        holder.commentButton.setOnClickListener(v -> listener.onCommentClick(pet));
        holder.shareButton.setOnClickListener(v -> listener.onShareClick(pet));
        holder.saveButton.setOnClickListener(v -> listener.onSaveClick(pet));

        // Configurar el botón de like para alternar su estado
        holder.likeButton.setOnClickListener(v -> {
            boolean isSelected = holder.likeButton.isSelected();
            holder.likeButton.setSelected(!isSelected);
            listener.onLikeClick(pet);
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView name, breed, age, description, reporterName;
        Button messageButton, commentButton, shareButton, saveButton, likeButton;
        ImageView petImage;

        public PetViewHolder(View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImageView);
            name = itemView.findViewById(R.id.nameTextView);
            breed = itemView.findViewById(R.id.breedTextView);
            age = itemView.findViewById(R.id.ageTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            reporterName = itemView.findViewById(R.id.reporterNameTextView);
            messageButton = itemView.findViewById(R.id.messageButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            shareButton = itemView.findViewById(R.id.shareButton);
            saveButton = itemView.findViewById(R.id.saveButton);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }

    public interface OnItemClickListener {
        void onMessageClick(Pet pet);
        void onCommentClick(Pet pet);
        void onShareClick(Pet pet);
        void onSaveClick(Pet pet);
        void onLikeClick(Pet pet);
    }
}
