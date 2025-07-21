package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.User;

public interface UserRepository {
    void getUserProfile(String userId, UserProfileCallback callback);

    interface UserProfileCallback {
        void onSuccess(User user);
        void onError(String error);
    }
} 