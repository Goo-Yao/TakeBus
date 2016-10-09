package com.rdc.takebus.entity;

import java.util.List;

/**
 * Created by ZZH on 2016/5/22.
 */
public class BusResultData {
    private List<BusLocation> buses;
    private List<BusStationTag> stations;

    public List<BusLocation> getBuses() {
        return buses;
    }

    public void setBuses(List<BusLocation> buses) {
        this.buses = buses;
    }

    public List<BusStationTag> getStations() {
        return stations;
    }

    public void setStations(List<BusStationTag> stations) {
        this.stations = stations;
    }
}
