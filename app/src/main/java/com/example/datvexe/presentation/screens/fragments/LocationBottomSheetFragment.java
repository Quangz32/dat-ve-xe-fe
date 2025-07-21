package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.datvexe.databinding.BottomSheetLocationBinding;
import com.example.datvexe.domain.model.BusStation;
import com.example.datvexe.presentation.adapter.LocationAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationBottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetLocationBinding binding;
    private LocationAdapter adapter;
    private OnLocationSelectedListener listener;
    private List<Map.Entry<String, List<String>>> groupedLocations;
    private Map<String, BusStation> stationMap = new HashMap<>();
    private Map<String, List<BusStation>> pendingLocations;

    public interface OnLocationSelectedListener {
        void onLocationSelected(BusStation station);
    }

    public void setOnLocationSelectedListener(OnLocationSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupSearchView();
        setupViews();

        // If locations were set before view creation, apply them now
        if (pendingLocations != null) {
            setLocationsInternal(pendingLocations);
            pendingLocations = null;
        }
    }

    private void setupViews() {
        binding.btnClose.setOnClickListener(v -> dismiss());
    }

    private void setupRecyclerView() {
        adapter = new LocationAdapter();
        binding.rvLocations.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLocations.setAdapter(adapter);

        adapter.setOnStationClickListener(stationName -> {
            if (listener != null && stationMap.containsKey(stationName)) {
                listener.onLocationSelected(stationMap.get(stationName));
                dismiss();
            }
        });
    }

    private void setupSearchView() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterLocations(s.toString());
            }
        });
    }

    private void filterLocations(String query) {
        if (groupedLocations == null) return;

        if (query.isEmpty()) {
            adapter.setLocations(groupedLocations);
            return;
        }

        String lowercaseQuery = query.toLowerCase();
        List<Map.Entry<String, List<String>>> filtered = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : groupedLocations) {
            String province = entry.getKey();
            List<String> filteredStations = new ArrayList<>();
            for (String station : entry.getValue()) {
                if (station.toLowerCase().contains(lowercaseQuery)) {
                    filteredStations.add(station);
                }
            }
            if (!filteredStations.isEmpty() || province.toLowerCase().contains(lowercaseQuery)) {
                filtered.add(new java.util.AbstractMap.SimpleEntry<>(province, filteredStations.isEmpty() ? entry.getValue() : filteredStations));
            }
        }
        adapter.setLocations(filtered);
    }

    public void setLocations(Map<String, List<BusStation>> locations) {
        if (binding != null && adapter != null) {
            setLocationsInternal(locations);
        } else {
            // Store locations to be applied when view is created
            pendingLocations = locations;
        }
    }

    private void setLocationsInternal(Map<String, List<BusStation>> locations) {
        // Chuyển đổi sang group: tỉnh/thành -> danh sách tên bến xe, và lưu map tên -> BusStation
        stationMap.clear();
        List<Map.Entry<String, List<String>>> groupList = new ArrayList<>();
        for (Map.Entry<String, List<BusStation>> entry : locations.entrySet()) {
            List<String> stationNames = new ArrayList<>();
            for (BusStation station : entry.getValue()) {
                String stationName = station.getTenBenXe();
                stationNames.add(stationName);
                stationMap.put(stationName, station);
            }
            groupList.add(new java.util.AbstractMap.SimpleEntry<>(entry.getKey(), stationNames));
        }
        groupedLocations = groupList;
        adapter.setLocations(groupedLocations);
    }
} 
