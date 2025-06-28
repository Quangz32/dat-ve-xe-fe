package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationResponseDto {
    @SerializedName("_id")
    private String id;

    private String type;

    private String title;

    private String message;

    private String tab;

    private UserDto user;

    private Date createdAt;

    @SerializedName("__v")
    private Integer version;
}
