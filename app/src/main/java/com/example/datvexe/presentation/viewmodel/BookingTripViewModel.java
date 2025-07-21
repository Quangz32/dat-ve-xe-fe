package com.example.datvexe.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BookingTripResult;
import com.example.datvexe.domain.usecase.booking.BookingTripUseCase;
import com.example.datvexe.data.local.SharedPreferencesManager;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingTripViewModel extends ViewModel {
    private final BookingTripUseCase bookingTripUseCase ;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<BookingTrip> _bookingTrip = new MutableLiveData<>();
    private final MutableLiveData<BookingTripResult> _bookingTripResult = new MutableLiveData<>();
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<BookingTripResult> bookingTripResult = _bookingTripResult;
    private final MutableLiveData<Boolean> _showUser = new MutableLiveData<>(false);
    public LiveData<BookingTrip> bookingTrip = _bookingTrip;
    public LiveData<String> errorMessage = _errorMessage;
    private final MutableLiveData<String> _error = new MutableLiveData<>(null);

    public LiveData<String> error = _error;
    public final LiveData<Boolean> showUser = _showUser;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;
    @Inject
    public BookingTripViewModel(BookingTripUseCase bookingTripUseCase) {
        this.bookingTripUseCase = bookingTripUseCase;
    }

    public void bookingTripCreate(String user, String busSchedule, Long totalPrice, List<String> seats, String pickupLocation, String dropoffLocation, Date departureTime, Boolean exportInvoice, String note, String paymentMethod) {
        _isLoading.setValue(true);
        _error.setValue(null);
        String userId = sharedPreferencesManager.getUserId();
        bookingTripUseCase.execute(userId, busSchedule, totalPrice, seats, pickupLocation, dropoffLocation,departureTime, exportInvoice, note, paymentMethod,
                new BookingTripUseCase.BookingTripCallback() {
            @Override
            public void onSuccess(BookingTrip bookingTrip) {
//                Log.d("xxx-sucess", bookingTrip.toString());
                _isLoading.postValue(false);
                _bookingTrip.postValue(bookingTrip);

            }

            @Override
            public void onError(String error) {
//                Log.d("xxx-fail", bookingTrip.getValue().toString());
//                Log.d("xxx-fail", bookingTrip.toString());


                _isLoading.postValue(false);
                _error.postValue(error);
            }
        });
    }

    public void toggleookingTrip() {
        _showUser.setValue(Boolean.FALSE.equals(showUser.getValue()));
    }
}