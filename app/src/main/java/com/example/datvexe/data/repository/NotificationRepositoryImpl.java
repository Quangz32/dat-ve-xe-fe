package com.example.datvexe.data.repository;

import com.example.datvexe.data.mapper.NotificationMapper;
import com.example.datvexe.data.remote.api.service.NotificationApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.NotificationResponseDto;
import com.example.datvexe.domain.model.Notification;
import com.example.datvexe.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationApiService apiService;

    @Inject
    public NotificationRepositoryImpl(NotificationApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public void getNotification(String userId, Callback callback) {
        Call<ApiResponse<List<NotificationResponseDto>>> call = apiService.getNotification(userId);

        call.enqueue(new retrofit2.Callback<ApiResponse<List<NotificationResponseDto>>>() {

            @Override
            public void onResponse(Call<ApiResponse<List<NotificationResponseDto>>> call,
                                   Response<ApiResponse<List<NotificationResponseDto>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<NotificationResponseDto>> apiResponse = response.body();

                    if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        List<Notification> notifications = NotificationMapper.toDomainModelList(apiResponse.getData());
                        callback.onSuccess(notifications);
                    } else {
                        callback.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<NotificationResponseDto>>> call, Throwable t) {
                callback.onError("Network Failure: " + t.getMessage());
            }
        });
    }
}
