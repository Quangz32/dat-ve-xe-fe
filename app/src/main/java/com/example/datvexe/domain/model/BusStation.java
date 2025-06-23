package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusStation {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Mã bến xe không được để trống")
    private String maBenXe;
    
    @NotBlank(message = "Tên bến xe không được để trống")
    private String tenBenXe;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private Date deletedAt;
} 