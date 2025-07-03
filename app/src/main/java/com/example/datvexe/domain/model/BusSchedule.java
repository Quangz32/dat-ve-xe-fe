package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
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
    private double price;

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

        public static ScheduleStatus fromValue(String value) {
            for (ScheduleStatus status : ScheduleStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return SCHEDULED;
        }

        public String getValue() {
            return value;
        }
    }

    // Display fields
    private String busName;
    private String busInfo;
    private String departureTime;
    private String departureLocation;
    private String duration;
    private String arrivalTime;
    private String arrivalLocation;
    private String formattedPrice; // Định dạng giá để hiển thị

    public BusSchedule(String busName, String route, String busInfo, double price,
                      String departureTime, String departureLocation, String duration,
                      String arrivalTime, String arrivalLocation) {
        this.busName = busName;
        this.route = route;
        this.busInfo = busInfo;
        this.price = price;
        this.departureTime = departureTime;
        this.departureLocation = departureLocation;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
        this.arrivalLocation = arrivalLocation;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBusInfo() {
        return busInfo;
    }

    public void setBusInfo(String busInfo) {
        this.busInfo = busInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }
    
    public String getFormattedPrice() {
        return formattedPrice != null ? formattedPrice : String.valueOf((int)price) + "đ";
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }
} 
