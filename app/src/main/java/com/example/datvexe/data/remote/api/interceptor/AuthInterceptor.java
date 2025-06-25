package com.example.datvexe.data.remote.api.interceptor;

import androidx.annotation.NonNull;

import com.example.datvexe.data.local.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    SharedPreferencesManager preferencesManager;

    public AuthInterceptor(SharedPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        String token = preferencesManager.getToken();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer " + token)
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
