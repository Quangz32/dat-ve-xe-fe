package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datvexe.databinding.BottomSheetPassengerCountBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PassengerCountBottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetPassengerCountBinding binding;
    private OnPassengerCountSelectedListener listener;
    private int currentCount = 1;

    public static PassengerCountBottomSheetFragment newInstance() {
        return new PassengerCountBottomSheetFragment();
    }

    public void setOnPassengerCountSelectedListener(OnPassengerCountSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = BottomSheetPassengerCountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        updateCountText();

        binding.btnClose.setOnClickListener(v -> dismiss());

        binding.btnDecrease.setOnClickListener(v -> {
            if (currentCount > 1) {
                currentCount--;
                updateCountText();
            }
        });

        binding.btnIncrease.setOnClickListener(v -> {
            if (currentCount < 10) {
                currentCount++;
                updateCountText();
            }
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPassengerCountSelected(currentCount);
            }
            dismiss();
        });
    }

    private void updateCountText() {
        binding.tvPassengerCount.setText(String.valueOf(currentCount));
        binding.btnDecrease.setEnabled(currentCount > 1);
        binding.btnIncrease.setEnabled(currentCount < 10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface OnPassengerCountSelectedListener {
        void onPassengerCountSelected(int count);
    }
} 
