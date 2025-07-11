package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datvexe.R;
import com.example.datvexe.databinding.FragmentBusOperatorInfoBinding;

public class BusOperatorInfoFragment extends Fragment {
    private FragmentBusOperatorInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusOperatorInfoBinding.inflate(inflater, container, false);
        // Gán dữ liệu hardcode
        binding.ivLogo.setImageResource(R.drawable.logo_sao_viet); // Đổi tên logo nếu cần
        binding.tvName.setText("Nhà xe Sao Việt");
        binding.tvHotline.setText("Hotline: 1900 1234");
        binding.tvAddress.setText("Địa chỉ: 789 Giải Phóng, Hà Nội");
        binding.tvDescription.setText("Nhà xe Sao Việt chuyên tuyến Hà Nội - Lào Cai - Sapa, phục vụ 24/7 với chất lượng cao.");
        return binding.getRoot();
    }
} 