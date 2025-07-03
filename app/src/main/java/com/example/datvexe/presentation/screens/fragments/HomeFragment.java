package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.datvexe.databinding.FragmentHomeBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding viewBinding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false);
        setupClickListeners();
        return viewBinding.getRoot();
    }

    private void setupClickListeners() {
        viewBinding.btnSearchTrip.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) requireActivity();
            activity.navigateToFragment(new LocationTripFragment());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}
