package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datvexe.R;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.presentation.screens.activities.MainActivity;

import java.util.ArrayList;

/**
 * Fragment for displaying detailed information about a bus route.
 */
public class RouteDetailFragment extends Fragment {

    private static final String ARG_SCHEDULE = "schedule";
    private static final String ARG_BUS_TYPE = "bus_type";
    private static final String ARG_SELECTED_SEATS = "selected_seats";
    private static final String ARG_TOTAL_PRICE = "total_price";
    private static final String ARG_CURRENT_FLOOR = "current_floor";

    private BusSchedule schedule;
    private String busType;
    private ArrayList<String> selectedSeats;
    private int totalPrice;
    private int currentFloor;

    private TextView tvRouteTitle;
    private TextView tvRouteTime;
    private ImageView ivBusImage;
    private TextView tvFirstStop;
    private TextView tvMiddleStop;
    private TextView tvLastStop;
    private ImageButton btnBack;

    public RouteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of RouteDetailFragment.
     *
     * @param schedule The bus schedule to show details for
     * @param busType The type of bus (e.g., "BUS34", "BUS20")
     * @return A new instance of RouteDetailFragment
     */
    public static RouteDetailFragment newInstance(BusSchedule schedule, String busType, 
            ArrayList<String> selectedSeats, int totalPrice, int currentFloor) {
        RouteDetailFragment fragment = new RouteDetailFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupData();
        setupListeners();
    }

    private void initViews(View view) {
        tvRouteTitle = view.findViewById(R.id.tvRouteTitle);
        tvRouteTime = view.findViewById(R.id.tvRouteTime);
        ivBusImage = view.findViewById(R.id.ivBusImage);
        tvFirstStop = view.findViewById(R.id.tvFirstStop);
        tvMiddleStop = view.findViewById(R.id.tvMiddleStop);
        tvLastStop = view.findViewById(R.id.tvLastStop);
        btnBack = view.findViewById(R.id.btnBack);
    }

    private void setupData() {
        if (schedule != null) {
            String routeTitle = schedule.getBenXeKhoiHanhDetail().getTenBenXe() + " - " + schedule.getBenXeDichDenDetail().getTenBenXe();
            tvRouteTitle.setText(routeTitle);
            
            String routeTime = schedule.getTimeStart() + " - " + schedule.getTimeEnd();
            tvRouteTime.setText(routeTime);
            
            ivBusImage.setImageResource(R.drawable.banner2);
            
            tvFirstStop.setText(schedule.getBenXeKhoiHanhDetail().getTenBenXe());
            tvMiddleStop.setText("Trạm dừng nghỉ KM68");
            tvLastStop.setText(schedule.getBenXeDichDenDetail().getTenBenXe());
        }
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                // Navigate back to the seat selection fragment with preserved state
                SeatSelectedFragment seatSelectedFragment = SeatSelectedFragment.newInstance(
                    schedule, 
                    busType,
                    selectedSeats,
                    totalPrice,
                    currentFloor
                );
                ((MainActivity) getActivity()).navigateToFragment(seatSelectedFragment);
            }
        });
    }
} 
