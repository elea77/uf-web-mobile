package com.example.uf_web_mobile;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/v1/login")
    Call<LoginResult> executeLogin (@Body HashMap<String, String> map);

    @POST("/api/v1/users")
    Call<Void> executeRegister (@Body HashMap<String, String> map);


}
