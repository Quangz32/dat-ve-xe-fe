package com.example.datvexe.domain.usecase.booking;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.TripRepository;

import javax.inject.Inject;

public class GetEndBookingUseCase {

    private final TripRepository tripRepository;

    @Inject
    public GetEndBookingUseCase(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void execute(String userId, UseCaseCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onError("User ID không được để trống");
            return;
        }

        tripRepository.getEndBook(userId, new TripRepository.BookCallBack() {
            @Override
            public void onSuccess(BookingTrip user) {
                callback.onSuccess(user);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface UseCaseCallback {
        void onSuccess(BookingTrip user);

        void onError(String error);
    }
}