package com.example.datvexe.data.remote.api.service;


import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingRequestDto;
import com.example.datvexe.data.remote.dto.BookingTripDto;
import com.example.datvexe.data.remote.dto.BookingTripResponseDto;
import com.example.datvexe.data.remote.dto.CancelBookingDto;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;
import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.data.remote.dto.DiscountResponseDto;
import com.example.datvexe.data.remote.dto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TripApiService {

    @GET("trip/location/all")
    Call<ApiResponse<List<LocationTripDto>>> getLocationTrip();

    @POST("trip/schedule")
    Call<ApiResponse<List<ScheduleResponseDto>>> loadDataSchedule(@Body()ScheduleReq scheduleReq);

//    @POST("trip/create")
//    Call<BookingTripResponseDto> createBooking(@Body BookingRequestDto bookingRequestDto);
@POST("trip/create")
  Call<ApiResponse<BookingTripDto>> createBooking(@Body() BookingRequestDto bookingRequestDto);


    @POST("trip/cancel")
    Call<ApiResponse<BookingResponseDto>> cancelBooking(@Body() CancelBookingDto bookingResponseDto);

    @POST("payment/create-url-vnpay")
    Call<ApiResponse<BookingResponseDto>> getUrlVnPayQrCode(@Body BookingResponseDto bookingResponseDto);

    @PUT("payment/change-status")
     Call<ApiResponse<BookingResponseDto>> updateStatusPayment(@Body BookingResponseDto bookingResponseDto);


    @GET("auth/profile/{userId}")
     Call<ApiResponse<UserDto>> getUser(@Path("userId") String userId);

    @GET("booking/book/{id}")
    Call<ApiResponse<BookingResponseDto>> getendBook(@Path("id") String id);



}
