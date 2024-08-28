package com.example.wastepickerapp.api;

import com.example.wastepickerapp.models.DefaultResponse;
import com.example.wastepickerapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
