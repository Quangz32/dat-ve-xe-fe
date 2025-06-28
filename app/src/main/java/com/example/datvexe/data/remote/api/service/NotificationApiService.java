package com.example.datvexe.data.remote.api.service;

import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.NotificationResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationApiService {

    @GET("notifice/{userId}/get")
    Call<ApiResponse<List<NotificationResponseDto>>> getNotification(@Path("userId") String userId);

}
