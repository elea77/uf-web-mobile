package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uf_web_mobile.models.AccountResult;
import com.example.uf_web_mobile.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAccountActivity extends AppActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";

    private TextView emailView;
    private TextView firstNameView;
    private TextView lastNameView;
    private TextView phoneView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        emailView = findViewById(R.id.emailView);
        firstNameView = findViewById(R.id.firstNameView);
        lastNameView = findViewById(R.id.lastNameView);
        phoneView = findViewById(R.id.phoneView);


        String STORAGE_NAME = "DATA";
        SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
        String id = preferences.getString("id", "");
        String token = preferences.getString("token", "");

        Call<AccountResult> call = retrofitInterface.getUserById(token, id);


        call.enqueue(new Callback<AccountResult>() {
            @Override
            public void onResponse(Call<AccountResult> call, Response<AccountResult> response) {
                if(response.isSuccessful()) {


                    Log.v("User", "Results"+ response.body());

                    AccountResult user = response.body();

                    // mise ?? jour des informations
                    emailView.setText(user.getEmail());
                    firstNameView.setText(user.getFirstName());
                    lastNameView.setText(user.getLastName());
                    phoneView.setText(user.getPhone());


                } else {
                    Toast.makeText(MyAccountActivity.this,
                            "Error", Toast.LENGTH_LONG).show();

                    Log.v("User", "Errors:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<AccountResult> call, Throwable t) {
                Toast.makeText(MyAccountActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.v("User", "Error sign up");

            }
        });

    }
}