package com.example.uf_web_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppActivity {

    private TextView titleView;
    private ImageView imageUrlView;
    private TextView priceView;
    private TextView dateView;
    private TextView timeView;
    private TextView descriptionView;

    private Product item;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


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

        findViewById(R.id.overbid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOverbidDialog();
            }
        });
    }

    private void handleOverbidDialog() {
        View view = getLayoutInflater().inflate(R.layout.overbid_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setView(view).show();

        Button overbidBtn = view.findViewById(R.id.overbid);

        final EditText priceEdit = view.findViewById(R.id.priceEdit);

        overbidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("TEST", priceEdit.getText().toString());

                // Get user data
                String STORAGE_NAME = "DATA";
                SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
                String id = preferences.getString("id", "");
                String token = preferences.getString("token", "");


                // Get datetime now
                Date now = new Date();
                // date
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/YYYY");
                String dateResult = dateFormatter.format(now);
                // time
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
                String timeResult = timeFormatter.format(now);

                // Get product data
                if(getIntent().getExtras() != null) {

                    // Get product id
                    item = (Product) getIntent().getExtras().get("object");
                    String idProduct = item.get_id();


                    HashMap<String, String> map = new HashMap<>();

                    map.put("priceH", priceEdit.getText().toString());
                    map.put("dateH", dateResult);
                    map.put("timeH", timeResult);
                    map.put("user", id);
                    map.put("product", idProduct);

                    Call<Void> call = retrofitInterface.executeOverbid(token, map);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(ProductActivity.this,
                                        "Succès", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(ProductActivity.this, response.message(),
                                        Toast.LENGTH_LONG).show();

                            }

                            // Rechargement de la page
                            Intent intentProduct;
                            intentProduct = new Intent(ProductActivity.this, ProductsListActivity.class);

                            //passage de l'objet produit
                            //intentProduct.putExtra("object", item);
                            startActivity(intentProduct);

                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ProductActivity.this, t.getMessage(),
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

            Intent intentHistory = new Intent(ProductActivity.this, HistoryProductActivity.class);
            intentHistory.putExtra("id", id);
            startActivity(intentHistory);
            finish();
        }
    }
}