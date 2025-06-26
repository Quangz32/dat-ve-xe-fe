package com.example.datvexe.data.remote.api.service;

import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookingApiService {

    @GET("booking/user/{userId}")
    Call<ApiResponse<List<BookingResponseDto>>> getBookingsByUserId(@Path("userId") String userId);

    @GET("booking/history/user/{userId}")
    Call<ApiResponse<List<BookingResponseDto>>> getBookingsHistoryByUserId(@Path("userId") String userId);

} 