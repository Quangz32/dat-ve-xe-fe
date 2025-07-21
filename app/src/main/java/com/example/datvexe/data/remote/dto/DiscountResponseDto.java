package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponseDto {
      @SerializedName("_id")
    private String id;
    private String title;
    private String code;
    private Integer percent;
    private Integer quantity;
    private String statas;
    private Date updatedAt;
    private Date createdAt;


    
}