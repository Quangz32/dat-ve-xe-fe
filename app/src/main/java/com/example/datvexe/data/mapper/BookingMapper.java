package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.BookingResponseDto;
import com.example.datvexe.data.remote.dto.BusScheduleDto;
import com.example.datvexe.data.remote.dto.UserDto;
import com.example.datvexe.domain.model.BookingTrip;
import com.example.datvexe.domain.model.BusSchedule;
import com.example.datvexe.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static BookingTrip toDomainModel(BookingResponseDto dto) {
        if (dto == null) return null;

        return BookingTrip.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .user(dto.getUser() != null ? dto.getUser().getId() : null)
                .busSchedule(dto.getBusSchedule() != null ? dto.getBusSchedule().getId() : null)
                .surcharge(dto.getSurcharge() != null ? dto.getSurcharge() : 0L)
                .totalPrice(dto.getTotalPrice())
                .seats(dto.getSeats())
                .pickupLocation(dto.getPickupLocation())
                .dropoffLocation(dto.getDropoffLocation())
                .departureTime(dto.getDepartureTime())
                .exportInvoice(dto.getExportInvoice() != null ? dto.getExportInvoice() : false)
                .note(dto.getNote())
                .status(dto.getStatus())
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .userDetail(mapUserDto(dto.getUser()))
                .busScheduleDetail(mapBusScheduleDto(dto.getBusSchedule()))
                .build();
    }

    public static List<BookingTrip> toDomainModelList(List<BookingResponseDto> dtoList) {
        if (dtoList == null) return new ArrayList<>();

        List<BookingTrip> bookingList = new ArrayList<>();
        for (BookingResponseDto dto : dtoList) {
            BookingTrip booking = toDomainModel(dto);
            if (booking != null) {
                bookingList.add(booking);
            }
        }
        return bookingList;
    }

    private static User mapUserDto(UserDto userDto) {
        if (userDto == null) return null;

        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .fullname(userDto.getFullname())
                .phone(userDto.getPhone())
                .build();
    }

    private static BusSchedule mapBusScheduleDto(BusScheduleDto busScheduleDto) {
        if (busScheduleDto == null) return null;

        return BusSchedule.builder()
                .id(busScheduleDto.getId())
                .busOperator(busScheduleDto.getBusOperator())
                .tripCode(busScheduleDto.getTripCode())
                .route(busScheduleDto.getRoute())
                .timeRoute(busScheduleDto.getTimeRoute())
                .price(busScheduleDto.getPrice())
                .date(busScheduleDto.getDate())
                .timeStart(convertDateToString(busScheduleDto.getTimeStart()))
                .benXeKhoiHanh(busScheduleDto.getBenXeKhoiHanh())
                .timeEnd(convertDateToString(busScheduleDto.getTimeEnd()))
                .benXeDichDen(busScheduleDto.getBenXeDichDen())
                .availableSeats(busScheduleDto.getAvailableSeats())
                .seatSelected(busScheduleDto.getSeatSelected())
                .status(busScheduleDto.getStatus())
                .createdAt(busScheduleDto.getCreatedAt())
                .updatedAt(busScheduleDto.getUpdatedAt())
                .build();
    }

    private static String convertDateToString(java.util.Date date) {
        if (date == null) return null;
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }
} 