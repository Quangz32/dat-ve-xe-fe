package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.databinding.ItemBusStationBinding;

import java.util.ArrayList;
import java.util.List;

public class BusStationAdapter extends RecyclerView.Adapter<BusStationAdapter.BusStationViewHolder> {
    private List<String> stations = new ArrayList<>();
    private OnStationClickListener listener;

    public interface OnStationClickListener {
        void onStationClick(String stationName);
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
        notifyDataSetChanged();
    }

    public void setOnStationClickListener(OnStationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BusStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBusStationBinding binding = ItemBusStationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BusStationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusStationViewHolder holder, int position) {
        String station = stations.get(position);
        holder.binding.tvStationName.setText(station);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onStationClick(station);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    static class BusStationViewHolder extends RecyclerView.ViewHolder {
        private final ItemBusStationBinding binding;

        public BusStationViewHolder(@NonNull ItemBusStationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 
