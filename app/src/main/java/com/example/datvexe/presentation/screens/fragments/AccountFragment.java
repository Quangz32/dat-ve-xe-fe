package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.databinding.FragmentAccountBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.AccountViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends Fragment {
    private final int TAB_INDEX = 3;
    private FragmentAccountBinding binding;
    private MainActivity mainActivity;

    private AccountViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        mainActivity = (MainActivity) requireActivity();

        mainActivity.setTabNavigateBackCallback(TAB_INDEX, () -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Tài khoản");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, false);
            mainActivity.navigateToMain();
        });
        binding.tvChatWithAdmin.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Chat với quản trị viên");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new ChatWithAdminFragment());
        });
        binding.tvLogout.setOnClickListener(v -> {
            viewModel.logout();
            mainActivity.goToAuthActivity();
        });
        // Thêm sự kiện mở SettingsFragment
        binding.tvSettings.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Cài đặt");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new SettingsFragment());
        });
        binding.tvBusOperatorInfo.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Thông tin nhà xe");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new BusOperatorInfoFragment());
        });
        binding.tvNews.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Tin tức");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new NewsFragment());
        });
        binding.tvLoyalty.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Điểm Loyalty");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(LoyaltyFragment.newInstance());
        });
        return binding.getRoot();
    }
}