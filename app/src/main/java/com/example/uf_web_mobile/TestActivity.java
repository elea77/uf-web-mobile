package com.example.uf_web_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegisterDialog();
            }
        });

    }

    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        EditText emailEdit = view.findViewById(R.id.emailEdit);
        EditText passwordEdit = view.findViewById(R.id.passwordEdit);

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

                        if(response.code() == 200) {


                            LoginResult result = response.body();

                            Log.v("User", "Error:"+ result);

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(TestActivity.this);
                            builder1.setTitle(result.getEmail());
                            builder1.setMessage(result.getEmail());

                            builder1.show();
                            Log.v("User", "Users:"+response.message());


                            Intent intentHome = new Intent(TestActivity.this, HomeActivity.class);
                            startActivity(intentHome);

                        } else if (response.code() == 404) {
                            Toast.makeText(TestActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_LONG).show();

                            Log.v("User", "Error : "+response.errorBody());
                        } else {
                            Toast.makeText(TestActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_LONG).show();
                            LoginResult result = response.body();

                            Log.v("User", "Response:"+ response.body());

                            //Log.v("User", "Users:"+response.body());


                            //Log.v("Response errorBody", String.valueOf(response.errorBody()));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(TestActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();

                        Log.v("User", "Error");

                    }
                });
            }
        });
    }

    private void handleRegisterDialog() {

        View view = getLayoutInflater().inflate(R.layout.register_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button registerBtn = view.findViewById(R.id.register);
        EditText emailEdit = view.findViewById(R.id.emailEdit);
        EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeRegister(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200) {
                            Toast.makeText(TestActivity.this,
                                    "Signed up successfully", Toast.LENGTH_LONG).show();
                            Log.v("User", "Users:"+response);

                        } else if (response.code() == 400) {
                            Toast.makeText(TestActivity.this,
                                    "Already registered", Toast.LENGTH_LONG).show();
                            Log.v("User", "Users:"+response.errorBody());

                        } else {
                            Log.v("User", "Users:"+response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(TestActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.v("User", "Error sign up");

                    }
                });

            }
        });

    }
}