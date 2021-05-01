package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Products(View view) {
        Intent intentProducts = new Intent(HomeActivity.this, ProductsListActivity.class);
        startActivity(intentProducts);
    }

    public void MyAccount(View view) {
        Intent intentAccount = new Intent(HomeActivity.this, MyAccountActivity.class);
        startActivity(intentAccount);
    }

    public void MyProducts(View view) {
        Intent intentProduct = new Intent(HomeActivity.this, MyProductsActivity.class);
        startActivity(intentProduct);
    }

    public void Logout(View view) {

        String STORAGE_NAME = "DATA";
        SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
        preferences.edit().clear().commit();

        //Log.v("Home", "all"+preferences.getAll());

        Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }
}