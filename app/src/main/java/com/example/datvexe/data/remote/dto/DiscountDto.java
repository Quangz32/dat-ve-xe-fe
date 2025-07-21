package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DiscountDto {
    @SerializedName("id")
    private String id;

    @SerializedName("code")
    private String code;

    @SerializedName("discount")
    private Integer percent;

    @SerializedName("description")
    private String description;
} 