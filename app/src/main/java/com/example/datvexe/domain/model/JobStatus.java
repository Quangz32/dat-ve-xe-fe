package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStatus {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Last run date không được để trống")
    private String lastRunDate; // Format: "YYYY-MM-DD"
} 