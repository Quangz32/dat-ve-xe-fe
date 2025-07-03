package com.example.datvexe.data.local;

import com.example.datvexe.data.remote.dto.LocationTripDto;
import com.example.datvexe.domain.model.BusStation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TripService {
    private static TripService instance;
    private List<LocationTripDto> locations = new ArrayList<>();
    private Map<String, BusStation> stationMap = new HashMap<>();

    private TripService() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized TripService getInstance() {
        if (instance == null) {
            instance = new TripService();
        }
        return instance;
    }

    public void setLocations(List<LocationTripDto> locations) {
        this.locations = locations;
        updateStationMap();
    }

    public List<LocationTripDto> getLocations() {
        return locations;
    }

    private void updateStationMap() {
        stationMap.clear();
        for (LocationTripDto location : locations) {
            if (location.getBenXe() != null) {
                for (BusStation station : location.getBenXe()) {
                    // Map by ID
                    stationMap.put(station.getId(), station);
                    // Map by name (tenBenXe)
                    stationMap.put(station.getTenBenXe(), station);
                }
            }
        }
    }

    /**
     * Find station by name or ID
     * @param nameOrId Name or ID of the station
     * @return BusStation object or null if not found
     */
    public BusStation findStation(String nameOrId) {
        return stationMap.get(nameOrId);
    }
} 
