package com.example.datvexe.data.repository;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.data.remote.api.service.AuthApiService;
import com.example.datvexe.data.remote.dto.LoginRequestDto;
import com.example.datvexe.data.remote.dto.LoginResponseDto;
import com.example.datvexe.domain.model.LoginResult;
import com.example.datvexe.domain.repository.AuthRepository;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private AuthApiService authApiService;
    private SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public AuthRepositoryImpl(AuthApiService authApiService, SharedPreferencesManager sharedPreferencesManager) {
        this.authApiService = authApiService;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void login(String username, String password, AuthCallback callback) {
        LoginRequestDto loginRequest = new LoginRequestDto(username, password);

        Call<LoginResponseDto> call = authApiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponseDto>() {
            @Override
            public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseDto loginResponse = response.body();

                    if (loginResponse.getStatus() == 200) {
                        // Lưu token vào SharedPreferences
                        sharedPreferencesManager.saveToken(loginResponse.getData());

                        LoginResult result = new LoginResult(true,
                                loginResponse.getMessage(), loginResponse.getData());
                        callback.onSuccess(result);
                    } else {
                        LoginResult result = new LoginResult(false,
                                loginResponse.getMessage(), null);
                        callback.onError(loginResponse.getMessage());
                    }
                } else {
                    try (ResponseBody errorBody = response.errorBody()) {
                        if (errorBody == null) throw new RuntimeException("Error body is null");
                        String errorBodyString = errorBody.string();
                        LoginResponseDto errorResponse = new Gson().fromJson(
                                errorBodyString, LoginResponseDto.class);
                        callback.onError(errorResponse.getMessage());
                    } catch (IOException e) {
                        callback.onError("Lỗi khi đọc thông báo lỗi từ máy chủ");
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }
}