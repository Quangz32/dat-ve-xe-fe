package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.BookingTrip;

import java.util.List;

public interface BookingRepository {

    void getBookingsByUserId(String userId, BookingCallback callback);

    interface BookingCallback {
        void onSuccess(List<BookingTrip> bookings);

        void onError(String error);
    }
} 