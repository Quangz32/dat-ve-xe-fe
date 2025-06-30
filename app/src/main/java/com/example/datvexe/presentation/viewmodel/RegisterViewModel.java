package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.RegisterResult;
import com.example.datvexe.domain.usecase.auth.RegisterUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<RegisterResult> _registerResult = new MutableLiveData<>();
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<RegisterResult> registerResult = _registerResult;
    public LiveData<String> errorMessage = _errorMessage;

    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
        _isLoading.setValue(false);
    }

    public void register(String username, String password, String email, String fullname, String phone) {
        _isLoading.setValue(true);

        registerUseCase.execute(username, password, email, fullname, phone, new RegisterUseCase.RegisterCallback() {
            @Override
            public void onSuccess(RegisterResult result) {
                _isLoading.setValue(false);
                _registerResult.setValue(result);
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