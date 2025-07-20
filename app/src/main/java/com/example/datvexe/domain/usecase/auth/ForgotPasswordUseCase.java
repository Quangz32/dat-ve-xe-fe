package com.example.datvexe.domain.usecase.auth;

import com.example.datvexe.domain.model.Result;

public class ForgotPasswordUseCase {
    public void execute(String usernameOrEmail, Callback callback) {
        // TODO: Gọi API thực tế ở đây, demo trả về thành công
        if (usernameOrEmail.equals("test")) {
            callback.onResult(new Result(false, "Tài khoản không tồn tại"));
        } else {
            callback.onResult(new Result(true, null));
        }
    }

    public interface Callback {
        void onResult(Result result);
    }
}
 