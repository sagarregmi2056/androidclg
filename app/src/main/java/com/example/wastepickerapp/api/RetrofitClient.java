package com.example.wastepickerapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static  final String BASE_URL = "https://wastepicker.techenfield.com/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public  static synchronized  RetrofitClient getInstance(){
        if(mInstance==null){
            mInstance = new RetrofitClient();

        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
