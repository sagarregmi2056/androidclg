package com.example.wastepickerapp.api;

import com.example.wastepickerapp.models.DefaultResponse;
import com.example.wastepickerapp.models.HomeResponse;
import com.example.wastepickerapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("register")
    Call<DefaultResponse> Register(
            @Field("username") String username,

            @Field("email")String email,


            @Field("password")String password,
            @Field("confirm_password") String confirm_password

    );
    @FormUrlEncoded
    @POST("login") // Ensure this path matches your server's endpoint for login
    Call<LoginResponse> Login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("logout")
    Call<DefaultResponse> logout(@Header("Authorization") String token);




    @FormUrlEncoded
    @POST("user_location")
    Call<DefaultResponse> submitUserLocation(
            @Header("Authorization") String token,
            @Field("province") String province,
            @Field("district") String district,
            @Field("metropolitan") String metropolitan,
            @Field("ward_number") String wardNumber,
            @Field("street_name") String streetName
    );

    @GET("discovery")
    Call<DefaultResponse> getDiscovery();

    @GET("event")
    Call<DefaultResponse> getEvent();

    @GET("news")
    Call<DefaultResponse> getNews();

    @GET("discovery-importances")
    Call<DefaultResponse> getDiscoveryImportances();

    @GET("discovery-process")
    Call<DefaultResponse> getDiscoveryProcess();

    @GET("event-highlights")
    Call<DefaultResponse> getEventHighlights();

    @GET("pickup-schedules")
    Call<DefaultResponse> getPickupSchedules();

    @POST("trash")
    Call<DefaultResponse> postTrash(
            @Body String jsonBody
    );

    @FormUrlEncoded
    @POST("resend")
    Call<DefaultResponse> resendOtp(
            @Field("email") String email
    );

    @GET("home")
    Call<HomeResponse> getHome(@Header("Authorization") String token);

    @GET("location-info")
    Call<DefaultResponse> getLocationInfo();
}
