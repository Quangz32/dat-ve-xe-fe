package com.example.datvexe.data.remote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingTripResponseDto {
    private int status;
    private String message;
    //    private boolean success;
    private BookingResponseDto data;

}
