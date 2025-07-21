package com.example.datvexe.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.User;
import com.example.datvexe.domain.usecase.booking.GetEndBookingUseCase;
import com.example.datvexe.domain.usecase.booking.GetUserByIdUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingConfirmViewModel extends ViewModel {
    private final GetUserByIdUseCase getUserByIdUseCase;

//    private  final  GetEndBookingUseCase getEndBookingUseCase;

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    private final MutableLiveData<BookingTrip> _bookTrip = new MutableLiveData<>();

    private final MutableLiveData<Boolean> _showUser = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> _error = new MutableLiveData<>(null);

    public LiveData<User> user = _user;
    public LiveData<BookingTrip> bookingTrip = _bookTrip;
    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<String> error = _error;
    public final LiveData<Boolean> showUser = _showUser;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public BookingConfirmViewModel(GetUserByIdUseCase getUserByIdUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
//        this.getEndBookingUseCase = getEndBookingUseCase;
    }

    public void loadUser() {
        _isLoading.setValue(true);
        _error.setValue(null);

        String userId = sharedPreferencesManager.getUserId();
        getUserByIdUseCase.execute(userId, new GetUserByIdUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(User user) {
                Log.d("infomation", user.toString());
                _isLoading.postValue(false);
                _user.postValue(user);
            }

            @Override
            public void onError(String error) {
                _isLoading.postValue(false);
                _error.postValue(error);
            }
        });
    }
    public void toggleUser() {
        _showUser.setValue(Boolean.FALSE.equals(showUser.getValue()));
    }


}
