package com.example.datvexe.domain.model;

public class LoginResult {
    private boolean isSuccess;
    private String message;
    private String token;

    public LoginResult(boolean isSuccess, String message, String token) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.token = token;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
} 