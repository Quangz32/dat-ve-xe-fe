package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.data.local.TripService;
import com.example.datvexe.databinding.FragmentBusScheduleBinding;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.domain.model.BusStation;
import com.example.datvexe.presentation.adapter.BusScheduleAdapter;
import com.example.datvexe.presentation.adapter.DateAdapter;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.BusScheduleViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BusScheduleFragment extends Fragment {
    private static final String TAG = "BusScheduleFragment";
    private FragmentBusScheduleBinding binding;
    private BusScheduleViewModel viewModel;
    private BusScheduleAdapter scheduleAdapter;
    private DateAdapter dateAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private TripService tripService = TripService.getInstance();

    private String fromStationId;
    private String toStationId;
    private String fromStationName;
    private String toStationName;
    private int passengerCount = 1;
    private Date selectedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BusScheduleViewModel.class);
        
        // Get arguments from bundle
        if (getArguments() != null) {
            // Get the station names (for display)
            fromStationName = getArguments().getString("departureLocation", "");
            toStationName = getArguments().getString("destinationLocation", "");
            
            // Get the station IDs (will be set later if directly passed)
            fromStationId = getArguments().getString("fromStationId");
            toStationId = getArguments().getString("toStationId");
            
            // Print debug info
            Log.d(TAG, "From station: " + fromStationName + " (ID: " + fromStationId + ")");
            Log.d(TAG, "To station: " + toStationName + " (ID: " + toStationId + ")");
            
            // If IDs are not provided, try to find them from names
            if (fromStationId == null && !fromStationName.isEmpty()) {
                BusStation station = tripService.findStation(fromStationName);
                if (station != null) {
                    fromStationId = station.getMaBenXe(); // Sử dụng maBenXe thay vì id
                }
            }
            
            if (toStationId == null && !toStationName.isEmpty()) {
                BusStation station = tripService.findStation(toStationName);
                if (station != null) {
                    toStationId = station.getMaBenXe(); // Sử dụng maBenXe thay vì id
                }
            }
            
            // Parse date
            String dateStr = getArguments().getString("date");
            if (dateStr != null) {
                try {
                    selectedDate = dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    selectedDate = new Date(); // Default to today
                }
            } else {
                selectedDate = new Date(); // Default to today
            }
            
            // Safely parse passengerCount
            try {
                String passengerCountStr = getArguments().getString("passengerCount");
                if (passengerCountStr != null) {
                    passengerCount = Integer.parseInt(passengerCountStr);
                }
            } catch (NumberFormatException e) {
                passengerCount = 1;
            }
            
            // Set values to ViewModel
            if (fromStationId != null) {
                BusStation fromStation = new BusStation();
                fromStation.setId(fromStationId);
                fromStation.setMaBenXe(fromStationId);  // Thêm mã bến xe
                fromStation.setName(fromStationName);
                fromStation.setTenBenXe(fromStationName);  // Thêm tên bến xe
                viewModel.setFromStation(fromStation);
            }
            
            if (toStationId != null) {
                BusStation toStation = new BusStation();
                toStation.setId(toStationId);
                toStation.setMaBenXe(toStationId);  // Thêm mã bến xe
                toStation.setName(toStationName);
                toStation.setTenBenXe(toStationName);  // Thêm tên bến xe
                viewModel.setToStation(toStation);
            }
            
            viewModel.setPassengerCount(passengerCount);
            
            if (selectedDate != null) {
                viewModel.setSelectedDate(selectedDate);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupListeners();
        setupObservers();
        setupDateSelector();
        
        // Show route information
        updateRouteInfo();
        
        // Load schedules
        if (fromStationId != null && toStationId != null) {
            Log.d(TAG, "Loading schedules with IDs: " + fromStationId + " -> " + toStationId);
            viewModel.loadSchedules();
        } else {
            // Show error if station IDs couldn't be resolved
            String message = "Không thể tìm thấy thông tin trạm xe, vui lòng thử lại";
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            Log.e(TAG, message + ": fromStationId=" + fromStationId + ", toStationId=" + toStationId);
            binding.emptyStateContainer.setVisibility(View.VISIBLE);
        }
    }

    private void updateRouteInfo() {
        String routeText = fromStationName + " → " + toStationName;
        binding.tvRoute.setText(routeText);
        binding.tvPassengerInfo.setText(passengerCount + " người");
    }

    private void setupViews() {
        // Setup RecyclerView
        setupRecyclerViews();
    }

    private void setupRecyclerViews() {
        // Setup date recycler view
        dateAdapter = new DateAdapter();
        binding.rvDates.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvDates.setAdapter(dateAdapter);

        // Setup bus schedule recycler view
        scheduleAdapter = new BusScheduleAdapter();
        binding.rvBusSchedules.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBusSchedules.setAdapter(scheduleAdapter);
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        binding.btnDepartureTime.setOnClickListener(v -> {
            // Handle departure time filter
            Toast.makeText(requireContext(), "Tính năng sắp xếp theo giờ khởi hành", Toast.LENGTH_SHORT).show();
        });

        binding.btnPrice.setOnClickListener(v -> {
            // Handle price filter
            Toast.makeText(requireContext(), "Tính năng sắp xếp theo giá", Toast.LENGTH_SHORT).show();
        });

        binding.btnPromotion.setOnClickListener(v -> {
            // Handle promotion filter
            Toast.makeText(requireContext(), "Tính năng lọc khuyến mãi", Toast.LENGTH_SHORT).show();
        });

        scheduleAdapter.setOnScheduleClickListener(schedule -> {
            // Handle schedule selection
            Toast.makeText(requireContext(), "Đã chọn: " + schedule.getBusName(), Toast.LENGTH_SHORT).show();
            // TODO: Navigate to booking screen
        });

        dateAdapter.setOnDateSelectedListener(date -> {
            // Load schedules for selected date
            viewModel.loadSchedulesForDate(date);
        });
    }

    private void setupObservers() {
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getSchedules().observe(getViewLifecycleOwner(), schedules -> {
            if (schedules != null && !schedules.isEmpty()) {
                Log.d(TAG, "Received " + schedules.size() + " schedules");
                scheduleAdapter.setSchedules(schedules);
                binding.rvBusSchedules.setVisibility(View.VISIBLE);
                binding.emptyStateContainer.setVisibility(View.GONE);
            } else {
                Log.d(TAG, "No schedules received");
                binding.rvBusSchedules.setVisibility(View.GONE);
                binding.emptyStateContainer.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), this::updateLoadingState);
        
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error loading schedules: " + error);
            }
        });
        
        viewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> {
            if (date != null) {
                binding.tvSelectedDate.setText(dateFormat.format(date));
                // Select the corresponding date in the RecyclerView
                dateAdapter.selectDate(date);
            }
        });
    }

    private void updateLoadingState(Boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (isLoading) {
            binding.rvBusSchedules.setVisibility(View.GONE);
            binding.emptyStateContainer.setVisibility(View.GONE);
        }
    }

    private void setupDateSelector() {
        // Generate dates for the next 7 days
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        // If selectedDate is set, use that as the starting point
        if (selectedDate != null) {
            calendar.setTime(selectedDate);
        }
        
        // Add the selected/current date as the first date
        dates.add(calendar.getTime());
        
        // Then add 6 more days
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(calendar.getTime());
        }
        
        dateAdapter.setDates(dates);
        
        // Select the first date (which is our selected/current date)
        if (!dates.isEmpty()) {
            viewModel.setSelectedDate(dates.get(0));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 
