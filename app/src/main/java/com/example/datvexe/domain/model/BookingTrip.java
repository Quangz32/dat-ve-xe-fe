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
public class BookingTrip {
    
    @SerializedName("_id")
    private String id;
    
    @NotBlank(message = "Code không được để trống")
    private String code;
    
    @NotBlank(message = "User không được để trống")
    private String user; // Reference to User ID
    
    @NotBlank(message = "Bus schedule không được để trống")
    private String busSchedule; // Reference to BusSchedule ID
    
    @NotNull(message = "Surcharge không được null")
    @Min(value = 0, message = "Surcharge phải >= 0")
    @Builder.Default
    private Long surcharge = 0L;
    
    @NotNull(message = "Total price không được null")
    @Min(value = 0, message = "Total price phải >= 0")
    private Long totalPrice;
    
    @NotNull(message = "Seats không được null")
    private List<String> seats;
    
    @NotBlank(message = "Pickup location không được để trống")
    private String pickupLocation;
    
    @NotBlank(message = "Dropoff location không được để trống")
    private String dropoffLocation;
    
    @NotNull(message = "Departure time không được null")
    private Date departureTime;
    
    @Builder.Default
    private Boolean exportInvoice = false;
    
    private String note;
    
    @Builder.Default
    private String status = "pending";
    
    @Builder.Default
    private String paymentMethod = "cash";
    
    private String reasonCancel;
    
    private String transactionId;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    // Nested objects for populated data
    private User userDetail;
    private BusSchedule busScheduleDetail;
    
    // Enum cho trạng thái booking
    public enum BookingStatus {
        PENDING("pending"),
        CONFIRMED("confirmed"),
        PAYED("payed"),
        CANCELLED("cancelled"),
        COMPLETED("completed"),
        DRAFT("draft");
        
        private final String value;
        
        BookingStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static BookingStatus fromValue(String value) {
            for (BookingStatus status : BookingStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return PENDING;
        }
    }
    
    // Enum cho phương thức thanh toán
    public enum PaymentMethod {
        CASH("cash"),
        VNPAY("VNPay"),
        BANKING("Banking");
        
        private final String value;
        
        PaymentMethod(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static PaymentMethod fromValue(String value) {
            for (PaymentMethod method : PaymentMethod.values()) {
                if (method.value.equals(value)) {
                    return method;
                }
            }
            return CASH;
        }
    }
} 