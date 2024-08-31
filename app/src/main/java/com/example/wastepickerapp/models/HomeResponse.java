package com.example.wastepickerapp.models;

public class HomeResponse {
    public String title;
    public Data data;
    @Override
    public String toString() {
        return "HomeResponse{" +
                "title='" + title + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public User user;
        public Schedule schedules;

        @Override
        public String toString() {
            return "Data{" +
                    "user=" + user +
                    ", schedules=" + schedules +
                    '}';
        }
        // Other fields as needed
    }
    public static class User {
        public String username;
        // Other user fields if needed
    }
    public static class Schedule {
        public String date;
        public String arrival_time;
        // Other schedule fields if needed
    }
}
