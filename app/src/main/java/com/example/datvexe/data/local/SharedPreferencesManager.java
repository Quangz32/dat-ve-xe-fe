package com.example.datvexe.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "DatVeXePrefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // Keys for bus search params
    private static final String KEY_FROM_STATION_ID = "from_station_id";
    private static final String KEY_FROM_STATION_NAME = "from_station_name";
    private static final String KEY_TO_STATION_ID = "to_station_id";
    private static final String KEY_TO_STATION_NAME = "to_station_name";
    private static final String KEY_TRAVEL_DATE = "travel_date";
    private static final String KEY_PASSENGER_COUNT = "passenger_count";


    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearUserData() {
        editor.clear();
        editor.apply();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void saveUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public void savePassword(String password) {
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    // Save bus search parameters
    public void saveBusSearchParams(String fromStationId, String fromStationName,
                                 String toStationId, String toStationName,
                                 String travelDate, String passengerCount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FROM_STATION_ID, fromStationId);
        editor.putString(KEY_FROM_STATION_NAME, fromStationName);
        editor.putString(KEY_TO_STATION_ID, toStationId);
        editor.putString(KEY_TO_STATION_NAME, toStationName);
        editor.putString(KEY_TRAVEL_DATE, travelDate);
        editor.putString(KEY_PASSENGER_COUNT, passengerCount);
        editor.apply();
    }

    // Get from station ID
    public String getFromStationId() {
        return sharedPreferences.getString(KEY_FROM_STATION_ID, null);
    }

    // Get from station name
    public String getFromStationName() {
        return sharedPreferences.getString(KEY_FROM_STATION_NAME, null);
    }

    // Get to station ID
    public String getToStationId() {
        return sharedPreferences.getString(KEY_TO_STATION_ID, null);
    }

    // Get to station name
    public String getToStationName() {
        return sharedPreferences.getString(KEY_TO_STATION_NAME, null);
    }

    // Get travel date
    public String getTravelDate() {
        return sharedPreferences.getString(KEY_TRAVEL_DATE, null);
    }

    // Get passenger count
    public String getPassengerCount() {
        return sharedPreferences.getString(KEY_PASSENGER_COUNT, null);
    }

    // Get all bus search parameters as a bundle
    public Bundle getBusSearchParams() {
        Bundle bundle = new Bundle();
        bundle.putString("fromStationId", getFromStationId());
        bundle.putString("fromStationName", getFromStationName());
        bundle.putString("toStationId", getToStationId());
        bundle.putString("toStationName", getToStationName());
        bundle.putString("date", getTravelDate());
        bundle.putString("passengerCount", getPassengerCount());
        return bundle;
    }

    // Clear bus search parameters
    public void clearBusSearchParams() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_FROM_STATION_ID);
        editor.remove(KEY_FROM_STATION_NAME);
        editor.remove(KEY_TO_STATION_ID);
        editor.remove(KEY_TO_STATION_NAME);
        editor.remove(KEY_TRAVEL_DATE);
        editor.remove(KEY_PASSENGER_COUNT);
        editor.apply();
    }
}
