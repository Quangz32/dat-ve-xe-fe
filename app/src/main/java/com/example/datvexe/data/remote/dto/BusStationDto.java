package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusStationDto {
    @SerializedName("_id")
    private String id;

    private String maBenXe;

    private String tenBenXe;

    private Date createdAt;
}
