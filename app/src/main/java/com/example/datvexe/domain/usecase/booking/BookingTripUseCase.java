package com.example.datvexe.domain.usecase.booking;

import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BookingTripResult;
import com.example.datvexe.domain.repository.TripRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class BookingTripUseCase {
    private final TripRepository tripRepository;

    @Inject
    public BookingTripUseCase(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void execute(String user, String busSchedule, Long totalPrice, List<String> seats, String pickupLocation, String dropoffLocation, Date departureTime,  Boolean exportInvoice,String note, String paymentMethod, BookingTripCallback callback) {
        // Validate input
//        if (user == null || user.trim().isEmpty()) {
//            callback.onError("Id không được để trống");
//            return;
//        }
//
//        if (busSchedule == null || busSchedule.trim().isEmpty()) {
//            callback.onError("id người lái không được để trống");
//            return;
//        }
//
//        if (pickupLocation == null || pickupLocation.trim().isEmpty()) {
//            callback.onError("Điểm đi không được để trống");
//            return;
//        }
//
//        if (dropoffLocation == null || dropoffLocation.trim().isEmpty()) {
//            callback.onError("Điểm đến không được để trống");
//            return;
//        }
//
//        if (seats == null || seats.size() < 1) {
//            callback.onError("Ghế không được để trống");
//            return;
//        }
//
//
//        if (tripRepository == null) {
//            callback.onError("Gía không được để trống");
//            return;
//        }


        // Validate email format

        tripRepository.bookingTrip(user.trim(), busSchedule.trim(),totalPrice, seats, pickupLocation.trim(), dropoffLocation.trim(),departureTime, exportInvoice, note.trim(), paymentMethod.trim(),
                new TripRepository.BookingTripCallBack() {
//                    @Override
//                    public void onSuccess(BookingTripResult result) {
//                        callback.onSuccess(result);
//                    }

                    @Override
                    public void onSuccess(BookingTrip result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
    }

//    public interface BookingTripCallback {
//        void onSuccess(BookingTripResult result);
//
//        void onError(String error);
//    }

    public interface BookingTripCallback {
        void onSuccess(BookingTrip result);

        void onError(String error);
    }
}
