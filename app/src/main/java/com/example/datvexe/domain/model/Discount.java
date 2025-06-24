package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Title không được để trống")
    private String title;

    @NotBlank(message = "Code không được để trống")
    private String code;

    @NotBlank(message = "Description không được để trống")
    private String description;

    @NotNull(message = "Percent không được null")
    @Min(value = 0, message = "Percent phải >= 0")
    @Max(value = 100, message = "Percent phải <= 100")
    private Integer percent;

    @Min(value = 0, message = "Quantity phải >= 0")
    @Builder.Default
    private Integer quantity = 0;

    @Builder.Default
    private String status = "01";

    private Date createdAt;

    private Date updatedAt;
} 