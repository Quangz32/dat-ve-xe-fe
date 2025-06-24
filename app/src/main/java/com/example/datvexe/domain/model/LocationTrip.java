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
public class LocationTrip {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Mã tỉnh không được để trống")
    private String maTinh;

    @NotBlank(message = "Tên tỉnh không được để trống")
    private String tenTinh;

    private List<String> benXe; // List of BusStation IDs

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    // Nested BusStation objects for populated data
    private List<BusStation> benXeDetails;

    // Enum cho các tỉnh hỗ trợ
    public enum ProvinceType {
        HA_NOI("Hà Nội"),
        LAO_CAI("Lào Cai");

        private final String value;

        ProvinceType(String value) {
            this.value = value;
        }

        public static ProvinceType fromValue(String value) {
            for (ProvinceType province : ProvinceType.values()) {
                if (province.value.equals(value)) {
                    return province;
                }
            }
            return HA_NOI;
        }

        public String getValue() {
            return value;
        }
    }
} 