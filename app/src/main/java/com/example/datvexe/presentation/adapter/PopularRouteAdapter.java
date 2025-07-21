package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.databinding.ItemPopularRouteBinding;
import com.example.datvexe.presentation.model.PopularRoute;

import java.util.ArrayList;
import java.util.List;

public class PopularRouteAdapter extends RecyclerView.Adapter<PopularRouteAdapter.PopularRouteViewHolder> {
    private List<PopularRoute> routes = new ArrayList<>();
    private OnRouteClickListener listener;

    public interface OnRouteClickListener {
        void onRouteClick(PopularRoute route);
    }

    public void setRoutes(List<PopularRoute> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    public void setOnRouteClickListener(OnRouteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularRouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPopularRouteBinding binding = ItemPopularRouteBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PopularRouteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRouteViewHolder holder, int position) {
        PopularRoute route = routes.get(position);
        holder.binding.tvRouteName.setText(route.getRouteName());
        holder.binding.tvPrice.setText(route.getPrice());
        holder.binding.tvDuration.setText(route.getDuration());
        holder.binding.imgBanner.setImageResource(route.getBannerResId());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRouteClick(route);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    static class PopularRouteViewHolder extends RecyclerView.ViewHolder {
        private final ItemPopularRouteBinding binding;

        public PopularRouteViewHolder(@NonNull ItemPopularRouteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 