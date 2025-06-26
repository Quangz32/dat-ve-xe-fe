package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.databinding.FragmentRegisterBinding;
import com.example.datvexe.presentation.screens.activities.AuthActivity;
import com.example.datvexe.presentation.viewmodel.RegisterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding viewBinding;
    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();
        setupObservers();
        setupClickListeners();
    }

    private void setupViewModel() {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    private void setupObservers() {
        // Quan sát trạng thái loading
        registerViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                viewBinding.progressBar.setVisibility(View.VISIBLE);
                viewBinding.btnRegister.setEnabled(false);
            } else {
                viewBinding.progressBar.setVisibility(View.GONE);
                viewBinding.btnRegister.setEnabled(true);
            }
        });

        // Quan sát kết quả đăng ký
        registerViewModel.registerResult.observe(getViewLifecycleOwner(), registerResult -> {
            if (registerResult != null && registerResult.isSuccess()) {
                Toast.makeText(getContext(), registerResult.getMessage(), Toast.LENGTH_SHORT).show();
                // Chuyển về LoginFragment sau khi đăng ký thành công
                if (getActivity() instanceof AuthActivity) {
                    ((AuthActivity) getActivity()).showLoginFragment();
                }
            }
        });

        // Quan sát lỗi
        registerViewModel.errorMessage.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                registerViewModel.clearErrorMessage();
            }
        });
    }

    private void setupClickListeners() {
        viewBinding.btnRegister.setOnClickListener(v -> {
            String username = viewBinding.etUsername.getText().toString().trim();
            String password = viewBinding.etPassword.getText().toString().trim();
            String confirmPassword = viewBinding.etConfirmPassword.getText().toString().trim();
            String email = viewBinding.etEmail.getText().toString().trim();
            String fullname = viewBinding.etFullname.getText().toString().trim();
            String phone = viewBinding.etPhone.getText().toString().trim();

            // Kiểm tra mật khẩu xác nhận
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra checkbox điều khoản
            if (!viewBinding.cbTerms.isChecked()) {
                Toast.makeText(getContext(), "Vui lòng đồng ý với điều khoản dịch vụ", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("RegisterFragment",
                    "Username: " + username + ", Email: " + email + ", Fullname: " + fullname + ", Phone: " + phone);
            registerViewModel.register(username, password, email, fullname, phone);
        });

        // Click để chuyển về LoginFragment
        viewBinding.tvLogin.setOnClickListener(v -> {
            if (getActivity() instanceof AuthActivity) {
                ((AuthActivity) getActivity()).showLoginFragment();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}