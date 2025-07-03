package com.example.datvexe.presentation.screens.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.R;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.databinding.FragmentLocationTripBinding;
import com.example.datvexe.domain.model.BusStation;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.screens.fragments.BusScheduleFragment;
import com.example.datvexe.presentation.viewmodel.LocationTripViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LocationTripFragment extends Fragment {
    private FragmentLocationTripBinding binding;
    private LocationTripViewModel viewModel;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private List<LocationTripDto> locationTrips = new ArrayList<>();
    
    // To store selected stations
    private BusStation selectedDepartureStation;
    private BusStation selectedDestinationStation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
        viewModel = new ViewModelProvider(this).get(LocationTripViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLocationTripBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupListeners();
        setupObservers();
        viewModel.loadLocationTrips();
    }

    private void setupViews() {
        // Set today's date as default
        binding.edtDate.setText(dateFormatter.format(calendar.getTime()));
        
        // Set default passenger count
        binding.edtPassengerCount.setText("1");
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.navigateToMain();
        });

        binding.edtDate.setOnClickListener(v -> showDatePickerBottomSheet());
        binding.edtPassengerCount.setOnClickListener(v -> showPassengerCountBottomSheet());

        binding.edtDepartureLocation.setOnClickListener(v -> 
            showLocationPicker("Chọn điểm xuất phát", true));

        binding.edtDestinationLocation.setOnClickListener(v -> 
            showLocationPicker("Chọn điểm đến", false));

        binding.btnSearch.setOnClickListener(v -> {
            if (validateInputs()) {
                navigateToBusSchedule();
            }
        });
    }

    private void navigateToBusSchedule() {
        Bundle args = new Bundle();
        
        // Pass both name and ID of stations
        args.putString("departureLocation", binding.edtDepartureLocation.getText().toString());
        args.putString("destinationLocation", binding.edtDestinationLocation.getText().toString());
        
        // Pass station IDs if available
        if (selectedDepartureStation != null) {
            args.putString("fromStationId", selectedDepartureStation.getMaBenXe());
            args.putString("fromStationName", selectedDepartureStation.getTenBenXe());
        }
        
        if (selectedDestinationStation != null) {
            args.putString("toStationId", selectedDestinationStation.getMaBenXe());
            args.putString("toStationName", selectedDestinationStation.getTenBenXe());
        }
        
        args.putString("date", binding.edtDate.getText().toString());
        args.putString("passengerCount", binding.edtPassengerCount.getText().toString());

        MainActivity activity = (MainActivity) requireActivity();
        
        BusScheduleFragment busScheduleFragment = new BusScheduleFragment();
        busScheduleFragment.setArguments(args);
        activity.navigateToFragment(busScheduleFragment);
    }

    private void showLocationPicker(String title, boolean isDeparture) {
        if (locationTrips.isEmpty()) {
            Toast.makeText(requireContext(), "Đang tải dữ liệu...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert locationTrips to Map<String, List<BusStation>>
        Map<String, List<BusStation>> locationMap = new HashMap<>();
        for (LocationTripDto location : locationTrips) {
            locationMap.put(location.getTenTinh(), location.getBenXe());
        }

        LocationBottomSheetFragment bottomSheet = new LocationBottomSheetFragment();
        bottomSheet.setLocations(locationMap);
        bottomSheet.setOnLocationSelectedListener(station -> {
            if (isDeparture) {
                selectedDepartureStation = station;
                binding.edtDepartureLocation.setText(station.getTenBenXe());
                binding.tilDepartureLocation.setError(null);
            } else {
                selectedDestinationStation = station;
                binding.edtDestinationLocation.setText(station.getTenBenXe());
                binding.tilDestinationLocation.setError(null);
            }
        });
        bottomSheet.show(getChildFragmentManager(), "locationPicker");
    }

    private void setupObservers() {
        viewModel.getLocationTrips().observe(getViewLifecycleOwner(), this::updateLocationData);
        viewModel.getError().observe(getViewLifecycleOwner(), this::showError);
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), this::updateLoadingState);
    }

    private void updateLocationData(List<LocationTripDto> locations) {
        this.locationTrips = locations;
    }

    private void showError(String errorMessage) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void updateLoadingState(Boolean isLoading) {
        binding.btnSearch.setEnabled(!isLoading);
    }

    private void showDatePickerBottomSheet() {
        DatePickerBottomSheetFragment bottomSheet = DatePickerBottomSheetFragment.newInstance();
        bottomSheet.setOnDateSelectedListener(date -> {
            binding.edtDate.setText(dateFormatter.format(date));
        });
        bottomSheet.show(getChildFragmentManager(), "datePicker");
    }

    private void showPassengerCountBottomSheet() {
        PassengerCountBottomSheetFragment bottomSheet = PassengerCountBottomSheetFragment.newInstance();
        bottomSheet.setOnPassengerCountSelectedListener(count -> {
            binding.edtPassengerCount.setText(String.valueOf(count));
            binding.tilPassengerCount.setError(null);
        });
        bottomSheet.show(getChildFragmentManager(), "passengerCount");
    }

    private boolean validateInputs() {
        boolean isValid = true;

        String departure = binding.edtDepartureLocation.getText().toString().trim();
        String destination = binding.edtDestinationLocation.getText().toString().trim();
        String passengers = binding.edtPassengerCount.getText().toString().trim();

        if (departure.isEmpty()) {
            binding.tilDepartureLocation.setError("Vui lòng chọn điểm xuất phát");
            isValid = false;
        } else {
            binding.tilDepartureLocation.setError(null);
        }

        if (destination.isEmpty()) {
            binding.tilDestinationLocation.setError("Vui lòng chọn điểm đến");
            isValid = false;
        } else {
            binding.tilDestinationLocation.setError(null);
        }

        if (passengers.isEmpty() || Integer.parseInt(passengers) < 1) {
            binding.tilPassengerCount.setError("Số lượng hành khách không hợp lệ");
            isValid = false;
        } else {
            binding.tilPassengerCount.setError(null);
        }

        return isValid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 
