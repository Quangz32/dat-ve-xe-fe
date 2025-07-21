package com.example.datvexe.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.BookingTrip;

import java.util.ArrayList;
import java.util.List;

public class LoyaltyBookingAdapter extends RecyclerView.Adapter<LoyaltyBookingAdapter.ViewHolder> {

    private List<BookingTrip> bookings = new ArrayList<>();

    public void setBookings(List<BookingTrip> list){
        bookings = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loyalty_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingTrip b = bookings.get(position);
        String code = b.getCode();
        String route = b.getBusScheduleDetail()!=null? b.getBusScheduleDetail().getRoute():"N/A";
        holder.tvCode.setText("Mã đặt: "+code);
        holder.tvRoute.setText("Tuyến: "+route);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCode;
        TextView tvRoute;
        TextView tvPlusOne;
        ViewHolder(View itemView){
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_booking_code);
            tvRoute = itemView.findViewById(R.id.tv_booking_route);
            tvPlusOne = itemView.findViewById(R.id.tv_plus_one);
        }
    }
} 