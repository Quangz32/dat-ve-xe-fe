package com.example.datvexe.presentation.screens.fragments;

import android.content.Intent;
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

import com.example.datvexe.databinding.FragmentLoginBinding;
import com.example.datvexe.presentation.screens.activities.AuthActivity;
import com.example.datvexe.presentation.screens.activities.MainActivity;
import com.example.datvexe.presentation.viewmodel.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private FragmentLoginBinding viewBinding;
    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false);
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
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupObservers() {
        // Quan sát trạng thái loading
        loginViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                viewBinding.progressBar.setVisibility(View.VISIBLE);
                viewBinding.btnLogin.setEnabled(false);
            } else {
                viewBinding.progressBar.setVisibility(View.GONE);
                viewBinding.btnLogin.setEnabled(true);
            }
        });

        // Quan sát kết quả đăng nhập
        loginViewModel.loginResult.observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult != null && loginResult.isSuccess()) {
                Toast.makeText(getContext(), loginResult.getMessage(), Toast.LENGTH_SHORT).show();
                // Chuyển sang MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        // Quan sát lỗi
        loginViewModel.errorMessage.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                loginViewModel.clearErrorMessage();
            }
        });
    }

    private void setupClickListeners() {
        viewBinding.btnLogin.setOnClickListener(v -> {
            String username = viewBinding.etUsername.getText().toString().trim();
            String password = viewBinding.etPassword.getText().toString().trim();

            Log.d("LoginFragment", "Username: " + username + ", Password: " + password);
            loginViewModel.login(username, password);
        });

        // Click để chuyển sang RegisterFragment
        viewBinding.tvRegister.setOnClickListener(v -> {
            if (getActivity() instanceof AuthActivity) {
                ((AuthActivity) getActivity()).showRegisterFragment();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}