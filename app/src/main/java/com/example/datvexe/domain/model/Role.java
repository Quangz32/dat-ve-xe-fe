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
public class Role {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Tên vai trò không được để trống")
    private String name;

    @NotBlank(message = "Mã vai trò không được để trống")
    private String code;

    @Builder.Default
    private String description = "";

    private String color;

    private Date createdAt;

    private Date updatedAt;
} 