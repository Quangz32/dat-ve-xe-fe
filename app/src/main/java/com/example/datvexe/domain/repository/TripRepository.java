package com.example.datvexe.domain.repository;

import com.example.datvexe.data.remote.api.callback.ScheduleCallBack;
import com.example.datvexe.data.remote.api.callback.TripCallBack;
import com.example.datvexe.data.remote.dto.request.ScheduleReq;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BookingTripResult;
import com.example.datvexe.domain.model.User;

import java.util.Date;
import java.util.List;

public interface TripRepository {
    void getLocationTrip(TripCallBack callBack);

    void loadDataSchedule(ScheduleReq req, ScheduleCallBack callBack);


    void getUser(String userId, UserCallBack userCallback);

    void getEndBook(String id, BookCallBack bookCallBack );

    void bookingTrip(String user, String busSchedule, Long totalPrice, List<String> seats, String pickupLocation, String dropoffLocation,  Date departureTime, Boolean exportInvoice, String note, String paymentMethod, BookingTripCallBack callback);

    interface UserCallBack {
        void onSuccess(User user);

        void onError(String error);
    }

    interface BookCallBack {
        void onSuccess(BookingTrip book);

        void onError(String error);
    }

    interface BookingTripCallBack {
        void onSuccess(BookingTrip booking);

        void onError(String error);
    }

//    interface BookingTripCallBack {
//        void onSuccess(BookingTripResult booking);
//
//        void onError(String error);
//    }
}
