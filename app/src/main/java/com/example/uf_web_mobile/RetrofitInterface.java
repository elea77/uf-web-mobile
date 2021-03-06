package com.example.uf_web_mobile;

import android.content.SharedPreferences;

import com.example.uf_web_mobile.models.AccountResult;
import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.LoginResult;
import com.example.uf_web_mobile.models.Product;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.Context.MODE_PRIVATE;

public interface RetrofitInterface {

    @POST("/api/v1/login")
    Call<LoginResult> executeLogin (@Body HashMap<String, String> map);

    @POST("/api/v1/users")
    Call<Void> executeRegister (@Body HashMap<String, String> map);

    @GET("/api/v1/products")
    Call<List<Product>> getProducts ();

    @GET("/api/v1/users/{id}")
    Call<AccountResult> getUserById(@Header("authorization") String token, @Path("id") String id);

    @POST("/api/v1/products")
    Call<Void> executeProduct (@Header("authorization") String token, @Body HashMap<String, String> map);

    @GET("/api/v1/products/user/{id}")
    Call<List<Product>> getProductsByUserId (@Header("authorization") String token, @Path("id") String id);

    @GET("/api/v1/history/product/{id}")
    Call<List<History>> getProductHistory (@Header("authorization") String token, @Path("id") String id);

    @DELETE("/api/v1/products/{id}")
    Call<Void> deleteProduct (@Header("authorization") String token, @Path("id") String id);

    @POST("/api/v1/history")
    Call<Void> executeOverbid (@Header("authorization") String token, @Body HashMap<String, String> map);

    @PUT("/api/v1/products/{id}")
    Call<Void> modifyProduct (@Header("authorization") String token, @Body HashMap<String, String> map, @Path("id") String id);
}
