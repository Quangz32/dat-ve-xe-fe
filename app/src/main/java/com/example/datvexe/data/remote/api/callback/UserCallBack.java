package com.example.datvexe.data.remote.api.callback;

import com.example.datvexe.data.remote.dto.UserDto;

public interface UserCallBack {
    void onSuccess(UserDto user); // <-- chỉ 1 user, không phải List
    void onError(String error);
}