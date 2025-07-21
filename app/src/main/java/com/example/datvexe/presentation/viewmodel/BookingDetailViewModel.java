package com.example.datvexe.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.datvexe.domain.model.BookingDetail;

public class BookingDetailViewModel extends ViewModel {
    private final MutableLiveData<BookingDetail> bookingDetail = new MutableLiveData<>();

    public LiveData<BookingDetail> getBookingDetail() {
        return bookingDetail;
    }

    public void loadBookingDetail(String bookingCode) {
        // TODO: Gọi API thực tế, ở đây demo dữ liệu cứng
        BookingDetail detail = new BookingDetail(
            bookingCode,
            "Sapa - Hà Nội",
            "Luu Phuc An",
            "A1",
            "09/06/2025 21:00",
            "400 đ",
            "Chờ xử lý",
            "cash"
        );
        bookingDetail.setValue(detail);
    }
} 