package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeBus {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Tên loại xe không được để trống")
    private String name;
    
    @NotBlank(message = "Mã loại xe không được để trống")
    private String code;
    
    private String model;
    
    @NotNull(message = "Số ghế không được null")
    private Integer seats;
    
    @Builder.Default
    private List<String> features = new java.util.ArrayList<>();
    
    @Builder.Default
    private String description = "";
    
    @Builder.Default
    private List<String> imageUrl = new java.util.ArrayList<>();
    
    private Date createdAt;
    
    private Date updatedAt;
} 