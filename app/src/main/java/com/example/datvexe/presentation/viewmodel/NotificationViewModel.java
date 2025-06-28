package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.domain.usecase.GetNotificationUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    public final GetNotificationUseCase getNotificationUseCase;

    private final MutableLiveData<List<Notification>> _notifications = new MutableLiveData<>();
    public final LiveData<List<Notification>> notifications = _notifications;

    //Later
    //    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    //    public final LiveData<Boolean> isLoading = _isLoading;

    @Inject
    public NotificationViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
    }

    public void loadNotifications(String userId) {
        getNotificationUseCase.execute(userId, new GetNotificationUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(List<Notification> notifications) {
                _notifications.setValue(notifications);
            }

            @Override
            public void onError(String error) {

                // Xử lý lỗi
            }
        });
    }


}
