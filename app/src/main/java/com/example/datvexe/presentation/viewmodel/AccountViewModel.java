package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.usecase.auth.LogoutUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AccountViewModel extends ViewModel {
    private final LogoutUseCase logoutUseCase;

    private final MutableLiveData<String> userName = new MutableLiveData<>("Luu Phuc An");
    private final MutableLiveData<String> phoneNumber = new MutableLiveData<>("0398653925");
    private final MutableLiveData<Integer> silver = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> promotion = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> friends = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> news = new MutableLiveData<>(0);

    public LiveData<String> getUserName() { return userName; }
    public LiveData<String> getPhoneNumber() { return phoneNumber; }
    public LiveData<Integer> getSilver() { return silver; }
    public LiveData<Integer> getPromotion() { return promotion; }
    public LiveData<Integer> getFriends() { return friends; }
    public LiveData<Integer> getNews() { return news; }

    @Inject
    public AccountViewModel(LogoutUseCase logoutUseCase) {
        this.logoutUseCase = logoutUseCase;
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
