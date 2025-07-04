package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.BusOperators;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.adapter.AmenityAdapter;
import com.example.datvexe.presentation.adapter.BusImageAdapter;
import com.example.datvexe.presentation.screens.activities.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class BusInfoFragment extends Fragment {

    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_BUS_OPERATOR = "bus_operator";
    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";

    private String busType;
    private BusOperators busOperator;
    private BusSchedule schedule;
    private ArrayList<String> selectedSeats;
    private int totalPrice;
    private int currentFloor;
    private ViewPager2 viewPager;
    private CircleIndicator3 indicator;
    private RecyclerView rvAmenities;
    private TextView tvBusType;
    private TextView tvSeatCount;
    private TextView tvBusModel;
    private TextView tvManufacturingYear;
    private TextView tvFeatures;

    public static BusInfoFragment newInstance(String busType, BusOperators busOperator, BusSchedule schedule,
            ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {
        BusInfoFragment fragment = new BusInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BUS_TYPE, busType);
        args.putParcelable(ARG_BUS_OPERATOR, busOperator);
        args.putParcelable(ARG_SCHEDULE, schedule);
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
            busType = getArguments().getString(ARG_BUS_TYPE);
            busOperator = getArguments().getParcelable(ARG_BUS_OPERATOR);
            schedule = getArguments().getParcelable(ARG_SCHEDULE);
            selectedSeats = getArguments().getStringArrayList(ARG_SELECTED_SEATS);
            totalPrice = getArguments().getInt(ARG_TOTAL_PRICE, 0);
            currentFloor = getArguments().getInt(ARG_CURRENT_FLOOR, 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bus_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupImageSlideshow();
        setupAmenities();
        setupBusInfo();
    }

    private void initViews(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        indicator = view.findViewById(R.id.indicator);
        rvAmenities = view.findViewById(R.id.rvAmenities);
        tvBusType = view.findViewById(R.id.tvBusType);
        tvSeatCount = view.findViewById(R.id.tvSeatCount);
        tvBusModel = view.findViewById(R.id.tvBusModel);
        tvManufacturingYear = view.findViewById(R.id.tvManufacturingYear);
        tvFeatures = view.findViewById(R.id.tvFeatures);

        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                // Navigate back with preserved state
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
    }

    private void setupImageSlideshow() {
        List<Integer> images = new ArrayList<>();
        if ("BUS20".equals(busType)) {
            images.addAll(Arrays.asList(
                R.drawable.car24_1,
                R.drawable.car24_2,
                R.drawable.car24_3,
                R.drawable.car24_4
            ));
        } else {
            images.addAll(Arrays.asList(
                R.drawable.car34_1,
                R.drawable.car34_2,
                R.drawable.car34_3,
                R.drawable.car34_4
            ));
        }

        BusImageAdapter adapter = new BusImageAdapter(images);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    private void setupAmenities() {
        List<AmenityAdapter.Amenity> amenities = new ArrayList<>();
        if ("BUS20".equals(busType)) {
            amenities.addAll(Arrays.asList(
                new AmenityAdapter.Amenity(1, "Điều hòa", "snow-outline"),
                new AmenityAdapter.Amenity(2, "WiFi miễn phí", "wifi-outline"),
                new AmenityAdapter.Amenity(3, "Nước uống", "water-outline"),
                new AmenityAdapter.Amenity(4, "Chăn mền", "bed-outline"),
                new AmenityAdapter.Amenity(5, "Ổ cắm điện", "flash-outline"),
                new AmenityAdapter.Amenity(6, "Nhà vệ sinh", "water-outline")
            ));
        } else {
            amenities.addAll(Arrays.asList(
                new AmenityAdapter.Amenity(1, "Điều hòa", "snow-outline"),
                new AmenityAdapter.Amenity(2, "WiFi miễn phí", "wifi-outline"),
                new AmenityAdapter.Amenity(3, "Nước uống", "water-outline"),
                new AmenityAdapter.Amenity(4, "Chăn mền", "bed-outline"),
                new AmenityAdapter.Amenity(5, "Ổ cắm điện", "flash-outline"),
                new AmenityAdapter.Amenity(6, "Nhà vệ sinh", "water-outline"),
                new AmenityAdapter.Amenity(7, "Tivi", "tv-outline"),
                new AmenityAdapter.Amenity(8, "Massage ghế", "body-outline")
            ));
        }

        rvAmenities.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        rvAmenities.setAdapter(new AmenityAdapter(amenities));
    }

    private void setupBusInfo() {
        String seatCount = "BUS20".equals(busType) ? "20" : "34";
        tvBusType.setText(seatCount + " giường");
        tvSeatCount.setText(seatCount);
        tvBusModel.setText("Thaco Mobihome");
        tvManufacturingYear.setText("2024");
        tvFeatures.setText("Xe giường nằm " + seatCount + " chỗ tiện nghi");
    }
} 
