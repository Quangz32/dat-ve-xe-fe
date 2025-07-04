package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.presentation.model.Seat;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seats;
    private final OnSeatClickListener listener;

    public interface OnSeatClickListener {
        void onSeatClick(Seat seat);
    }

    public SeatAdapter(List<Seat> seats, OnSeatClickListener listener) {
        this.seats = seats;
        this.listener = listener;
    }

    public void updateSeats(List<Seat> newSeats) {
        this.seats = newSeats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.bind(seat);
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    class SeatViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout seatLayout;
        private final TextView tvSeatId;
        private final TextView tvSeatPrice;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatLayout = itemView.findViewById(R.id.seatLayout);
            tvSeatId = itemView.findViewById(R.id.tvSeatId);
            tvSeatPrice = itemView.findViewById(R.id.tvSeatPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Seat seat = seats.get(position);
                    // Chỉ cho phép click nếu ghế chưa được đặt
                    if (!seat.isBooked()) {
                        listener.onSeatClick(seat);
                    }
                }
            });
        }

        public void bind(Seat seat) {
            tvSeatId.setText(seat.getId());
            
            // Format price to K format
            int priceInK = seat.getPrice() / 1000;
            String formattedPrice = priceInK + "K";
            tvSeatPrice.setText(formattedPrice);
            
            // Update UI based on seat selection state and booking state
            if (seat.isBooked()) {
                // Ghế đã được đặt
                seatLayout.setBackgroundResource(R.drawable.seat_booked_background);
                tvSeatId.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.darkgray));
                tvSeatPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.darkgray));
                itemView.setEnabled(false); // Không cho phép click vào ghế đã đặt
            } else if (seat.isSelected()) {
                // Ghế đang được chọn
                seatLayout.setBackgroundResource(R.drawable.seat_selected_background);
                tvSeatId.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                tvSeatPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.white));
                itemView.setEnabled(true);
            } else {
                // Ghế còn trống và chưa chọn
                seatLayout.setBackgroundResource(R.drawable.seat_background);
                tvSeatId.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.black));
                tvSeatPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.darkgray_700));
                itemView.setEnabled(true);
            }
        }
    }
} 
