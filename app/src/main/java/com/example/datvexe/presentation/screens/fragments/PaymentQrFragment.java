package com.example.datvexe.presentation.screens.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datvexe.R;
import com.example.datvexe.presentation.viewmodel.PaymentQrViewModel;
import com.example.datvexe.domain.model.BusSchedule;

import java.util.ArrayList;

public class PaymentQrFragment extends Fragment {

    private PaymentQrViewModel mViewModel;

    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";
    private BusSchedule schedule;
    private String busType;
    private  String code;
    private ArrayList<String> selectedSeats;
    private int totalPrice;
    private int currentFloor;

    public static PaymentQrFragment newInstance() {
        return new PaymentQrFragment();
    }

    public static PaymentQrFragment newInstance(BusSchedule schedule, String busType,
                                                ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {
        PaymentQrFragment fragment = new PaymentQrFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE, schedule);
        args.putString(ARG_BUS_TYPE, busType);
        args.putStringArrayList(ARG_SELECTED_SEATS, selectedSeats);
        args.putInt(ARG_TOTAL_PRICE, totalPrice);
        args.putInt(ARG_CURRENT_FLOOR, currentFloor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            schedule = getArguments().getParcelable(ARG_SCHEDULE);
            busType = getArguments().getString(ARG_BUS_TYPE);
            selectedSeats = getArguments().getStringArrayList(ARG_SELECTED_SEATS);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE, 0);
            currentFloor = getArguments().getInt(ARG_CURRENT_FLOOR, 1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_qr, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentQrViewModel.class);
        // TODO: Use the ViewModel
    }

}