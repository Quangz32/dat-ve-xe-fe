 package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.datvexe.domain.usecase.auth.ForgotPasswordUseCase;
import com.example.datvexe.domain.model.Result;

public class ForgotPasswordViewModel extends ViewModel {
    private final MutableLiveData<Result> forgotPasswordResult = new MutableLiveData<>();
    private final ForgotPasswordUseCase forgotPasswordUseCase = new ForgotPasswordUseCase();

    public void forgotPassword(String usernameOrEmail) {
        forgotPasswordUseCase.execute(usernameOrEmail, result -> forgotPasswordResult.postValue(result));
    }

    public LiveData<Result> getForgotPasswordResult() {
        return forgotPasswordResult;
    }
}
