package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.usecase.auth.LogoutUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AccountViewModel extends ViewModel {
    private final LogoutUseCase logoutUseCase;

    @Inject
    public AccountViewModel(LogoutUseCase logoutUseCase) {
        this.logoutUseCase = logoutUseCase;
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
