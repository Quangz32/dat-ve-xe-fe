package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.databinding.FragmentBookingBinding;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.presentation.adapter.BookingAdapter;
import com.example.datvexe.presentation.viewmodel.BookingViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingFragment extends Fragment {

    // Tạm thời hardcode userId - trong thực tế sẽ lấy từ SharedPreferences hoặc Session
    private static final String USER_ID = "67a5a8bc040810b61bb8e672";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentBookingBinding.inflate(inflater, container, false);
        setupToolbar();
        setupRecyclerView();
        observeViewModel();
        setupListener();

        // Load dữ liệu
        viewModel.loadBookings(USER_ID);
        viewModel.loadHistoryBooking(USER_ID);

        return viewBinding.getRoot();
    }

    private void setupListener() {
        viewBinding.btnHistory.setOnClickListener(v -> {
            viewModel.toggleBookingHistory();
            Log.d("history", Objects.requireNonNull(viewModel.bookingsHistory.getValue()).toString());
        });

        viewBinding.toolbar.setNavigationOnClickListener(v -> {
            viewModel.showBookings();
        });
    }

    private void setupToolbar() {
        // Thiết lập Toolbar
        ((AppCompatActivity) Objects.requireNonNull(getActivity()))
                .setSupportActionBar(viewBinding.toolbar);
        viewBinding.toolbar.setTitle("Booking");
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
            viewBinding.toolbar.setTitle("Lịch sử");
            viewBinding.btnHistory.setVisibility(View.GONE);
            viewBinding.cardVehicleType.setVisibility(View.GONE);
            showOrHideBackBtnOnToolbar(true);
            showBookingsList(viewModel.bookingsHistory.getValue());
        } else {
            viewBinding.toolbar.setTitle("Booking");
            viewBinding.btnHistory.setVisibility(View.VISIBLE);
            viewBinding.cardVehicleType.setVisibility(View.VISIBLE);
            showOrHideBackBtnOnToolbar(false);
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

    private void showOrHideBackBtnOnToolbar(boolean show) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Objects.requireNonNull(activity.getSupportActionBar())
                    .setDisplayHomeAsUpEnabled(show);
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