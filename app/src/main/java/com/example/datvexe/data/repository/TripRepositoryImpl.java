package com.example.datvexe.data.repository;

import com.example.datvexe.data.remote.api.callback.ScheduleCallBack;
import com.example.datvexe.data.remote.api.callback.TripCallBack;
import com.example.datvexe.data.remote.api.service.TripApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;
import com.example.datvexe.domain.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripRepositoryImpl implements TripRepository {
    private final TripApiService apiService;

    @Inject()
    public TripRepositoryImpl(TripApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public void getLocationTrip(TripCallBack callBack) {
        Call<ApiResponse<List<LocationTripDto>>> call = apiService.getLocationTrip();
        call.enqueue(new Callback<ApiResponse<List<LocationTripDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<LocationTripDto>>> call, Response<ApiResponse<List<LocationTripDto>>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<LocationTripDto>> apiResponse = response.body();
                    if(apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        callBack.onSuccess(apiResponse.getData());
                    } else {
                        callBack.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callBack.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<LocationTripDto>>> call, Throwable t) {
                callBack.onError("Network Failure: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadDataSchedule(ScheduleReq req, ScheduleCallBack callBack) {
        Call<ApiResponse<List<ScheduleResponseDto>>> call = apiService.loadDataSchedule(req);
        call.enqueue(new Callback<ApiResponse<List<ScheduleResponseDto>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ScheduleResponseDto>>> call, Response<ApiResponse<List<ScheduleResponseDto>>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<ScheduleResponseDto>> apiResponse = response.body();
                    if(apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        callBack.onSuccess(apiResponse.getData());
                    } else {
                        callBack.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callBack.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ScheduleResponseDto>>> call, Throwable t) {
                callBack.onError("Network Failure: " + t.getMessage());
            }
        });
    }
}
