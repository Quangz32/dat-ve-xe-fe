package com.example.datvexe.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
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
public class BusOperators implements Parcelable {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Tên nhà xe không được để trống")
    private String name;

    private String types; // Reference to TypeBus ID

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Biển số xe không được để trống")
    private String bienSoXe;

    @Builder.Default
    private Boolean status = true;

    @Min(value = 0, message = "Rating phải >= 0")
    @Max(value = 5, message = "Rating phải <= 5")
    @Builder.Default
    private Double rating = 0.0;

    @Min(value = 0, message = "Total trips phải >= 0")
    @Builder.Default
    private Integer totalTrips = 0;

    @Builder.Default
    private String description = "";

    private Date createdAt;

    private Date updatedAt;

    // Nested TypeBus object for populated data
    private TypeBus typeBusDetail;
    
    // List of types for convenience
    private List<String> typesList;

    protected BusOperators(Parcel in) {
        id = in.readString();
        name = in.readString();
        types = in.readString();
        phone = in.readString();
        bienSoXe = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalTrips = null;
        } else {
            totalTrips = in.readInt();
        }
        description = in.readString();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        typeBusDetail = in.readParcelable(TypeBus.class.getClassLoader());
        typesList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(types);
        dest.writeString(phone);
        dest.writeString(bienSoXe);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        if (totalTrips == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalTrips);
        }
        dest.writeString(description);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeParcelable(typeBusDetail, flags);
        dest.writeStringList(typesList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusOperators> CREATOR = new Creator<BusOperators>() {
        @Override
        public BusOperators createFromParcel(Parcel in) {
            return new BusOperators(in);
        }

        @Override
        public BusOperators[] newArray(int size) {
            return new BusOperators[size];
        }
    };
    
    // Helper method to get types as a list
    public List<String> getTypes() {
        if (typesList != null) {
            return typesList;
        }
        
        List<String> result = new ArrayList<>();
        if (types != null && !types.isEmpty()) {
            result.add(types);
        }
        return result;
    }
} 
