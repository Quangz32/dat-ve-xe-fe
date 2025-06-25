package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.model.LoginResult;
import com.example.datvexe.domain.repository.AuthRepository;

import javax.inject.Inject;

public class LoginUseCase {
    private AuthRepository authRepository;

    @Inject
    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String username, String password, LoginCallback callback) {
        if (username == null || username.trim().isEmpty()) {
            callback.onError("Tên đăng nhập không được để trống");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            callback.onError("Mật khẩu không được để trống");
            return;
        }

        authRepository.login(username, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(LoginResult result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(LoginResult result);

        void onError(String error);
    }
} 