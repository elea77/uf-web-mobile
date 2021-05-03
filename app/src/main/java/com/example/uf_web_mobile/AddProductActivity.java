package com.example.uf_web_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        handleAddProductDialog();

    }

    private void handleAddProductDialog() {

        Button addProductBtn = findViewById(R.id.addProduct);
        EditText titleEdit = findViewById(R.id.titleEdit);
        EditText descriptionEdit = findViewById(R.id.descriptionEdit);
        EditText priceEdit = findViewById(R.id.priceEdit);
        EditText imgUrlEdit = findViewById(R.id.imageUrlEdit);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get user data
                String STORAGE_NAME = "DATA";
                SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
                String id = preferences.getString("id", "");
                String token = preferences.getString("token", "");


                // Get datetime now
                Date now = new Date();

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY");
                String dateResult = dateFormatter.format(now);

                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
                String timeResult = timeFormatter.format(now);


                HashMap<String, String> map = new HashMap<>();

                map.put("title", titleEdit.getText().toString());
                map.put("description", descriptionEdit.getText().toString());
                map.put("price", priceEdit.getText().toString());
                map.put("imageUrl", imgUrlEdit.getText().toString());
                map.put("date", dateResult);
                map.put("time", timeResult);
                map.put("user", id);


                Call<Void> call = retrofitInterface.executeProduct(token, map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(AddProductActivity.this,
                                    "Produit ajouté avec succès", Toast.LENGTH_LONG).show();
                            Log.v("Product", "Response:"+response);

                            Intent intentMyProducts = new Intent(AddProductActivity.this, MyProductsListActivity.class);
                            startActivity(intentMyProducts);

                        } else {
                            Log.v("Product", "Error:"+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.v("Product", "Error adding product");

                    }
                });

            }
        });
    }
}