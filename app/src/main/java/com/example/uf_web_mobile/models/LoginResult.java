package com.example.uf_web_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;


    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }



}
