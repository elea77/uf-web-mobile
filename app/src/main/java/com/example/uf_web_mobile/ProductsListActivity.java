package com.example.uf_web_mobile;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

        List<Product> productList = new ArrayList<>();

        productList.add(new Product (
                "Produit 1",
                "Description",
                "imageUrl",
                "Price",
                "Date",
                "Status"
        ));

        productList.add(new Product (
                "Produit 2",
                "Description",
                "imageUrl",
                "Price",
                "Date",
                "Status"
        ));

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

                // Intent
                Intent intentProduct = new Intent(ProductsListActivity.this, ProductActivity.class);

                //passage de l'objet produit
                //intentDetails.putExtra("object", item);
                intentProduct.putExtra("object", item);

                startActivity(intentProduct);
            }
        });
    }
}