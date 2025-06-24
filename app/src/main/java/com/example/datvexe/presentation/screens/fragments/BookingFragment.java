package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.presentation.adapter.BookingAdapter;
import com.example.datvexe.presentation.viewmodel.BookingViewModel;

import java.util.List;


public class BookingFragment extends Fragment {

    // Tạm thời hardcode userId - trong thực tế sẽ lấy từ SharedPreferences hoặc Session
    private static final String USER_ID = "67a5a8bc040810b61bb8e672";
    private BookingViewModel viewModel;
    private BookingAdapter adapter;
    private RecyclerView rvBookings;
    private ProgressBar progressBar;
    private TextView tvError;
    private TextView tvEmpty;

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
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        initViews(view);
        setupRecyclerView();
        observeViewModel();

        // Load dữ liệu
        Log.d("boolingFragment", "loadBook");
        viewModel.loadBookings(USER_ID);

        return view;
    }

    private void initViews(View view) {
        rvBookings = view.findViewById(R.id.rv_bookings);
        progressBar = view.findViewById(R.id.progress_bar);
        tvError = view.findViewById(R.id.tv_error);
        tvEmpty = view.findViewById(R.id.tv_empty);
    }

    private void setupRecyclerView() {
        adapter = new BookingAdapter();
        rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBookings.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.bookings.observe(getViewLifecycleOwner(), this::handleBookingsResult);
        viewModel.isLoading.observe(getViewLifecycleOwner(), this::handleLoadingState);
        viewModel.error.observe(getViewLifecycleOwner(), this::handleErrorState);
    }

    private void handleBookingsResult(List<BookingTrip> bookings) {
        Log.d("books", bookings.toString());
        Log.d("boolingFragment", "loadBook2");

        if (bookings != null && !bookings.isEmpty()) {
            adapter.setBookings(bookings);
            rvBookings.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
        } else {
            rvBookings.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        }
    }

    private void handleLoadingState(Boolean isLoading) {
        if (isLoading != null && isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            rvBookings.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void handleErrorState(String error) {
        if (error != null && !error.isEmpty()) {
            tvError.setText(error);
            tvError.setVisibility(View.VISIBLE);
            rvBookings.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }
}