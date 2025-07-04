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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.R;
import com.example.datvexe.data.local.SharedPreferencesManager;
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
    private SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", new Locale("vi"));
    private TripService tripService = TripService.getInstance();
    private SharedPreferencesManager sharedPreferencesManager;

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
        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
        
        // First try to get arguments from bundle
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

            // Save search parameters to SharedPreferences
            saveBusSearchParams();
            
            // Set values to ViewModel
            if (fromStationId != null) {
                BusStation fromStation = new BusStation();
                fromStation.setId(fromStationId);
                fromStation.setMaBenXe(fromStationId);
                fromStation.setName(fromStationName);
                fromStation.setTenBenXe(fromStationName);
                viewModel.setFromStation(fromStation);
            }
            
            if (toStationId != null) {
                BusStation toStation = new BusStation();
                toStation.setId(toStationId);
                toStation.setMaBenXe(toStationId);
                toStation.setName(toStationName);
                toStation.setTenBenXe(toStationName);
                viewModel.setToStation(toStation);
            }
            
            viewModel.setPassengerCount(passengerCount);
            
            if (selectedDate != null) {
                viewModel.setSelectedDate(selectedDate);
            }
        }
        // If no arguments, try to load from shared preferences
        else {
            loadBusSearchParams();
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
        
        // Show month and year
        updateMonthYearHeader();
        
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

    private void updateMonthYearHeader() {
        if (selectedDate != null) {
            String monthYearStr = "Tháng " + new SimpleDateFormat("M", Locale.getDefault()).format(selectedDate) +
                    " năm " + new SimpleDateFormat("yyyy", Locale.getDefault()).format(selectedDate);
            binding.tvMonthYear.setText(monthYearStr);
        }
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
            if (requireActivity() instanceof MainActivity) {
                LocationTripFragment locationTripFragment = new LocationTripFragment();
                ((MainActivity) requireActivity()).navigateToFragment(locationTripFragment);
            }
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
            // Navigate to SeatSelectedFragment
            String busType = getBusTypeFromSchedule(schedule);
            
            // Save the current search parameters to SharedPreferences 
            saveBusSearchParams();
            
            // Tạo fragment và truyền dữ liệu qua Bundle
            SeatSelectedFragment fragment = SeatSelectedFragment.newInstance(schedule, busType);
            
            // Sử dụng phương thức navigateToFragment từ MainActivity
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).navigateToFragment(fragment);
            } else {
                Log.e(TAG, "Activity không phải là MainActivity");
                Toast.makeText(requireContext(), "Không thể chuyển màn hình", Toast.LENGTH_SHORT).show();
            }
        });

        dateAdapter.setOnDateSelectedListener(date -> {
            // Load schedules for selected date
            selectedDate = date;
            updateMonthYearHeader();
            viewModel.loadSchedulesForDate(date);
        });
    }

    private String getBusTypeFromSchedule(BusSchedule schedule) {
        if (schedule.getBusOperatorDetail() != null) {
            List<String> types = schedule.getBusOperatorDetail().getTypes();
            if (types != null && !types.isEmpty()) {
                return types.get(0);
            }
            
            if (schedule.getBusOperatorDetail().getTypeBusDetail() != null &&
                schedule.getBusOperatorDetail().getTypeBusDetail().getCode() != null) {
                return schedule.getBusOperatorDetail().getTypeBusDetail().getCode();
            }
        }
        
        if (schedule.getBusOperator() != null &&
            schedule.getBusOperator().getTypes() != null && 
            !schedule.getBusOperator().getTypes().isEmpty()) {
            return schedule.getBusOperator().getTypes().get(0);
        }
        
        Log.e(TAG, "Bus type not found for schedule: " + schedule);
        return "BUS34"; // Default to BUS34 if no type is specified
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
                selectedDate = date;
                updateMonthYearHeader();
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
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        
        if (selectedDate != null) {
            calendar.setTime(selectedDate);
        }
        
        dates.add(calendar.getTime());
        
        // Then add 6 more days
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(calendar.getTime());
        }
        
        dateAdapter.setDates(dates);
        
        if (!dates.isEmpty()) {
            viewModel.setSelectedDate(dates.get(0));
        }
    }

    // Lưu thông tin tìm kiếm vào SharedPreferences
    private void saveBusSearchParams() {
        if (sharedPreferencesManager != null) {
            String dateStr = selectedDate != null ? dateFormat.format(selectedDate) : null;
            String passengerCountStr = String.valueOf(passengerCount);
            sharedPreferencesManager.saveBusSearchParams(
                fromStationId, fromStationName, 
                toStationId, toStationName, 
                dateStr, passengerCountStr
            );
            Log.d(TAG, "Saved search params to SharedPreferences");
        }
    }

    // Tải thông tin tìm kiếm từ SharedPreferences
    private void loadBusSearchParams() {
        if (sharedPreferencesManager != null) {
            fromStationId = sharedPreferencesManager.getFromStationId();
            fromStationName = sharedPreferencesManager.getFromStationName();
            toStationId = sharedPreferencesManager.getToStationId();
            toStationName = sharedPreferencesManager.getToStationName();
            
            String dateStr = sharedPreferencesManager.getTravelDate();
            if (dateStr != null) {
                try {
                    selectedDate = dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    selectedDate = new Date();
                }
            } else {
                selectedDate = new Date();
            }
            
            String passengerCountStr = sharedPreferencesManager.getPassengerCount();
            if (passengerCountStr != null) {
                try {
                    passengerCount = Integer.parseInt(passengerCountStr);
                } catch (NumberFormatException e) {
                    passengerCount = 1;
                }
            }
            
            Log.d(TAG, "Loaded search params from SharedPreferences: " + 
                "fromStationId=" + fromStationId + 
                ", toStationId=" + toStationId);
                
            // Set values to ViewModel if they exist
            if (fromStationId != null) {
                BusStation fromStation = new BusStation();
                fromStation.setId(fromStationId);
                fromStation.setMaBenXe(fromStationId);
                fromStation.setName(fromStationName);
                fromStation.setTenBenXe(fromStationName);
                viewModel.setFromStation(fromStation);
            }
            
            if (toStationId != null) {
                BusStation toStation = new BusStation();
                toStation.setId(toStationId);
                toStation.setMaBenXe(toStationId);
                toStation.setName(toStationName);
                toStation.setTenBenXe(toStationName);
                viewModel.setToStation(toStation);
            }
            
            viewModel.setPassengerCount(passengerCount);
            
            if (selectedDate != null) {
                viewModel.setSelectedDate(selectedDate);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 
