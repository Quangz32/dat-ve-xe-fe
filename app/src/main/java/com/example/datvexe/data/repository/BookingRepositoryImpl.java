package com.example.datvexe.data.repository;

import com.example.datvexe.data.mapper.BookingMapper;
import com.example.datvexe.data.remote.NetworkModule;
import com.example.datvexe.data.remote.api.BookingApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.repository.BookingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepositoryImpl implements BookingRepository {

    private final BookingApiService apiService;

    public BookingRepositoryImpl() {
        this.apiService = NetworkModule.getBookingApiService();
    }

    @Override
    public void getBookingsByUserId(String userId, BookingRepository.BookingCallback callback) {
        Call<ApiResponse<List<BookingResponseDto>>> call = apiService.getBookingsByUserId(userId);

        call.enqueue(new Callback<ApiResponse<List<BookingResponseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BookingResponseDto>>> call,
                                   Response<ApiResponse<List<BookingResponseDto>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<BookingResponseDto>> apiResponse = response.body();

                    if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        List<BookingTrip> bookings = BookingMapper.toDomainModelList(apiResponse.getData());
                        callback.onSuccess(bookings);
                    } else {
                        callback.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BookingResponseDto>>> call, Throwable t) {
                callback.onError("Network Failure: " + t.getMessage());
            }
        });
    }
} 