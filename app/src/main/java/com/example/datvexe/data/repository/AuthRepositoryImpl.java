package com.example.datvexe.data.repository;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.data.mapper.UserMapper;
import com.example.datvexe.data.remote.api.service.AuthApiService;
import com.example.datvexe.data.remote.dto.LoginRequestDto;
import com.example.datvexe.data.remote.dto.LoginResponseDto;
import com.example.datvexe.data.remote.dto.RegisterRequestDto;
import com.example.datvexe.data.remote.dto.RegisterResponseDto;
import com.example.datvexe.domain.model.LoginResult;
import com.example.datvexe.domain.model.RegisterResult;
import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.AuthRepository;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthApiService authApiService;
    private final SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public AuthRepositoryImpl(AuthApiService authApiService, SharedPreferencesManager sharedPreferencesManager) {
        this.authApiService = authApiService;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void login(String username, String password, LoginCallback callback) {
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

    @Override
    public void register(String username, String password, String email, String fullname, String phone, RegisterCallback callback) {
        RegisterRequestDto registerRequest = new RegisterRequestDto(username, password, email, fullname, phone);

        Call<RegisterResponseDto> call = authApiService.register(registerRequest);
        call.enqueue(new Callback<RegisterResponseDto>() {
            @Override
            public void onResponse(Call<RegisterResponseDto> call, Response<RegisterResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponseDto registerResponse = response.body();

                    if (registerResponse.getStatus() == 200) {
                        User user = UserMapper.toDomainModel(registerResponse.getUser());
                        RegisterResult result = new RegisterResult(true, registerResponse.getMessage(), user);
                        callback.onSuccess(result);
                    } else {
                        callback.onError(registerResponse.getMessage());
                    }
                } else {
                    try (ResponseBody errorBody = response.errorBody()) {
                        if (errorBody == null) throw new RuntimeException("Error body is null");
                        String errorBodyString = errorBody.string();
                        RegisterResponseDto errorResponse = new Gson().fromJson(
                                errorBodyString, RegisterResponseDto.class);
                        callback.onError(errorResponse.getMessage() != null ? errorResponse.getMessage() : "Đăng ký thất bại");
                    } catch (IOException e) {
                        callback.onError("Lỗi khi đọc thông báo lỗi từ máy chủ");
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseDto> call, Throwable t) {
                callback.onError("Lỗi mạng: " + t.getMessage());
            }
        });
    }
}