package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Role ID không được để trống")
    private String roleId;

    private List<String> permissionIds;

    private Date createdAt;

    private Date updatedAt;

    // Nested objects for populated data
    private Role roleDetail;
    private List<Permission> permissionDetails;
} 