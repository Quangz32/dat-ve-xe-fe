package com.example.datvexe.domain.repository;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.Discount;

import java.util.List;

public interface BookingRepository {

    void getBookingsByUserId(String userId, BookingCallback callback);

    void getBookingsHistoryByUserId(String userId, BookingCallback callback);

    void getDiscountsByUser(DiscountCallback callback);

    interface BookingCallback {
        void onSuccess(List<BookingTrip> bookings);

        void onError(String error);
    }

    interface DiscountCallback {
        void onSuccess(List<Discount> discounts);
        void onError(String error);
    }
} 