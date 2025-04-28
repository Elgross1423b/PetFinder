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
        holder.bind(petList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public void updatePets(List<Pet> newPets) {
        petList = newPets;
        notifyDataSetChanged();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        private final ImageView petImage;
        private final ImageView reporterImage;
        private final TextView nameText, breedText, ageText;
        private final TextView reporterText, descriptionText, locationText;
        private final Button likeBtn, messageBtn, shareBtn;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImageView);
            reporterImage = itemView.findViewById(R.id.reporterImageView);
            nameText = itemView.findViewById(R.id.nameTextView);
            breedText = itemView.findViewById(R.id.breedTextView);
            ageText = itemView.findViewById(R.id.ageTextView);
            reporterText = itemView.findViewById(R.id.reporterNameTextView);
            descriptionText = itemView.findViewById(R.id.descriptionTextView);
            locationText = itemView.findViewById(R.id.locationTextView);
            likeBtn = itemView.findViewById(R.id.likeButton);
            messageBtn = itemView.findViewById(R.id.messageButton);
            shareBtn = itemView.findViewById(R.id.shareButton);
        }

        public void bind(final Pet pet, final OnItemClickListener listener) {
            nameText.setText(pet.getName());
            breedText.setText(pet.getBreed());
            ageText.setText(pet.getAge());
            descriptionText.setText(pet.getDescription());
            reporterText.setText(pet.getReporterName());
            locationText.setText("Ubicación: " + pet.getLocation());

            // Cargar imagen de la mascota
            int petImageResId = itemView.getContext().getResources()
                    .getIdentifier(pet.getImageUrl(), "drawable",
                            itemView.getContext().getPackageName());
            petImage.setImageResource(petImageResId != 0 ? petImageResId : R.drawable.ic_pet);

            // Cargar imagen del reportero
            int reporterImageResId = itemView.getContext().getResources()
                    .getIdentifier(pet.getReporterImage(), "drawable",
                            itemView.getContext().getPackageName());
            reporterImage.setImageResource(reporterImageResId != 0 ? reporterImageResId : R.drawable.ic_profile);

            // Listeners
            likeBtn.setOnClickListener(v -> listener.onLikeClick(pet));
            messageBtn.setOnClickListener(v -> listener.onMessageClick(pet));
            shareBtn.setOnClickListener(v -> listener.onShareClick(pet));
            itemView.setOnClickListener(v -> listener.onCommentClick(pet));
        }
    }
}