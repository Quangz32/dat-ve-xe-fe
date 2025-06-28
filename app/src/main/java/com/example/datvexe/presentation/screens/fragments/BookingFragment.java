package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.databinding.FragmentBookingBinding;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.presentation.adapter.BookingAdapter;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.BookingViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingFragment extends Fragment {

    // Tạm thời hardcode userId - trong thực tế sẽ lấy từ SharedPreferences hoặc Session
    private static final String USER_ID = "67a5a8bc040810b61bb8e672";
    private final int TAB_INDEX = 1;
    private FragmentBookingBinding viewBinding;

    private BookingViewModel viewModel;
    private BookingAdapter adapter;

    public BookingFragment() {
        // Required empty public constructor
    }

    public static BookingFragment newInstance() {
        return new BookingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentBookingBinding.inflate(inflater, container, false);
        setupRecyclerView();
        observeViewModel();
        setupListener();

        // Load dữ liệu
        viewModel.loadBookings(USER_ID);
        viewModel.loadHistoryBooking(USER_ID);

        //
        MainActivity mainActivity = (MainActivity) requireActivity();
        mainActivity.setTabNavigateBackCallback(TAB_INDEX, () -> {
            viewModel.toggleBookingHistory();
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, false);
            mainActivity.setActionBarTitle(TAB_INDEX, "Booking");
        });

        return viewBinding.getRoot();
    }

    private void setupListener() {
        viewBinding.btnHistory.setOnClickListener(v -> {
            viewModel.toggleBookingHistory();
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.setActionBarTitle(TAB_INDEX, "Lịch sử đặt xe");
        });

    }

    private void setupRecyclerView() {
        adapter = new BookingAdapter();
        viewBinding.rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        viewBinding.rvBookings.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.bookings.observe(getViewLifecycleOwner(), (bookings) -> {
            if (Boolean.FALSE.equals(viewModel.showHistory.getValue())) {
                showBookingsList(bookings);
            }
        });

        viewModel.bookingsHistory.observe(getViewLifecycleOwner(), (bookings) -> {
            if (Boolean.TRUE.equals(viewModel.showHistory.getValue())) {
                showBookingsList(bookings);
            }
        });

        viewModel.showHistory.observe(getViewLifecycleOwner(), this::handleShowBookingsAndHistory);
        viewModel.isLoading.observe(getViewLifecycleOwner(), this::handleLoadingState);
        viewModel.error.observe(getViewLifecycleOwner(), this::handleErrorState);
    }

    private void handleShowBookingsAndHistory(Boolean showHistory) {
        if (showHistory != null && showHistory) {
            viewBinding.btnHistory.setVisibility(View.GONE);
            viewBinding.cardVehicleType.setVisibility(View.GONE);
            showBookingsList(viewModel.bookingsHistory.getValue());
        } else {
            viewBinding.btnHistory.setVisibility(View.VISIBLE);
            viewBinding.cardVehicleType.setVisibility(View.VISIBLE);
            showBookingsList(viewModel.bookings.getValue());
        }
    }

    private void showBookingsList(List<BookingTrip> bookings) {
        if (bookings != null && !bookings.isEmpty()) {
            adapter.setBookings(bookings);
            viewBinding.rvBookings.setVisibility(View.VISIBLE);
            viewBinding.tvEmpty.setVisibility(View.GONE);
            viewBinding.tvError.setVisibility(View.GONE);
        } else {
            viewBinding.rvBookings.setVisibility(View.GONE);
            viewBinding.tvEmpty.setVisibility(View.VISIBLE);
            viewBinding.tvError.setVisibility(View.GONE);
        }
    }

    private void handleLoadingState(Boolean isLoading) {
        if (isLoading != null && isLoading) {
            viewBinding.progressBar.setVisibility(View.VISIBLE);
            viewBinding.rvBookings.setVisibility(View.GONE);
            viewBinding.tvError.setVisibility(View.GONE);
            viewBinding.tvEmpty.setVisibility(View.GONE);
        } else {
            viewBinding.progressBar.setVisibility(View.GONE);
        }
    }

    private void handleErrorState(String error) {
        if (error != null && !error.isEmpty()) {
            viewBinding.tvError.setText(error);
            viewBinding.tvError.setVisibility(View.VISIBLE);
            viewBinding.rvBookings.setVisibility(View.GONE);
            viewBinding.tvEmpty.setVisibility(View.GONE);
        } else {
            viewBinding.tvError.setVisibility(View.GONE);
        }
    }
}