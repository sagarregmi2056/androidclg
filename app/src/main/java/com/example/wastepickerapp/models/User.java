package com.example.wastepickerapp.models;

public class User {
    public String id;
    public String username;
    public String email;
    public String otp;
    public String otp_expires_at;
    public String fcm_id;
    public String device_id;
    public String area_id;
    public String token;
    public String created_at;
    public String updated_at;
    public String province;
    public String district;
    public String metropolitan;
    public String ward_number;
    public String street_name;
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Include other fields if needed
                '}';
    }
}
