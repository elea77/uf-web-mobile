package com.example.uf_web_mobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("token")
    @Expose
    private String token;


    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }



}
