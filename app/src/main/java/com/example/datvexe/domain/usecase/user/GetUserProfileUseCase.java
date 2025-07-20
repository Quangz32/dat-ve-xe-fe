package com.example.datvexe.domain.usecase.user;

import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.UserRepository;

import javax.inject.Inject;

public class GetUserProfileUseCase {
    private final UserRepository userRepository;

    @Inject
    public GetUserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String userId, UserRepository.UserProfileCallback callback) {
        userRepository.getUserProfile(userId, callback);
    }
} 