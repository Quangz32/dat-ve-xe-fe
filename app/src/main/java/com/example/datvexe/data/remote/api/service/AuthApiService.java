package com.example.datvexe.data.remote.api.service;

import com.example.datvexe.data.remote.dto.LoginRequestDto;
import com.example.datvexe.data.remote.dto.LoginResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<LoginResponseDto> login(@Body LoginRequestDto loginRequest);
} 