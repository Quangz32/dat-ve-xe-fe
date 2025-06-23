package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusOperators {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Tên nhà xe không được để trống")
    private String name;
    
    private String types; // Reference to TypeBus ID
    
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    
    @NotBlank(message = "Biển số xe không được để trống")
    private String bienSoXe;
    
    @Builder.Default
    private Boolean status = true;
    
    @Min(value = 0, message = "Rating phải >= 0")
    @Max(value = 5, message = "Rating phải <= 5")
    @Builder.Default
    private Double rating = 0.0;
    
    @Min(value = 0, message = "Total trips phải >= 0")
    @Builder.Default
    private Integer totalTrips = 0;
    
    @Builder.Default
    private String description = "";
    
    private Date createdAt;
    
    private Date updatedAt;
    
    // Nested TypeBus object for populated data
    private TypeBus typeBusDetail;
} 