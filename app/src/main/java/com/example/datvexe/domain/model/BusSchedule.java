package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusSchedule {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Bus operator không được để trống")
    private String busOperator; // Reference to BusOperators ID
    
    @NotBlank(message = "Trip code không được để trống")
    private String tripCode;
    
    @NotBlank(message = "Route không được để trống")
    private String route;
    
    @NotNull(message = "Time route không được null")
    private Integer timeRoute;
    
    @NotNull(message = "Price không được null")
    @Min(value = 0, message = "Price phải >= 0")
    private Long price;
    
    @NotNull(message = "Date không được null")
    private Date date;
    
    @NotBlank(message = "Time start không được để trống")
    private String timeStart;
    
    @NotBlank(message = "Bến xe khởi hành không được để trống")
    private String benXeKhoiHanh; // Reference to BusStation ID
    
    @NotBlank(message = "Time end không được để trống")
    private String timeEnd;
    
    @NotBlank(message = "Bến xe đích đến không được để trống")
    private String benXeDichDen; // Reference to BusStation ID
    
    @NotNull(message = "Available seats không được null")
    @Min(value = 0, message = "Available seats phải >= 0")
    private Integer availableSeats;
    
    @Builder.Default
    private List<String> seatSelected = new java.util.ArrayList<>();
    
    @Builder.Default
    private String status = "scheduled";
    
    private Date createdAt;
    
    private Date updatedAt;
    
    // Nested objects for populated data
    private BusOperators busOperatorDetail;
    private BusStation benXeKhoiHanhDetail;
    private BusStation benXeDichDenDetail;
    
    // Enum cho trạng thái chuyến xe
    public enum ScheduleStatus {
        SCHEDULED("scheduled"),
        DEPARTED("departed"),
        ARRIVED("arrived"),
        CANCELLED("cancelled");
        
        private final String value;
        
        ScheduleStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static ScheduleStatus fromValue(String value) {
            for (ScheduleStatus status : ScheduleStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return SCHEDULED;
        }
    }
} 