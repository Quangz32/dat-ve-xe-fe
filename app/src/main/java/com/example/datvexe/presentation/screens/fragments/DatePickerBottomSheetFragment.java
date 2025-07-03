package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datvexe.databinding.BottomSheetDatePickerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerBottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetDatePickerBinding binding;
    private OnDateSelectedListener listener;
    private Calendar calendar;

    public static DatePickerBottomSheetFragment newInstance() {
        return new DatePickerBottomSheetFragment();
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = BottomSheetDatePickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        calendar = Calendar.getInstance();
        binding.datePicker.setMinDate(System.currentTimeMillis() - 1000);

        binding.btnClose.setOnClickListener(v -> dismiss());

        binding.btnConfirm.setOnClickListener(v -> {
            if (listener != null) {
                calendar.set(Calendar.YEAR, binding.datePicker.getYear());
                calendar.set(Calendar.MONTH, binding.datePicker.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, binding.datePicker.getDayOfMonth());
                listener.onDateSelected(calendar.getTime());
            }
            dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);
    }
} 
