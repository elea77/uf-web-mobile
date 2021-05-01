package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductActivity extends AppActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // handleAddProductDialog();

    }

    private void handleAddProductDialog() {

        Button registerBtn = findViewById(R.id.register);
        EditText emailEdit = findViewById(R.id.emailEdit);
        EditText passwordEdit = findViewById(R.id.passwordEdit);
        EditText firstNameEdit = findViewById(R.id.firstNameEdit);
        EditText lastNameEdit = findViewById(R.id.lastNameEdit);
        EditText phoneEdit = findViewById(R.id.phoneEdit);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());
                map.put("firstName", firstNameEdit.getText().toString());
                map.put("lastName", lastNameEdit.getText().toString());
                map.put("phone", phoneEdit.getText().toString());


                Call<Void> call = retrofitInterface.executeRegister(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(AddProductActivity.this,
                                    "Signed up successfully", Toast.LENGTH_LONG).show();
                            Log.v("User", "Users:"+response);

                            Intent intentLogin = new Intent(AddProductActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                            finish();

                        } else {
                            Log.v("User", "Users:"+response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.v("User", "Error sign up");

                    }
                });

            }
        });
    }
}