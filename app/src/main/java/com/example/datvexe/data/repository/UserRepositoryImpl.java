package com.example.datvexe.data.repository;

import com.example.datvexe.data.mapper.UserMapper;
import com.example.datvexe.data.remote.api.service.UserApiService;
import com.example.datvexe.data.remote.dto.ApiResponse;
import com.example.datvexe.data.remote.dto.UserDto;
import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.UserRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private final UserApiService apiService;

    @Inject
    public UserRepositoryImpl(UserApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getUserProfile(String userId, UserProfileCallback callback) {
        apiService.getProfile(userId).enqueue(new Callback<ApiResponse<UserDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserDto>> call, Response<ApiResponse<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()==200) {
                    UserDto dto = response.body().getData();
                    callback.onSuccess(UserMapper.toDomainModel(dto));
                } else {
                    callback.onError("Lỗi lấy profile");
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
} 