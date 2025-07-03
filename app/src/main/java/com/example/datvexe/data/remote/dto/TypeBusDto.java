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
public class TypeBusDto {
    @SerializedName("_id")
    private String id;
    private String name;
    private String code;
    private Number seats;
    private List<String> features;
    private String description;
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;
    private String model;
}
