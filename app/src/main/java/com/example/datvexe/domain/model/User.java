package com.example.datvexe.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Role ID không được để trống")
    private String roleId;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullname;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @Builder.Default
    private String address = "";

    @Builder.Default
    private String status = "00";

    @Builder.Default
    private String avatar = "";

    private Date dateOfBirth;

    @Builder.Default
    private Gender gender = Gender.OTHER;

    @Builder.Default
    private Boolean verified = false;

    private Date lastLogin;

    @Builder.Default
    @Min(value = 0, message = "Điểm tích lũy phải >= 0")
    private Integer loyaltyPoints = 0;

    private String referralCode;

    @Builder.Default
    private Boolean isBlocked = false;

    private Date createdAt;

    private Date updatedAt;

    private Date deleteAt;

    // Nested objects for populated data
    private Role roleDetail;

    // Enum cho giới tính
    public enum Gender {
        MALE("male"),
        FEMALE("female"),
        OTHER("other");

        private final String value;

        Gender(String value) {
            this.value = value;
        }

        public static Gender fromValue(String value) {
            for (Gender gender : Gender.values()) {
                if (gender.value.equals(value)) {
                    return gender;
                }
            }
            return OTHER;
        }

        public String getValue() {
            return value;
        }
    }

    // Enum cho trạng thái người dùng
    public enum UserStatus {
        ACTIVE("00"),
        INACTIVE("01"),
        SUSPENDED("02"),
        PENDING("03");

        private final String value;

        UserStatus(String value) {
            this.value = value;
        }

        public static UserStatus fromValue(String value) {
            for (UserStatus status : UserStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return ACTIVE;
        }

        public String getValue() {
            return value;
        }
    }
} 