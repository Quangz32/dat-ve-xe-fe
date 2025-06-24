package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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