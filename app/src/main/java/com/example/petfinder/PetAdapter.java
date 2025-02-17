package com.example.petfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

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

        // Asignar los listeners a los botones
        holder.messageButton.setOnClickListener(v -> listener.onMessageClick(pet));
        holder.commentButton.setOnClickListener(v -> listener.onCommentClick(pet));
        holder.shareButton.setOnClickListener(v -> listener.onShareClick(pet));
        holder.saveButton.setOnClickListener(v -> listener.onSaveClick(pet));
        holder.likeButton.setOnClickListener(v -> listener.onLikeClick(pet));
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView name, breed, age;
        Button messageButton, commentButton, shareButton, saveButton, likeButton;

        public PetViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.petName);
            breed = itemView.findViewById(R.id.petBreed);
            age = itemView.findViewById(R.id.petAge);
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
