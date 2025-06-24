package com.example.datvexe.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserDto {
    @SerializedName("_id")
    private String id;

    private String email;

    private String fullname;

    private String phone;
} 