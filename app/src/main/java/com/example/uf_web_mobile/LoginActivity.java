package com.example.uf_web_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); //fermeture de l'activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });
    }

    private void handleLoginDialog() {

        Button loginBtn = findViewById(R.id.login);
        EditText emailEdit = findViewById(R.id.emailEdit);
        EditText passwordEdit = findViewById(R.id.passwordEdit);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if(response.isSuccessful()) {

                            LoginResult result = response.body();

                            Log.v("User", "token:"+ result.getToken());

                            Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intentHome);
                            finish();
                        }
                        else {

                            try {
                                Toast.makeText(LoginActivity.this, "Error: "+response.errorBody().string(),
                                        Toast.LENGTH_LONG).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();

                        Log.v("User", "Error");

                    }
                });
            }
        });

    }

    public void register(View view) {
        Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentRegister);
    }
}