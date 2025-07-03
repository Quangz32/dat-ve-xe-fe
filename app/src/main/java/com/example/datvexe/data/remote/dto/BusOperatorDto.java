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
public class BusOperatorDto {
    @SerializedName("_id")
    private String id;
    private String name;
    private Object types;
    private String phone;
    private String bienSoXe;
    private Boolean status;
    private Number rate;
    private Number totalTrips;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
