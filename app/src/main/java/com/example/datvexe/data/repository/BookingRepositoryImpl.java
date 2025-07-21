package com.example.datvexe.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.example.datvexe.data.mapper.BookingMapper;
import com.example.datvexe.data.remote.api.service.BookingApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.data.remote.dto.UserDto;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.repository.BookingRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepositoryImpl implements BookingRepository {

    private final BookingApiService apiService;
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Inject
    public BookingRepositoryImpl(BookingApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getBookingsByUserId(String userId, BookingRepository.BookingCallback callback) {
        Call<ApiResponse<List<BookingResponseDto>>> call = apiService.getBookingsByUserId(userId);

        call.enqueue(new Callback<ApiResponse<List<BookingResponseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BookingResponseDto>>> call,
                                   Response<ApiResponse<List<BookingResponseDto>>> response) {
                // Process response on background thread
                backgroundExecutor.execute(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<List<BookingResponseDto>> apiResponse = response.body();

                        if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                            List<BookingTrip> bookings = BookingMapper.toDomainModelList(apiResponse.getData());
                            // Post result to main thread
                            mainHandler.post(() -> callback.onSuccess(bookings));
                        } else {
                            // Post error to main thread
                            mainHandler.post(() -> callback.onError("API Error: " + apiResponse.getMessage()));
                        }
                    } else {
                        // Post error to main thread
                        mainHandler.post(() -> callback.onError("Network Error: " + response.message()));
                    }
                });
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BookingResponseDto>>> call, Throwable t) {
                // Post error to main thread
                mainHandler.post(() -> callback.onError("Network Failure: " + t.getMessage()));
            }
        });
    }

    @Override
    public void getBookingsHistoryByUserId(String userId, BookingRepository.BookingCallback callback) {
        Call<ApiResponse<List<BookingResponseDto>>> call = apiService.getBookingsHistoryByUserId(userId);

        call.enqueue(new Callback<ApiResponse<List<BookingResponseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BookingResponseDto>>> call,
                                   Response<ApiResponse<List<BookingResponseDto>>> response) {
                // Process response on background thread
                backgroundExecutor.execute(() -> {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse<List<BookingResponseDto>> apiResponse = response.body();

                        if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                            List<BookingTrip> bookings = BookingMapper.toDomainModelList(apiResponse.getData());
                            // Post result to main thread
                            mainHandler.post(() -> callback.onSuccess(bookings));
                        } else {
                            // Post error to main thread
                            mainHandler.post(() -> callback.onError("API Error: " + apiResponse.getMessage()));
                        }
                    } else {
                        // Post error to main thread
                        mainHandler.post(() -> callback.onError("Network Error: " + response.message()));
                    }
                });
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BookingResponseDto>>> call, Throwable t) {
                // Post error to main thread
                mainHandler.post(() -> callback.onError("Network Failure: " + t.getMessage()));
            }
        });
    }

    public void shutdown() {
        if (!backgroundExecutor.isShutdown()) {
            backgroundExecutor.shutdown();
        }
    }

} 