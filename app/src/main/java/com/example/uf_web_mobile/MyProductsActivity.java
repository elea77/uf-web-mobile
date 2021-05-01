package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyProductsActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
    }

    public void addProduct(View view) {
        Intent intentAddProduct = new Intent(MyProductsActivity.this, AddProductActivity.class);
        startActivity(intentAddProduct);
    }
}