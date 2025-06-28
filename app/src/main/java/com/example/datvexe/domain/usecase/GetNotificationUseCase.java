package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

public class GetNotificationUseCase {
    public final NotificationRepository notificationRepository;

    @Inject
    public GetNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void execute(String userId, UseCaseCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onError("User ID không được để trống");
            return;
        }

        notificationRepository.getNotification(userId, new NotificationRepository.Callback() {
            @Override
            public void onSuccess(List<Notification> notifications) {
                callback.onSuccess(notifications);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface UseCaseCallback {
        void onSuccess(List<Notification> notifications);

        void onError(String error);
    }
}
