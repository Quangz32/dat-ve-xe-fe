package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.LoginResult;

public interface AuthRepository {
    void login(String username, String password, AuthCallback callback);

    interface AuthCallback {
        void onSuccess(LoginResult result);

        void onError(String error);
    }
} 