package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datvexe.databinding.FragmentAccountBinding;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.AccountViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import androidx.annotation.Nullable;

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
        binding.btnFaq.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Câu hỏi thường gặp");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new FaqFragment());
        });
        binding.btnLoyalty.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Điểm Loyalty");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(LoyaltyFragment.newInstance());
        });
        // Thêm sự kiện mở SettingsFragment
        binding.btnSettings.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Cài đặt");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new SettingsFragment());
        });
        binding.btnBusOperatorInfo.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Thông tin nhà xe");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new BusOperatorInfoFragment());
        });
        binding.btnNews.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Tin tức");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new NewsFragment());
        });
        binding.btnPromotion.setOnClickListener(v -> {
            mainActivity.setActionBarTitle(TAB_INDEX, "Khuyến mãi");
            mainActivity.setShowOrHideNavigateBack(TAB_INDEX, true);
            mainActivity.navigateToFragment(new NotificationFragment());
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (requireActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().show();
                activity.getSupportActionBar().setTitle("Tài khoản");
            }
            // Đảm bảo Toolbar của Activity cũng hiện lên
            View toolbar = activity.findViewById(com.example.datvexe.R.id.toolbar);
            if (toolbar != null) toolbar.setVisibility(View.VISIBLE);
        }
    }
}