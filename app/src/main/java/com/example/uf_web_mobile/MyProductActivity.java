package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uf_web_mobile.models.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProductActivity extends AppActivity {

    private TextView titleView;
    private ImageView imageUrlView;
    private TextView priceView;
    private TextView dateView;
    private TextView timeView;
    private TextView descriptionView;

    private TextView maxAuctionView;

    private Product item;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        titleView = findViewById(R.id.titleView);
        imageUrlView = findViewById(R.id.imageUrlView);
        priceView = findViewById(R.id.priceView);
        dateView = findViewById(R.id.dateView);
        timeView = findViewById(R.id.timeView);
        descriptionView = findViewById(R.id.descriptionView);

        // récupération de l'objet
        if(getIntent().getExtras() != null) {
            item = (Product) getIntent().getExtras().get("object");

            // mise à jour des informations
            titleView.setText(item.getTitle());
            priceView.setText(item.getPrice() + "€");

            dateView.setText(item.getDate());
            timeView.setText(item.getTime());
            descriptionView.setText(item.getDescription());

            Picasso.get()
                    .load(item.getImageUrl())
                    .into(imageUrlView);
        }

        findViewById(R.id.acceptSale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleConfirmDialog();
            }
        });

        findViewById(R.id.deleteProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDeleteDialog();
            }
        });
    }

    private void handleDeleteDialog() {
        View view = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);

        maxAuctionView = view.findViewById(R.id.maxAuction);
        maxAuctionView.setText("Êtes-vous sûr de vouloir supprimer le produit ?");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(view).show();

        // Get user token
        String STORAGE_NAME = "DATA";
        SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
        String token = preferences.getString("token", "");


        Button confirmBtn = view.findViewById(R.id.confirm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getExtras() != null) {

                    // Get product id
                    item = (Product) getIntent().getExtras().get("object");
                    String id = item.get_id();

                    Call<Void> call = retrofitInterface.deleteProduct(token, id);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {

                                Intent intentProduct = new Intent(MyProductActivity.this, MyProductsListActivity.class);
                                startActivity(intentProduct);
                                finish();
                            } else {
                                Toast.makeText(MyProductActivity.this, response.message(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MyProductActivity.this, t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    private void handleConfirmDialog() {
        View view = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);


        maxAuctionView = view.findViewById(R.id.maxAuction);
        maxAuctionView.setText("Êtes-vous sûr de vouloir accepter la vente ?");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(view).show();

        Button confirmBtn = view.findViewById(R.id.confirm);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getExtras() != null) {

                    // Get user data
                    String STORAGE_NAME = "DATA";
                    SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
                    String token = preferences.getString("token", "");

                    // Get product data
                    item = (Product) getIntent().getExtras().get("object");
                    String id = item.get_id();

                    HashMap<String, String> map = new HashMap<>();

                    map.put("status", "false");

                    Call<Void> call = retrofitInterface.modifyProduct(token, map, id);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(MyProductActivity.this,
                                        "Succès", Toast.LENGTH_LONG).show();

                                Intent intentMyProducts = new Intent(MyProductActivity.this, MySoldProductActivity.class);
                                intentMyProducts.putExtra("object", item);
                                startActivity(intentMyProducts);
                                finish();


                            } else {
                                Log.v("Product", "Error:"+response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MyProductActivity.this, t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.v("Product", "Error adding product");

                        }
                    });
                }
            }
        });

    }

    public void auctionHistory(View view) {
        if(getIntent().getExtras() != null) {
            item = (Product) getIntent().getExtras().get("object");
            String id = item.get_id();

            Intent intentHistory = new Intent(MyProductActivity.this, HistoryProductActivity.class);
            intentHistory.putExtra("id", id);
            startActivity(intentHistory);
        }
    }

}