package com.example.datvexe.presentation.model;

public class PopularRoute {
    private String routeName;
    private String price;
    private String duration;
    private int bannerResId;

    public PopularRoute(String routeName, String price, String duration, int bannerResId) {
        this.routeName = routeName;
        this.price = price;
        this.duration = duration;
        this.bannerResId = bannerResId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getBannerResId() {
        return bannerResId;
    }

    public void setBannerResId(int bannerResId) {
        this.bannerResId = bannerResId;
    }
} 