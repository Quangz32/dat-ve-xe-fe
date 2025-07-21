package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingRequestDto {

    private String user;
    private String busSchedule;
    private Long totalPrice;

    private List<String> seats;
    private String pickupLocation;
    private String dropoffLocation;

    private Date departureTime;

    private Boolean exportInvoice;

    private String note;
    private String paymentMethod;

}