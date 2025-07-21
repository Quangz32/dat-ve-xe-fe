package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.databinding.ItemLocationBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Map.Entry<String, List<String>>> locations = new ArrayList<>();
    private OnStationClickListener listener;

    public interface OnStationClickListener {
        void onStationClick(String stationName);
    }

    public void setLocations(List<Map.Entry<String, List<String>>> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    public void setOnStationClickListener(OnStationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLocationBinding binding = ItemLocationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new LocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Map.Entry<String, List<String>> location = locations.get(position);
        holder.binding.tvProvinceName.setText(location.getKey());

        BusStationAdapter stationAdapter = new BusStationAdapter();
        holder.binding.rvStations.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.binding.rvStations.setAdapter(stationAdapter);
        stationAdapter.setStations(location.getValue());
        stationAdapter.setOnStationClickListener(stationName -> {
            if (listener != null) {
                listener.onStationClick(stationName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        private final ItemLocationBinding binding;

        public LocationViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 
