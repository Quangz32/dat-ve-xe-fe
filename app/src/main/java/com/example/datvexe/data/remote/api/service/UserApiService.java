package com.example.datvexe.data.remote.api.service;

import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("auth/profile/{id}")
    Call<ApiResponse<UserDto>> getProfile(@Path("id") String userId);
} 