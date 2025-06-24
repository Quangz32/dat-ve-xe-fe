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
public class BusScheduleDto {
    @SerializedName("_id")
    private String id;

    private String busOperator;

    private String tripCode;

    private String route;

    private Integer timeRoute;

    private Long price;

    private Date date;

    private Date timeStart;

    private String benXeKhoiHanh;

    private Date timeEnd;

    private String benXeDichDen;

    private Integer availableSeats;

    private List<String> seatSelected;

    private String status;

    private Date updatedAt;

    private Date createdAt;

    @SerializedName("__v")
    private Integer version;
} 