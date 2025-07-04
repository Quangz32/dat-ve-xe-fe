package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.databinding.ItemBusScheduleBinding;
import com.example.datvexe.domain.model.BusSchedule;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusScheduleAdapter extends RecyclerView.Adapter<BusScheduleAdapter.BusScheduleViewHolder> {
    private List<BusSchedule> schedules = new ArrayList<>();
    private OnScheduleClickListener listener;

    public interface OnScheduleClickListener {
        void onScheduleClick(BusSchedule schedule);
    }

    public void setSchedules(List<BusSchedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    public void setOnScheduleClickListener(OnScheduleClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BusScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBusScheduleBinding binding = ItemBusScheduleBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BusScheduleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusScheduleViewHolder holder, int position) {
        BusSchedule schedule = schedules.get(position);
        holder.bind(schedule);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    class BusScheduleViewHolder extends RecyclerView.ViewHolder {
        private final ItemBusScheduleBinding binding;

        public BusScheduleViewHolder(@NonNull ItemBusScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            
            // Set click listener for the entire item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onScheduleClick(schedules.get(position));
                }
            });
        }

        public void bind(BusSchedule schedule) {
            // Thông tin xe
            String busName = schedule.getBusName();
            if (schedule.getBusOperatorDetail() != null && schedule.getBusOperatorDetail().getTypeBusDetail() != null) {
                busName += " - " + schedule.getBusOperatorDetail().getTypeBusDetail().getName();
            }
            binding.tvBusName.setText(busName);
            
            // Route info
            binding.tvRoute.setText(schedule.getRoute());
            
            // Bus info with code and available seats
            String busId = schedule.getBusOperator() != null ? schedule.getBusOperator().getName() : "";
            String availableSeats = schedule.getAvailableSeats() != null ? schedule.getAvailableSeats() + " chỗ trống" : "";
            binding.tvBusInfo.setText(busId + (availableSeats.isEmpty() ? "" : " • " + availableSeats));
            
            // Format price
            binding.tvPrice.setText(formatPrice(schedule.getPrice()) + "đ");
            
            // Departure and arrival info
            binding.tvDepartureTime.setText(schedule.getDepartureTime());
            binding.tvDepartureLocation.setText(schedule.getDepartureLocation());
            binding.tvDuration.setText(schedule.getDuration());
            binding.tvArrivalTime.setText(schedule.getArrivalTime());
            binding.tvArrivalLocation.setText(schedule.getArrivalLocation());
        }
        
        private String formatPrice(double price) {
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            return formatter.format(price);
        }
    }
} 
