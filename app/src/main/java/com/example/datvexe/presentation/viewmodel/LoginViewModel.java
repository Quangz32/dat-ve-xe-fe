package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.LoginResult;
import com.example.datvexe.domain.usecase.auth.LoginUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> _loginResult = new MutableLiveData<>();
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    //Public LiveData để bên ngoài có thể quan sát
    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<LoginResult> loginResult = _loginResult;
    public LiveData<String> errorMessage = _errorMessage;

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void login(String username, String password) {
        _isLoading.setValue(true);

        loginUseCase.execute(username, password, new LoginUseCase.LoginCallback() {
            @Override
            public void onSuccess(LoginResult result) {
                _isLoading.setValue(false);
                _loginResult.setValue(result);
            }

            @Override
            public void onError(String error) {
                _isLoading.setValue(false);
                _errorMessage.setValue(error);
            }
        });
    }

    public void clearErrorMessage() {
        _errorMessage.setValue(null);
    }
} 