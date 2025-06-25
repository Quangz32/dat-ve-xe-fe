package com.example.datvexe.presentation.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.datvexe.databinding.ActivityLoginBinding;
import com.example.datvexe.presentation.viewmodel.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding viewBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(viewBinding.getRoot());

        setupViewModel();
        setupObservers();
        setupClickListeners();
    }

    private void setupViewModel() {
        // Khởi tạo ViewModel thông qua Hilt injection
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupObservers() {
        // Quan sát trạng thái loading
        loginViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                viewBinding.progressBar.setVisibility(View.VISIBLE);
                viewBinding.btnLogin.setEnabled(false);
            } else {
                viewBinding.progressBar.setVisibility(View.GONE);
                viewBinding.btnLogin.setEnabled(true);
            }
        });

        // Quan sát kết quả đăng nhập
        loginViewModel.loginResult.observe(this, loginResult -> {
            if (loginResult != null && loginResult.isSuccess()) {
                Toast.makeText(this, loginResult.getMessage(), Toast.LENGTH_SHORT).show();
                // Chuyển sang MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Quan sát lỗi
        loginViewModel.errorMessage.observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                loginViewModel.clearErrorMessage();
            }
        });
    }

    private void setupClickListeners() {
        viewBinding.btnLogin.setOnClickListener(v -> {
            String username = viewBinding.etUsername.getText().toString().trim();
            String password = viewBinding.etPassword.getText().toString().trim();

            Log.d("LoginActivity", "Username: " + username + ", Password: " + password);
            loginViewModel.login(username, password);
        });
    }
}