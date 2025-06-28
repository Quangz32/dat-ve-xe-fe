package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.Notification;

import java.util.List;

public interface NotificationRepository {
    void getNotification(String userId, Callback callback);

    interface Callback {
        void onSuccess(List<Notification> notifications);

        void onError(String error);
    }
}
