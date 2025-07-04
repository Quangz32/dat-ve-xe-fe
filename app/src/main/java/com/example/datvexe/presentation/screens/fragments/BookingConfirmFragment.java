package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.model.Seat;
import com.example.datvexe.presentation.screens.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingConfirmFragment extends Fragment {

    private static final String TAG = "BookingConfirmFragment";
    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_SELECTED_SEATS = "selectedSeats";
    private static final String ARG_TOTAL_PRICE = "totalPrice";
    private static final String ARG_BUS_TYPE = "busType";
    
    private BusSchedule schedule;
    private ArrayList<Seat> selectedSeats;
    private int totalPrice;
    private String busType;

    public BookingConfirmFragment() {
        // Required empty public constructor
    }

    public static BookingConfirmFragment newInstance(BusSchedule schedule, ArrayList<Seat> selectedSeats, 
                                                   int totalPrice, String busType) {
        BookingConfirmFragment fragment = new BookingConfirmFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE, schedule);
        args.putParcelableArrayList(ARG_SELECTED_SEATS, selectedSeats);
        args.putInt(ARG_TOTAL_PRICE, totalPrice);
        args.putString(ARG_BUS_TYPE, busType);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getArguments() != null) {
            // Extract data from arguments
            schedule = getArguments().getParcelable(ARG_SCHEDULE);
            selectedSeats = getArguments().getParcelableArrayList(ARG_SELECTED_SEATS);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE);
            busType = getArguments().getString(ARG_BUS_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Log the values as requested
        Log.d(TAG, "Schedule: " + (schedule != null ? schedule.toString() : "null"));
        
        StringBuilder seatInfo = new StringBuilder("Selected Seats: ");
        if (selectedSeats != null) {
            for (Seat seat : selectedSeats) {
                seatInfo.append(seat.getSeatNumber()).append(", ");
            }
        }
        Log.d(TAG, seatInfo.toString());
        
        Log.d(TAG, "Total Price: " + totalPrice);
        Log.d(TAG, "Bus Type: " + busType);

        // Back button
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                // Tạo SeatSelectedFragment mới với dữ liệu từ tham số hiện tại
                SeatSelectedFragment seatSelectedFragment = SeatSelectedFragment.newInstance(schedule, busType);
                ((MainActivity) requireActivity()).navigateToFragment(seatSelectedFragment);
            }
        });
    }
} 
