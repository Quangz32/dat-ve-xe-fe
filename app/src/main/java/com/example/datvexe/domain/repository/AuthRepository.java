package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.LoginResult;
import com.example.datvexe.domain.model.RegisterResult;

public interface AuthRepository {
    void login(String username, String password, LoginCallback callback);

    void register(String username, String password, String email, String fullname, String phone,
            RegisterCallback callback);

    interface LoginCallback {
        void onSuccess(LoginResult result);

        void onError(String error);
    }

    interface RegisterCallback {
        void onSuccess(RegisterResult result);

        void onError(String error);
    }
} 