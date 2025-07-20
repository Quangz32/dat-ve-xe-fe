package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.R;
import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.domain.model.User;
import com.example.datvexe.presentation.viewmodel.UserViewModel;
import com.example.datvexe.presentation.adapter.LoyaltyBookingAdapter;
import com.example.datvexe.presentation.viewmodel.BookingViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@AndroidEntryPoint
public class LoyaltyFragment extends Fragment {
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private UserViewModel userViewModel;
    private BookingViewModel bookingViewModel;
    private LoyaltyBookingAdapter bookingAdapter;
    private TextView tvLoyalty;

    public LoyaltyFragment() {
        // Required empty public constructor
    }

    public static LoyaltyFragment newInstance() {
        return new LoyaltyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loyalty, container, false);
        tvLoyalty = view.findViewById(R.id.tv_loyalty_points);
        setupRecyclerView(view);
        observeBookings();
        bookingViewModel.loadHistoryBooking();
        observeUser();
        loadUserProfile();
        return view;
    }

    private void loadUserProfile() {
        String userId = sharedPreferencesManager.getUserId();
        if (userId != null) {
            userViewModel.loadUserProfile(userId);
        } else {
            tvLoyalty.setText("Không tìm thấy userId");
        }
    }

    private void observeUser() {
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Integer points = user.getLoyaltyPoints();
                tvLoyalty.setText("Điểm Loyalty của bạn: " + (points != null ? points : 0));
            }
        });
        userViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvLoyalty.setText("Lỗi: " + error);
            }
        });
    }

    private void setupRecyclerView(View root){
        bookingAdapter = new LoyaltyBookingAdapter();
        androidx.recyclerview.widget.RecyclerView rv = root.findViewById(R.id.rv_booking_history);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(bookingAdapter);
    }

    private void observeBookings(){
        bookingViewModel.bookingsHistory.observe(getViewLifecycleOwner(), bookings -> {
            if(bookings!=null){
                java.util.List<com.example.datvexe.domain.model.BookingTrip> completed = new java.util.ArrayList<>();
                for(com.example.datvexe.domain.model.BookingTrip b: bookings){
                    if("completed".equalsIgnoreCase(b.getStatus())){
                        completed.add(b);
                    }
                }
                bookingAdapter.setBookings(completed);
            }
        });
    }
} 