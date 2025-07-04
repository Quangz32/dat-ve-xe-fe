package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;

import java.util.List;

public class BusImageAdapter extends RecyclerView.Adapter<BusImageAdapter.BusImageViewHolder> {

    private final List<Integer> images;

    public BusImageAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public BusImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_image, parent, false);
        return new BusImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusImageViewHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class BusImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        BusImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivBusImage);
        }
    }
} 
