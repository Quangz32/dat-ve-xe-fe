package com.example.datvexe.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

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
public class TypeBus implements Parcelable {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Tên loại xe không được để trống")
    private String name;

    @NotBlank(message = "Mã loại xe không được để trống")
    private String code;

    private String model;

    @NotNull(message = "Số ghế không được null")
    private Integer seats;

    @Builder.Default
    private List<String> features = new java.util.ArrayList<>();

    @Builder.Default
    private String description = "";

    @Builder.Default
    private List<String> imageUrl = new java.util.ArrayList<>();

    private Date createdAt;

    private Date updatedAt;

    protected TypeBus(Parcel in) {
        id = in.readString();
        name = in.readString();
        code = in.readString();
        model = in.readString();
        if (in.readByte() == 0) {
            seats = null;
        } else {
            seats = in.readInt();
        }
        features = in.createStringArrayList();
        description = in.readString();
        imageUrl = in.createStringArrayList();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(model);
        if (seats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(seats);
        }
        dest.writeStringList(features);
        dest.writeString(description);
        dest.writeStringList(imageUrl);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TypeBus> CREATOR = new Creator<TypeBus>() {
        @Override
        public TypeBus createFromParcel(Parcel in) {
            return new TypeBus(in);
        }

        @Override
        public TypeBus[] newArray(int size) {
            return new TypeBus[size];
        }
    };
} 
