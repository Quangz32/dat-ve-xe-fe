package com.example.datvexe.domain.usecase;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.repository.BookingRepository;

import java.util.List;

public class GetBookingByUserIdUseCase {

    private final BookingRepository bookingRepository;

    public GetBookingByUserIdUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void execute(String userId, UseCaseCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onError("User ID không được để trống");
            return;
        }

        bookingRepository.getBookingsByUserId(userId, new BookingRepository.BookingCallback() {
            @Override
            public void onSuccess(List<BookingTrip> bookings) {
                callback.onSuccess(bookings);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface UseCaseCallback {
        void onSuccess(List<BookingTrip> bookings);

        void onError(String error);
    }
} 