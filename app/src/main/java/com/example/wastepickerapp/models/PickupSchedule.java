package com.example.wastepickerapp.models;

public class PickupSchedule {
    private int id;
    private String date;
    private String arrivalTime;
    private int areaId;

    public PickupSchedule(int id, String date, String arrivalTime, int areaId) {
        this.id = id;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.areaId = areaId;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getAreaId() {
        return areaId;
    }
}
