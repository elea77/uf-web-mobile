package com.example.uf_web_mobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.Product;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppActivity {

    private TextView titleView;
    private ImageView imageUrlView;
    private TextView priceView;
    private TextView dateView;
    private TextView timeView;
    private TextView descriptionView;

    private Product item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

        builder.setView(view).show();

    }

    public void auctionHistory(View view) {
        if(getIntent().getExtras() != null) {
            item = (Product) getIntent().getExtras().get("object");
            String id = item.get_id();

            Intent intentHistory = new Intent(ProductActivity.this, HistoryProductActivity.class);
            intentHistory.putExtra("id", id);
            startActivity(intentHistory);
        }
    }
}