 package com.example.datvexe.presentation.screens.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.datvexe.R;
import com.example.datvexe.presentation.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordFragment extends Fragment {
    private ForgotPasswordViewModel viewModel;
    private EditText edtUsernameOrEmail;
    private Button btnSend;
    private Button btnBackToLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        edtUsernameOrEmail = view.findViewById(R.id.edtUsernameOrEmail);
        btnSend = view.findViewById(R.id.btnSend);
        btnBackToLogin = view.findViewById(R.id.btnBackToLogin);

        btnSend.setOnClickListener(v -> {
            String input = edtUsernameOrEmail.getText().toString().trim();
            if (TextUtils.isEmpty(input)) {
                edtUsernameOrEmail.setError("Vui lòng nhập tên đăng nhập hoặc email");
                return;
            }
            viewModel.forgotPassword(input);
        });

        btnBackToLogin.setOnClickListener(v -> {
            if (getActivity() instanceof com.example.datvexe.presentation.screens.activities.AuthActivity) {
                ((com.example.datvexe.presentation.screens.activities.AuthActivity) getActivity()).showLoginFragment();
            }
        });

        viewModel.getForgotPasswordResult().observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                Toast.makeText(getContext(), "Mật khẩu mới của bạn là: 123456", Toast.LENGTH_LONG).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
