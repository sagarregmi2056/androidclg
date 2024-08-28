package com.example.wastepickerapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> Register(
            @Field("username") String username,

            @Field("email")String email,


            @Field("password")String password,
            @Field("confirm_password") String confirm_password

    );
}
