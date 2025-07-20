package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.repository.UserRepository;
import com.example.datvexe.domain.usecase.user.GetUserProfileUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    public UserViewModel(GetUserProfileUseCase getUserProfileUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase;
    }

    public LiveData<User> getUser() { return userLiveData; }
    public LiveData<String> getError() { return errorLiveData; }

    public void loadUserProfile(String userId) {
        getUserProfileUseCase.execute(userId, new UserRepository.UserProfileCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.postValue(user);
            }
            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        });
    }
} 