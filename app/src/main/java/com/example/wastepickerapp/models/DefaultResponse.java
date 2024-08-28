package com.example.wastepickerapp.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("error")
    @Nullable
    private  String err;
    @SerializedName("message")
    @Nullable
    private  String msg;
    //    creating a constructur for intializing values
    public DefaultResponse(String err,String msg){
        this.err= err;
        this.msg   = msg;


    }

    public String getErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }
}
