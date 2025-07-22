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
import com.example.datvexe.databinding.FragmentPaymentQrBinding;
import com.example.datvexe.databinding.FragmentPaymentVnPayBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.PaymentVnPayViewModel;
import com.example.datvexe.domain.model.BusSchedule;

import java.util.ArrayList;

public class PaymentVnPayFragment extends Fragment {

    private PaymentVnPayViewModel mViewModel;
private FragmentPaymentVnPayBinding viewBinding;
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

    public static PaymentVnPayFragment newInstance() {
        return new PaymentVnPayFragment();
    }

    public static PaymentVnPayFragment newInstance(BusSchedule schedule, String busType,
                                                   ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {
        PaymentVnPayFragment fragment = new PaymentVnPayFragment();
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
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        viewBinding = FragmentPaymentVnPayBinding.inflate(inflater, container, false);
//        viewModel = new ViewModelProvider(this).get(BookingConfirmViewModel.class);

//        setupViewModel();
//        observeViewModel();
//        viewModel.loadUser();

//        // Gán tổng tiền
//        viewBinding.tvTotal.setText(String.format("%,d đ", totalPrice));
//
//        // Gán điểm đến
//        if (schedule != null) {
//            viewBinding.tvRoute.setText(schedule.getRoute());
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            String dateString = sdf.format(schedule.getDate());
//            viewBinding.tv.setText(dateString);
//        }

        // viewBinding.btnBackHome.setOnClickListener(v -> {
        //     if (requireActivity() instanceof MainActivity) {
        //         HomeFragment seatSelectedFragment =HomeFragment.newInstance();
        //         ((MainActivity) requireActivity()).navigateToFragment(seatSelectedFragment);
        //     }
        // });

        // viewBinding.btnPay.setOnClickListener(v -> {
        //     if (requireActivity() instanceof MainActivity) {
        //         PaymentSuccessFragment seatSelectedFragment =PaymentSuccessFragment.newInstance();
        //         ((MainActivity) requireActivity()).navigateToFragment(seatSelectedFragment);
        //     }
        // });


//        setupObservers();
//        setupClickListeners();
        // Xử lý nút back
        return viewBinding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentVnPayViewModel.class);
        // TODO: Use the ViewModel
    }

}