package com.example.datvexe.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.util.Objects;

public class Seat implements Parcelable {
    private String id;
    private int price;
    private boolean isSelected;
    private boolean isBooked;

    public Seat(String id, int price, boolean isBooked) {
        this.id = id;
        this.price = price;
        this.isBooked = isBooked;
        this.isSelected = false;
    }

    protected Seat(Parcel in) {
        id = in.readString();
        price = in.readInt();
        isSelected = in.readByte() != 0;
        isBooked = in.readByte() != 0;
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getSeatNumber() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "Seat{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", isSelected=" + isSelected +
                ", isBooked=" + isBooked +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(price);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isBooked ? 1 : 0));
    }
} 
