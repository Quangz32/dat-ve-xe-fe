package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

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
public class LogRequest {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Method không được để trống")
    private String method; // GET, POST, PUT, DELETE

    @NotBlank(message = "URL không được để trống")
    private String url;

    @NotNull(message = "Status code không được null")
    private Integer statusCode;

    @NotBlank(message = "IP không được để trống")
    private String ip;

    private String userAgent;

    private Map<String, Object> headers;

    @Builder.Default
    private Map<String, Object> requestBody = new java.util.HashMap<>();

    @Builder.Default
    private Map<String, Object> responseBody = new java.util.HashMap<>();

    private String user; // Reference to User ID

    private Date createdAt;

    // Nested User object for populated data
    private User userDetail;
} 