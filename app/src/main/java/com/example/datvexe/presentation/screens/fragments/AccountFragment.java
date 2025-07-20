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

        // Bind dữ liệu từ ViewModel
        viewModel.getUserName().observe(getViewLifecycleOwner(), name -> binding.tvUserName.setText(name));
        viewModel.getPhoneNumber().observe(getViewLifecycleOwner(), phone -> binding.tvPhoneNumber.setText(phone));
        viewModel.getSilver().observe(getViewLifecycleOwner(), silver -> binding.tvSilver.setText(String.valueOf(silver)));
        viewModel.getPromotion().observe(getViewLifecycleOwner(), promo -> binding.tvPromotion.setText(String.valueOf(promo)));
        // viewModel.getFriends().observe(getViewLifecycleOwner(), friends -> binding.tvFriends.setText(String.valueOf(friends)));
        // viewModel.getNews().observe(getViewLifecycleOwner(), news -> binding.tvNews.setText(String.valueOf(news)));

        binding.btnChatWithAdmin.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Chat với quản trị viên");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new ChatWithAdminFragment());
        });
        binding.btnLogout.setOnClickListener(v -> {
            viewModel.logout();
            mainActivity.goToAuthActivity();
        });
        // Các nút khác có thể thêm xử lý tương tự
        return binding.getRoot();
    }
}