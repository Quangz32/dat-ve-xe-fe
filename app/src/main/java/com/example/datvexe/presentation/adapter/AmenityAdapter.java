package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;

import java.util.List;

public class AmenityAdapter extends RecyclerView.Adapter<AmenityAdapter.AmenityViewHolder> {

    private final List<Amenity> amenities;

    public AmenityAdapter(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    @NonNull
    @Override
    public AmenityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenity, parent, false);
        return new AmenityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenityViewHolder holder, int position) {
        Amenity amenity = amenities.get(position);
        holder.bind(amenity);
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    static class AmenityViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;

        public AmenityViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(Amenity amenity) {
            // Set icon based on the icon name
            int iconResId = itemView.getContext().getResources().getIdentifier(
                    "baseline_" + amenity.getIcon().replace("-outline", "") + "_24",
                    "drawable",
                    itemView.getContext().getPackageName()
            );
            ivIcon.setImageResource(iconResId);
            tvName.setText(amenity.getName());
        }
    }

    public static class Amenity {
        private final int id;
        private final String name;
        private final String icon;

        public Amenity(int id, String name, String icon) {
            this.id = id;
            this.name = name;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }
    }
} 
