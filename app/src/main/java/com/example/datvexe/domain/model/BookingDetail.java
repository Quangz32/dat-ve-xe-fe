package com.example.datvexe.domain.model;

public class BookingDetail {
    private String code;
    private String route;
    private String customerName;
    private String seat;
    private String departureTime;
    private String price;
    private String status;
    private String paymentMethod;

    public BookingDetail(String code, String route, String customerName, String seat, String departureTime, String price, String status, String paymentMethod) {
        this.code = code;
        this.route = route;
        this.customerName = customerName;
        this.seat = seat;
        this.departureTime = departureTime;
        this.price = price;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public String getCode() { return code; }
    public String getRoute() { return route; }
    public String getCustomerName() { return customerName; }
    public String getSeat() { return seat; }
    public String getDepartureTime() { return departureTime; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
} 