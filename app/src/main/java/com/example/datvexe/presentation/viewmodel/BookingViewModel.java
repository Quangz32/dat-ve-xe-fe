package com.example.datvexe.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.usecase.GetBookingByUserIdUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingViewModel extends ViewModel {
    private final GetBookingByUserIdUseCase getBookingByUserIdUseCase;

    private final MutableLiveData<List<BookingTrip>> _bookings = new MutableLiveData<>();
    public final LiveData<List<BookingTrip>> bookings = _bookings;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    @Inject
    public BookingViewModel(GetBookingByUserIdUseCase getBookingByUserIdUseCase) {
        this.getBookingByUserIdUseCase = getBookingByUserIdUseCase;
    }

    public void loadBookings(String userId) {
        _isLoading.setValue(true);
        _error.setValue(null);

        getBookingByUserIdUseCase.execute(userId, new GetBookingByUserIdUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(List<BookingTrip> bookings) {
                Log.d("books", bookings.toString());
                _isLoading.setValue(false);
                _bookings.setValue(bookings);
            }

            @Override
            public void onError(String error) {
                _isLoading.setValue(false);
                _error.setValue(error);
            }
        });
    }

    public void refreshBookings(String userId) {
        loadBookings(userId);
    }

}
