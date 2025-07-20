package com.example.datvexe.data.repository;

import com.example.datvexe.data.mapper.BookingMapper;
import com.example.datvexe.data.mapper.DiscountMapper;
import com.example.datvexe.data.remote.api.service.BookingApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.data.remote.dto.DiscountDto;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.Discount;
import com.example.datvexe.domain.repository.BookingRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepositoryImpl implements BookingRepository {

    private final BookingApiService apiService;

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

    @Override
    public void getBookingsHistoryByUserId(String userId, BookingRepository.BookingCallback callback) {
        Call<ApiResponse<List<BookingResponseDto>>> call = apiService.getBookingsHistoryByUserId(userId);

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

    @Override
    public void getDiscountsByUser(DiscountCallback callback) {
        apiService.getDiscountsByUser().enqueue(new Callback<ApiResponse<List<DiscountDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DiscountDto>>> call, Response<ApiResponse<List<DiscountDto>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                    List<Discount> discounts = response.body().getData().stream()
                            .map(DiscountMapper::toDomainModel)
                            .collect(java.util.stream.Collectors.toList());
                    callback.onSuccess(discounts);
                } else {
                    callback.onError("Lỗi tải khuyến mãi");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DiscountDto>>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
} 