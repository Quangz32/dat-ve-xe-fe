package com.example.datvexe.domain.model;

import android.os.Parcelable;

public class BookingTripResult {
    private boolean isSuccess;
    private String message;
    private BookingTrip bookingTrip;

    public BookingTripResult(boolean isSuccess, String message, BookingTrip bookingTrip) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.bookingTrip = bookingTrip;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookingTrip getBookingTrip() {
        return  bookingTrip;
    }

    public void setBookingTrip(BookingTrip token) {
        this.bookingTrip = token;
    }
}