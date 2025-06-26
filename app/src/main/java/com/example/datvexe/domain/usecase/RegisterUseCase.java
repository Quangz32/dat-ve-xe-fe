package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.model.RegisterResult;
import com.example.datvexe.domain.repository.AuthRepository;

import javax.inject.Inject;

public class RegisterUseCase {
    private final AuthRepository authRepository;

    @Inject
    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String username, String password, String email, String fullname, String phone,
                        RegisterCallback callback) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            callback.onError("Tên đăng nhập không được để trống");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            callback.onError("Mật khẩu không được để trống");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            callback.onError("Email không được để trống");
            return;
        }

        if (fullname == null || fullname.trim().isEmpty()) {
            callback.onError("Họ và tên không được để trống");
            return;
        }

        if (phone == null || phone.trim().isEmpty()) {
            callback.onError("Số điện thoại không được để trống");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            callback.onError("Email không đúng định dạng");
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            callback.onError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }

        // Validate phone format
        if (!isValidPhone(phone)) {
            callback.onError("Số điện thoại không đúng định dạng");
            return;
        }

        authRepository.register(username.trim(), password, email.trim(), fullname.trim(), phone.trim(),
                new AuthRepository.RegisterCallback() {
                    @Override
                    public void onSuccess(RegisterResult result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9]{10,11}$");
    }

    public interface RegisterCallback {
        void onSuccess(RegisterResult result);

        void onError(String error);
    }
}