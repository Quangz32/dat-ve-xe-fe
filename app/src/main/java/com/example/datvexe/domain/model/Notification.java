package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @SerializedName("_id")
    private String id;

    private String type;

    private String title;

    private String message;

    @NotBlank(message = "Tab không được để trống")
    @Builder.Default
    private String tab = "events";

    private String user; // Reference to User ID

    private Date createdAt;

    // Nested User object for populated data
    private User userDetail;

    // Enum cho các loại tab
    public enum NotificationTab {
        EVENTS("events"),
        PROMOTIONS("promotions");

        private final String value;

        NotificationTab(String value) {
            this.value = value;
        }

        public static NotificationTab fromValue(String value) {
            for (NotificationTab tab : NotificationTab.values()) {
                if (tab.value.equals(value)) {
                    return tab;
                }
            }
            return EVENTS;
        }

        public String getValue() {
            return value;
        }
    }
} 