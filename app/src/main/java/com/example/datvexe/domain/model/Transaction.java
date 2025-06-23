package com.example.datvexe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @SerializedName("_id")
    private String id;
    
    private Double amount;
    
    private String description;
    
    private String qrCode;
    
    @Builder.Default
    private String status = "pending"; // 'pending' | 'paid'
    
    private Date createdAt;
    
    // Enum cho trạng thái transaction
    public enum TransactionStatus {
        PENDING("pending"),
        PAID("paid");
        
        private final String value;
        
        TransactionStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static TransactionStatus fromValue(String value) {
            for (TransactionStatus status : TransactionStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return PENDING;
        }
    }
} 