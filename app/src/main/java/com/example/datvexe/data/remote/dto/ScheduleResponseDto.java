package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    @SerializedName("_id")
    private String id;
    private BusOperatorDto busOperator;
    private String tripCode;
    private String route;
    private Number timeRoute;
    private Number price;
    private Date date;
    private Date timeStart;
    private Date timeEnd;
    private BusStationDto benXeKhoiHanh;
    private BusStationDto benXeDichDen;
    private Number availableSeats;
    private List<String> seatSelected;
    private String status;
    private Date updatedAt;
    private Date createdAt;
}
