package com.example.datvexe.presentation.viewmodel;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.domain.usecase.notification.GetNotificationUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private final GetNotificationUseCase getNotificationUseCase;

    private final MutableLiveData<List<Notification>> _notifications = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();

    public final LiveData<List<Notification>> notifications = _notifications;
    public final LiveData<Boolean> isLoading = _isLoading;
    public final LiveData<String> error = _error;

    // Background executor for heavy operations
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Inject
    public NotificationViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
    }

    public void loadNotifications(String userId) {
        _isLoading.setValue(true);
        _error.setValue(null);

        // Run on background thread to avoid ANR
        backgroundExecutor.execute(() -> {
            getNotificationUseCase.execute(userId, new GetNotificationUseCase.UseCaseCallback() {
                @Override
                public void onSuccess(List<Notification> notifications) {
                    Log.d("notifications", notifications.toString());
                    // Post result to main thread
                    mainHandler.post(() -> {
                        _isLoading.setValue(false);
                        _notifications.setValue(notifications);
                    });
                }

                @Override
                public void onError(String error) {
                    // Post error to main thread
                    mainHandler.post(() -> {
                        _isLoading.setValue(false);
                        _error.setValue(error);
                    });
                }
            });
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Shutdown executor to prevent memory leaks
        if (!backgroundExecutor.isShutdown()) {
            backgroundExecutor.shutdown();
        }
    }
}
