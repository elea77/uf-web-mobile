package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uf_web_mobile.models.Product;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

public class MySoldProductActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_sold_product);


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
    }

    public void auctionHistory(View view) {
        if(getIntent().getExtras() != null) {
            item = (Product) getIntent().getExtras().get("object");
            String id = item.get_id();

            Intent intentHistory = new Intent(MySoldProductActivity.this, HistoryProductActivity.class);
            intentHistory.putExtra("id", id);
            startActivity(intentHistory);
        }
    }
}