package com.example.datvexe.domain.usecase.booking;

import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

public class GetUserByIdUseCase {

    private final TripRepository tripRepository;

    @Inject
    public GetUserByIdUseCase(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void execute(String userId, UseCaseCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            callback.onError("User ID không được để trống");
            return;
        }

        tripRepository.getUser(userId, new TripRepository.UserCallBack() {
            @Override
            public void onSuccess(User user) {
                callback.onSuccess(user);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    public interface UseCaseCallback {
        void onSuccess(User user);

        void onError(String error);
    }
} 