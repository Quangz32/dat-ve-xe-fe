package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datvexe.data.local.TripService;
import com.example.datvexe.data.remote.api.callback.TripCallBack;
import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.domain.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LocationTripViewModel extends ViewModel {
    private final TripRepository tripRepository;
    private final MutableLiveData<List<LocationTripDto>> locationTrips = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final TripService tripService = TripService.getInstance();

    @Inject
    public LocationTripViewModel(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public LiveData<List<LocationTripDto>> getLocationTrips() {
        return locationTrips;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadLocationTrips() {
        isLoading.setValue(true);
        tripRepository.getLocationTrip(new TripCallBack() {
            @Override
            public void onSuccess(List<LocationTripDto> data) {
                locationTrips.postValue(data);
                tripService.setLocations(data);
                isLoading.postValue(false);
            }

            @Override
            public void onError(String message) {
                error.postValue(message);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                error.postValue("Lỗi kết nối: " + (throwable.getMessage() != null ? throwable.getMessage() : "Không thể kết nối đến máy chủ"));
                isLoading.postValue(false);
            }
        });
    }
} 
