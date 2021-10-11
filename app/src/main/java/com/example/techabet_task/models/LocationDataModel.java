package com.example.techabet_task.models;

public class LocationDataModel {
    String timeStr, longStr, latStr;

    public LocationDataModel(String timeStr, String longStr, String latStr) {
        this.timeStr = timeStr;
        this.longStr = longStr;
        this.latStr = latStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public String getLongStr() {
        return longStr;
    }

    public String getLatStr() {
        return latStr;
    }
}
