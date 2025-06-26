package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.RegisterResult;
import com.example.datvexe.domain.usecase.RegisterUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
        isLoading.setValue(false);
    }

    public void register(String username, String password, String email, String fullname, String phone) {
        isLoading.setValue(true);

        registerUseCase.execute(username, password, email, fullname, phone, new RegisterUseCase.RegisterCallback() {
            @Override
            public void onSuccess(RegisterResult result) {
                isLoading.setValue(false);
                registerResult.setValue(result);
            }

            @Override
            public void onError(String error) {
                isLoading.setValue(false);
                errorMessage.setValue(error);
            }
        });
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }
}