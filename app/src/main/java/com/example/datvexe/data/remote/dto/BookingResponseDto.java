package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class BookingResponseDto {
    @SerializedName("_id")
    private String id;

    private String code;

    private UserDto user;

    private BusScheduleDto busSchedule;

    private Long surcharge;

    private Long totalPrice;

    private List<String> seats;

    private String pickupLocation;

    private String dropoffLocation;

    private Date departureTime;

    private Boolean exportInvoice;

    private String note;

    private String status;

    private String paymentMethod;

    private String transactionId;

    private Date updatedAt;

    private Date createdAt;

    @SerializedName("__v")
    private Integer version;
} 