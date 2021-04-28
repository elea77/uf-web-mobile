package com.example.uf_web_mobile;

import com.example.uf_web_mobile.models.AccountResult;
import com.example.uf_web_mobile.models.LoginResult;
import com.example.uf_web_mobile.models.Product;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/api/v1/login")
    Call<LoginResult> executeLogin (@Body HashMap<String, String> map);

    @POST("/api/v1/users")
    Call<Void> executeRegister (@Body HashMap<String, String> map);

    @GET("/api/v1/products")
    Call<List<Product>> getProducts ();

    @Headers({
            "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwODgwZGQ1MmM3ZmUzMDAyY2VhMmJmMCIsImlzQWRtaW4iOmZhbHNlLCJpYXQiOjE2MTk2MDgyMDYsImV4cCI6MTYxOTY5NDYwNn0.OAmy6ukCpfeHWab0tuGerAnjowBO2PfZ8Aypc1IY5Kc"
    })
    @GET("/api/v1/users/{id}")
    //Call<AccountResult> getUserById(@Query("id") Integer id);
    Call<AccountResult> getUserById(@Path("id") String id);

}
