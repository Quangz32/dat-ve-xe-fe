package com.example.datvexe.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.data.local.SharedPreferencesManager;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.usecase.booking.GetBookingByUserIdUseCase;
import com.example.datvexe.domain.usecase.booking.GetBookingHistoryByUserIdUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingViewModel extends ViewModel {
    private final GetBookingByUserIdUseCase getBookingUC;
    private final GetBookingHistoryByUserIdUseCase getBookingHistoryUC;

    private final MutableLiveData<Boolean> _showHistory = new MutableLiveData<>(false);
    private final MutableLiveData<List<BookingTrip>> _bookings = new MutableLiveData<>();
    private final MutableLiveData<List<BookingTrip>> _bookingsHistory = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();

    public final LiveData<Boolean> showHistory = _showHistory;
    public final LiveData<List<BookingTrip>> bookings = _bookings;
    public final LiveData<List<BookingTrip>> bookingsHistory = _bookingsHistory;
    public final LiveData<Boolean> isLoading = _isLoading;
    public final LiveData<String> error = _error;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @Inject
    public BookingViewModel(GetBookingByUserIdUseCase getBookingByUserIdUseCase,
                            GetBookingHistoryByUserIdUseCase getBookingHistoryByUserIdUseCase) {
        this.getBookingUC = getBookingByUserIdUseCase;
        this.getBookingHistoryUC = getBookingHistoryByUserIdUseCase;
    }

    public void loadBookings() {
        _isLoading.setValue(true);
        _error.setValue(null);

        String userId = sharedPreferencesManager.getUserId();
        getBookingUC.execute(userId, new GetBookingByUserIdUseCase.UseCaseCallback() {
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

    public void loadHistoryBooking() {
        _isLoading.setValue(true);
        _error.setValue(null);

        String userId = sharedPreferencesManager.getUserId();
        getBookingHistoryUC.execute(userId, new GetBookingHistoryByUserIdUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(List<BookingTrip> bookings) {
                Log.d("books history", bookings.toString());
                _isLoading.setValue(false);
                _bookingsHistory.setValue(bookings);
            }

            @Override
            public void onError(String error) {
                _isLoading.setValue(false);
                _error.setValue(error);
            }
        });
    }

    public void refreshBookings() {
        loadBookings();
    }

    public void toggleBookingHistory() {
        _showHistory.setValue(Boolean.FALSE.equals(showHistory.getValue()));
    }

    public void showBookingsHistory() {
        _showHistory.setValue(true);
    }

    public void showBookings() {
        _showHistory.setValue(false);
    }

}
