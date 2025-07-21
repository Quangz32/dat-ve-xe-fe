package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.databinding.FragmentBookingConfirmBinding;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.BookingConfirmViewModel;
import com.example.datvexe.presentation.viewmodel.BookingTripViewModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingConfirmFragment extends Fragment {

    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";

    private BusSchedule schedule;
    private BookingTripViewModel bookingTripViewModel;
    private String busType;
    private  String code;
    private ArrayList<String> selectedSeats;
    private int totalPrice;
    private int currentFloor;
    private FragmentBookingConfirmBinding viewBinding;
    private BookingConfirmViewModel viewModel;
    private final int TAB_INDEX = 1;
    

    public BookingConfirmFragment() {}


    public static BookingConfirmFragment newInstance(BusSchedule schedule, String busType,
                                                     ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {

        BookingConfirmFragment fragment = new BookingConfirmFragment();
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
        viewBinding = FragmentBookingConfirmBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(BookingConfirmViewModel.class);

        setupViewModel();
        observeViewModel();
        viewModel.loadUser();

        // Gán tổng tiền
        viewBinding.tvTotal.setText(String.format("%,d đ", totalPrice));

        // Gán điểm đến
        if (schedule != null) {
            viewBinding.tvRoute.setText(schedule.getRoute());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateString = sdf.format(schedule.getDate());
            viewBinding.tvDate.setText(dateString);
        }

        viewBinding.btnBack.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                SeatSelectedFragment seatSelectedFragment = SeatSelectedFragment.newInstance(
                        schedule,
                        busType,
                        selectedSeats,
                        totalPrice,
                        currentFloor
                );
                ((MainActivity) requireActivity()).navigateToFragment(seatSelectedFragment);
            }
        });


//        setupObservers();
        setupClickListeners();
        // Xử lý nút back
        return viewBinding.getRoot();
    }

    private void setupViewModel() {
         bookingTripViewModel = new ViewModelProvider(this).get(BookingTripViewModel.class);
        
    }


    private void observeViewModel() {
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                viewBinding.edtName.setText(user.getFullname());
                viewBinding.edtPhone.setText(user.getPhone());
                viewBinding.edtEmail.setText(user.getEmail());
                // Nếu có trường address:
                // viewBinding.edtAddress.setText(user.getAddress());
                viewBinding.tvMembership.setText(getMembershipLevel(user.getLoyaltyPoints()));
            }
        });



    }

    private String getMembershipLevel(int loyaltyPoints) {
        if (loyaltyPoints >= 10000) {
            return "Kim cương";
        } else if (loyaltyPoints >= 5000) {
            return "Vàng";
        } else if (loyaltyPoints >= 1000) {
            return "Bạc";
        } else if (loyaltyPoints >= 100) {
            return "Đồng";
        } else {
            return "Thành viên mới";
        }
    }
    private void setupClickListeners() {

        viewBinding.btnBooking.setOnClickListener(v -> {
            String busSchedule = schedule.getId();
            Long totalPrice1 = (long)totalPrice/1000;
            String pickupLocation = schedule.getBenXeKhoiHanh();
            String dropoffLocation = schedule.getBenXeDichDen();
            List<String> seats = selectedSeats;
            String note = viewBinding.edtNote.getText().toString().trim();
            String paymentMethod = "cash";
            Boolean exportInvoice = viewBinding.switchInvoice.isChecked();
            Date departureTime = schedule.getDepartureTime();

            Log.d("PaymentFragment",
                    "Diem di:" + pickupLocation + "Diem den" + dropoffLocation);
            Log.d("xxx-call-create","");
            bookingTripViewModel.bookingTripCreate("", busSchedule, totalPrice1, seats, pickupLocation, dropoffLocation, departureTime,  exportInvoice,  note, paymentMethod);

            bookingTripViewModel.bookingTrip.observe(getViewLifecycleOwner(), new Observer<BookingTrip>() {
                @Override
                public void onChanged(BookingTrip bookingTrip) {
                    Log.d("xxx", bookingTrip.toString());
                }
            });
            navigateToPayment();
        });

    }


    public void navigateToPayment()
    {
        if (requireActivity() instanceof MainActivity) {
            PaymentFragment paymentFragment = PaymentFragment.newInstance(
                    schedule,
                    busType,
                    selectedSeats,
                    totalPrice,
                    currentFloor
            );
            ((MainActivity) requireActivity()).navigateToFragment(paymentFragment);

        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}