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
import com.example.datvexe.databinding.FragmentBookingConfirmBinding;
import com.example.datvexe.databinding.FragmentPaymentSuccessBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.BookingConfirmViewModel;
import com.example.datvexe.presentation.viewmodel.PaymentSuccessViewModel;
import com.example.datvexe.domain.model.BusSchedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PaymentSuccessFragment extends Fragment {

    private PaymentSuccessViewModel mViewModel;
    private FragmentPaymentSuccessBinding viewBinding;
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

    public static PaymentSuccessFragment newInstance() {
        return new PaymentSuccessFragment();
    }

    public static PaymentSuccessFragment newInstance(BusSchedule schedule, String busType,
                                                     ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
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
        viewBinding = FragmentPaymentSuccessBinding.inflate(inflater, container, false);
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

        viewBinding.btnHome.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                HomeFragment seatSelectedFragment =HomeFragment.newInstance();
                ((MainActivity) requireActivity()).navigateToFragment(seatSelectedFragment);
            }
        });


//        setupObservers();
//        setupClickListeners();
        // Xử lý nút back
        return viewBinding.getRoot();
    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_payment_success, container, false);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentSuccessViewModel.class);
        // TODO: Use the ViewModel
    }

}