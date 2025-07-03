package com.example.datvexe.data.remote.dto;

import com.example.datvexe.domain.model.BusStation;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationTripDto {
    @SerializedName("_id")
    private String id;

    private String maTinh;

    private String tenTinh;

    private List<BusStation> benXe;

    private Date createdAt;

}
