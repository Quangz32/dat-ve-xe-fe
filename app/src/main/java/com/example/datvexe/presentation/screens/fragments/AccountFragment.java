package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.datvexe.databinding.FragmentAccountBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;

public class AccountFragment extends Fragment {
    private final int TAB_INDEX = 3;
    private FragmentAccountBinding binding;
    private MainActivity mainActivity;

    public AccountFragment() {
        // Required empty public constructor
    }


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        return binding.getRoot();
    }
}