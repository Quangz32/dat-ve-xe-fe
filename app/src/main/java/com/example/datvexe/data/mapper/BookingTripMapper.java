package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.BookingTripDto;
import com.example.datvexe.domain.model.BookingTrip;

public class BookingTripMapper {
    public static BookingTrip toDomainModel(BookingTripDto dto) {
        if (dto == null)
            return null;

        return BookingTrip.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .user(dto.getUser())
                .busSchedule(dto.getBusSchedule() )
                .totalPrice(dto.getTotalPrice())
                .seats(dto.getSeats())
                .pickupLocation(dto.getPickupLocation())
                .dropoffLocation(dto.getDropoffLocation())
                .departureTime(dto.getDepartureTime())
                .exportInvoice(dto.getExportInvoice())
                .note(dto.getNote())
                .paymentMethod(dto.getPaymentMethod())
                .build();
    }
}
