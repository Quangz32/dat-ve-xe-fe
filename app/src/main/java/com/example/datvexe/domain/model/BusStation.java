package com.example.datvexe.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class BusStation implements Parcelable {

    @SerializedName("_id")
    private String id;

    @NotBlank(message = "Mã bến xe không được để trống")
    private String maBenXe;

    @NotBlank(message = "Tên bến xe không được để trống")
    private String tenBenXe;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;
    
    // Convenience field for UI display
    private String name;
    
    // Getter for name that returns tenBenXe if name is null
    public String getName() {
        return name != null ? name : tenBenXe;
    }
    
    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    protected BusStation(Parcel in) {
        id = in.readString();
        maBenXe = in.readString();
        tenBenXe = in.readString();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        long tmpDeletedAt = in.readLong();
        deletedAt = tmpDeletedAt == -1 ? null : new Date(tmpDeletedAt);
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(maBenXe);
        dest.writeString(tenBenXe);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeLong(deletedAt != null ? deletedAt.getTime() : -1);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusStation> CREATOR = new Creator<BusStation>() {
        @Override
        public BusStation createFromParcel(Parcel in) {
            return new BusStation(in);
        }

        @Override
        public BusStation[] newArray(int size) {
            return new BusStation[size];
        }
    };
} 
