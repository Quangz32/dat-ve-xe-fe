package com.example.datvexe.di;

import android.content.Context;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.data.remote.api.AuthApiService;
import com.example.datvexe.data.remote.api.BookingApiService;
import com.example.datvexe.data.repository.AuthRepositoryImpl;
import com.example.datvexe.data.repository.BookingRepositoryImpl;
import com.example.datvexe.domain.repository.AuthRepository;
import com.example.datvexe.domain.repository.BookingRepository;
import com.example.datvexe.domain.usecase.GetBookingByUserIdUseCase;
import com.example.datvexe.domain.usecase.LoginUseCase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    private static final String BASE_URL = "http://10.0.2.2:9999/";

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    public AuthApiService provideAuthApiService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }

    @Provides
    @Singleton
    public BookingApiService provideBookingApiService(Retrofit retrofit) {
        return retrofit.create(BookingApiService.class);
    }

    @Provides
    @Singleton
    public SharedPreferencesManager provideSharedPreferencesManager(@ApplicationContext Context context) {
        return new SharedPreferencesManager(context);
    }

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(AuthApiService authApiService, SharedPreferencesManager sharedPreferencesManager) {
        return new AuthRepositoryImpl(authApiService, sharedPreferencesManager);
    }

    @Provides
    @Singleton
    public BookingRepository provideBookingRepository(BookingApiService bookingApiService) {
        return new BookingRepositoryImpl(bookingApiService);
    }

    @Provides
    public LoginUseCase provideLoginUseCase(AuthRepository authRepository) {
        return new LoginUseCase(authRepository);
    }

    @Provides
    public GetBookingByUserIdUseCase provideGetBookingByUserIdUseCase(BookingRepository bookingRepository) {
        return new GetBookingByUserIdUseCase(bookingRepository);
    }
}
