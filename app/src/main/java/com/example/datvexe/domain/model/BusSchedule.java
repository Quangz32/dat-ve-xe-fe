package com.example.datvexe.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class BusSchedule implements Parcelable {

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

    // Parcelable implementation
    protected BusSchedule(Parcel in) {
        id = in.readString();
        busOperator = in.readString();
        tripCode = in.readString();
        route = in.readString();
        if (in.readByte() == 0) {
            timeRoute = null;
        } else {
            timeRoute = in.readInt();
        }
        price = in.readDouble();
        long tmpDate = in.readLong();
        date = tmpDate == -1 ? null : new Date(tmpDate);
        timeStart = in.readString();
        benXeKhoiHanh = in.readString();
        timeEnd = in.readString();
        benXeDichDen = in.readString();
        if (in.readByte() == 0) {
            availableSeats = null;
        } else {
            availableSeats = in.readInt();
        }
        seatSelected = in.createStringArrayList();
        status = in.readString();
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        busOperatorDetail = in.readParcelable(BusOperators.class.getClassLoader());
        benXeKhoiHanhDetail = in.readParcelable(BusStation.class.getClassLoader());
        benXeDichDenDetail = in.readParcelable(BusStation.class.getClassLoader());
        busName = in.readString();
        busInfo = in.readString();
        departureTime = in.readString();
        departureLocation = in.readString();
        duration = in.readString();
        arrivalTime = in.readString();
        arrivalLocation = in.readString();
        formattedPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(busOperator);
        dest.writeString(tripCode);
        dest.writeString(route);
        if (timeRoute == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(timeRoute);
        }
        dest.writeDouble(price);
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeString(timeStart);
        dest.writeString(benXeKhoiHanh);
        dest.writeString(timeEnd);
        dest.writeString(benXeDichDen);
        if (availableSeats == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableSeats);
        }
        dest.writeStringList(seatSelected);
        dest.writeString(status);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeParcelable(busOperatorDetail, flags);
        dest.writeParcelable(benXeKhoiHanhDetail, flags);
        dest.writeParcelable(benXeDichDenDetail, flags);
        dest.writeString(busName);
        dest.writeString(busInfo);
        dest.writeString(departureTime);
        dest.writeString(departureLocation);
        dest.writeString(duration);
        dest.writeString(arrivalTime);
        dest.writeString(arrivalLocation);
        dest.writeString(formattedPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusSchedule> CREATOR = new Creator<BusSchedule>() {
        @Override
        public BusSchedule createFromParcel(Parcel in) {
            return new BusSchedule(in);
        }

        @Override
        public BusSchedule[] newArray(int size) {
            return new BusSchedule[size];
        }
    };

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

    public BusOperators getBusOperator() {
        return busOperatorDetail;
    }
} 
