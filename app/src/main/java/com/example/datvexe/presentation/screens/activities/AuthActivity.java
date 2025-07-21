package com.example.datvexe.presentation.screens.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datvexe.R;
import com.example.datvexe.databinding.ActivityAuthBinding;
import com.example.datvexe.presentation.screens.fragments.LoginFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityAuthBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(viewBinding.getRoot());

        // Hiển thị LoginFragment mặc định
        if (savedInstanceState == null) {
            showLoginFragment();
        }
    }

    public void showLoginFragment() {
        replaceFragment(new LoginFragment());
    }

    public void showRegisterFragment() {
        replaceFragment(new com.example.datvexe.presentation.screens.fragments.RegisterFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}