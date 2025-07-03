package com.example.datvexe.data.remote.api.service;


import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TripApiService {

    @GET("trip/location/all")
    Call<ApiResponse<List<LocationTripDto>>> getLocationTrip();

    @POST("trip/schedule")
    Call<ApiResponse<List<ScheduleResponseDto>>> loadDataSchedule(@Body()ScheduleReq scheduleReq);
}
