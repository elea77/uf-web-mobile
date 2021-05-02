package com.example.uf_web_mobile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uf_web_mobile.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductsListActivity extends AppActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";

    private ListView listViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        listViewData = findViewById(R.id.listViewData);


        Call<List<Product>> call = retrofitInterface.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {

                    List<Product> productList = response.body();

                    int length = productList.size();

                    for (int i = length; i < length; i++) {

                        productList.add(new Product(
                                productList.get(i).get_id(),
                                productList.get(i).getTitle(),
                                productList.get(i).getDescription(),
                                productList.get(i).getImageUrl(),
                                productList.get(i).getPrice(),
                                productList.get(i).getDate(),
                                productList.get(i).getTime(),
                                productList.get(i).getStatus()
                        ));

                    }

                    listViewData.setAdapter(
                            new ProductAdapter(
                                    ProductsListActivity.this,
                                    R.layout.item_product,
                                    productList
                            )
                    );


                    listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            // Objet Produit
                            Product item = productList.get(position);

                            String STORAGE_NAME = "DATA";
                            SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
                            String userId = preferences.getString("id", "");

                            String productUserId = productList.get(position).getUser();


                            Intent intentProduct;
                            if(productUserId.equals(userId)) {
                                // Intent
                                intentProduct = new Intent(ProductsListActivity.this, MyProductActivity.class);

                            } else {
                                // Intent
                                intentProduct = new Intent(ProductsListActivity.this, ProductActivity.class);
                            }

                            //passage de l'objet produit
                            intentProduct.putExtra("object", item);
                            startActivity(intentProduct);
                        }
                    });

                } else {
                    Toast.makeText(ProductsListActivity.this,
                            "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsListActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });
    }
}