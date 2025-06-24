package com.example.datvexe.data.remote.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
} 