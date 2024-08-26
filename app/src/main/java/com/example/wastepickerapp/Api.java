package com.example.wastepickerapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface Api {
    @POST("Register")
    Call<ResponseBody> RegisterUser(
            @Field("username") String username,

            @Field("email")String email,


            @Field("password")String password,
            @Field("confirm_password") String confirm_password

    );
}
