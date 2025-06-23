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
public class Permission {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Tên quyền không được để trống")
    private String name;
    
    @NotBlank(message = "Mã quyền không được để trống")
    private String code;
    
    @Builder.Default
    private String description = "";
    
    private Date createdAt;
    
    private Date updatedAt;
} 