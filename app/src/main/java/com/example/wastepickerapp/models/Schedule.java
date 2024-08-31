package com.example.wastepickerapp.models;

public class Schedule {
    public String id;
    public String date;
    public String arrival_time;
    public String area_id;
    public String created_at;
    public String updated_at;
    public String status;
    @Override
    public String toString() {
        return "Schedule{" +
                "date='" + date + '\'' +
                ", arrival_time='" + arrival_time + '\'' +
                // Include other fields if needed
                '}';
    }
}
