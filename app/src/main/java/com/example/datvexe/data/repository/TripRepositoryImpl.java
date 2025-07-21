package com.example.datvexe.data.repository;

import android.util.Log;

import com.example.datvexe.data.mapper.BookingMapper;
import com.example.datvexe.data.mapper.BookingTripMapper;
import com.example.datvexe.data.mapper.UserMapper;
import com.example.datvexe.data.remote.api.callback.ScheduleCallBack;
import com.example.datvexe.data.remote.api.callback.TripCallBack;
import com.example.datvexe.data.remote.api.service.TripApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.BookingRequestDto;
import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.data.remote.dto.BookingTripDto;
import com.example.datvexe.data.remote.dto.BookingTripResponseDto;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.data.remote.dto.ScheduleResponseDto;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BookingTripResult;
import com.example.datvexe.domain.repository.TripRepository;
import com.example.datvexe.data.remote.dto.UserDto;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.datvexe.domain.model.User;
import com.google.gson.Gson;


public class TripRepositoryImpl implements TripRepository {
    private final TripApiService apiService;
    private final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();
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
                    } else if(apiResponse.getData().isEmpty()) {
                        callBack.onError("API Error: " + apiResponse.getMessage());
                    }else{
                        callBack.onError("API Error: " + "Lỗi khi lấy dữ liệu !!!");
                    }
                } else {
                    callBack.onError("Network Error: " + "Lỗi khi lấy dữ liệu !!!");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ScheduleResponseDto>>> call, Throwable t) {
                callBack.onError("Network Failure: " + t.getMessage());
            }
        });
    }
    

      @Override
      public void getUser(String userId, TripRepository.UserCallBack callback) {
        Call<ApiResponse<UserDto>> call = apiService.getUser(userId);

        call.enqueue(new Callback<ApiResponse<UserDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserDto>> call, Response<ApiResponse<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<UserDto> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        // Chuyển UserDto sang User (domain model)
                        User user = UserMapper.toDomainModel(apiResponse.getData());
                        callback.onSuccess(user);
                    } else {
                        callback.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserDto>> call, Throwable t) {
                callback.onError("Network Failure: " + t.getMessage());
            }
        });
      }


    @Override
    public void getEndBook(String userId, TripRepository.BookCallBack callback) {
        Call<ApiResponse<BookingResponseDto>> call = apiService.getendBook(userId);

        call.enqueue(new Callback<ApiResponse<BookingResponseDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<BookingResponseDto>> call, Response<ApiResponse<BookingResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<BookingResponseDto> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        // Chuyển UserDto sang User (domain model)
                        BookingTrip user = BookingMapper.toDomainModel(apiResponse.getData());
                        callback.onSuccess(user);
                    } else {
                        callback.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Network Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BookingResponseDto>> call, Throwable t) {
                callback.onError("Network Failure: " + t.getMessage());
            }
        });
    }


//    @Override
//    public void bookingTrip(String user, String busSchedule,Long totalPrice, List<String> seats, String pickupLocation, String dropoffLocation, Date departureTime, Boolean exportInvoice,String note,String paymentMethod, BookingTripCallBack callback) {
//
//        BookingRequestDto bookingRequest = new BookingRequestDto(user, busSchedule, totalPrice, seats, pickupLocation, dropoffLocation, departureTime,exportInvoice,note, paymentMethod);
//
//        Call<BookingTripResponseDto> call = apiService.createBooking(bookingRequest);
//
//        call.enqueue(new Callback<BookingTripResponseDto>() {
//            @Override
//            public void onResponse(Call<BookingTripResponseDto> call, Response<BookingTripResponseDto> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    BookingTripResponseDto bookingTripResponse = response.body();
//                    Log.d("booking", "Chiiii");
//
//                    if (bookingTripResponse.getStatus() == 200) {
//                        BookingTrip bookingTrip = BookingTripMapper.toDomainModel(bookingTripResponse.getData());
//
//                        Log.d("booking", bookingTrip.toString());
//                        BookingTripResult result = new BookingTripResult(true, bookingTripResponse.getMessage(), bookingTrip);
//                        callback.onSuccess(result);
//                    } else {
//                        callback.onError(bookingTripResponse.getMessage());
//                    }
//                } else {
//                    try (ResponseBody errorBody = response.errorBody()) {
//                        if (errorBody == null) throw new RuntimeException("Error body is null");
//                        String errorBodyString = errorBody.string();
//                        BookingTripResponseDto errorResponse = new Gson().fromJson(
//                                errorBodyString, BookingTripResponseDto.class);
//                        callback.onError(errorResponse.getMessage() != null ? errorResponse.getMessage() : "Tạo đơn");
//                    } catch (IOException e) {
//                        callback.onError("Lỗi khi đọc thông báo lỗi từ máy chủ");
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<BookingTripResponseDto> call, Throwable t) {
//                callback.onError("Lỗi mạng: " + t.getMessage());
//            }
//        });
//    }




    @Override
    public void bookingTrip(String user, String busSchedule,Long totalPrice, List<String> seats, String pickupLocation, String dropoffLocation, Date departureTime, Boolean exportInvoice,String note,String paymentMethod, TripRepository.BookingTripCallBack callback) {

        BookingRequestDto bookingRequest = new BookingRequestDto(user, busSchedule, totalPrice, seats, pickupLocation, dropoffLocation, departureTime,exportInvoice,note, paymentMethod);

        Call<ApiResponse<BookingTripDto>> call = apiService.createBooking(bookingRequest);

        call.enqueue(new Callback<ApiResponse<BookingTripDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<BookingTripDto>> call, Response<ApiResponse<BookingTripDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<BookingTripDto> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200 && apiResponse.getData() != null) {
                        // Chuyển UserDto sang User (domain model)
                        BookingTrip bookingTrip = BookingTripMapper.toDomainModel(apiResponse.getData());
                        callback.onSuccess(bookingTrip);
                    } else {
                        callback.onError("API Error: " + apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Network Error: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<ApiResponse<BookingTripDto>> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }




}
