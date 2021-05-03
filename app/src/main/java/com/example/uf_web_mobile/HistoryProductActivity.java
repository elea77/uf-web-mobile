package com.example.uf_web_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryProductActivity extends AppActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";

    private ListView listViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_product);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        listViewData = findViewById(R.id.listViewData);


        if(getIntent().getExtras() != null) {

            // Get product id
            String id = (String) getIntent().getExtras().get("id");

            // Get user data
            String STORAGE_NAME = "DATA";
            SharedPreferences preferences = getSharedPreferences(STORAGE_NAME,MODE_PRIVATE);
            String token = preferences.getString("token", "");

            Call<List<History>> call = retrofitInterface.getProductHistory(token,id);


            call.enqueue(new Callback<List<History>>() {
                @Override
                public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                    if(response.isSuccessful()) {

                        List<History> historyList = response.body();

                        int length = historyList.size();

                        for (int i = length; i < length; i++) {

                            historyList.add(new History(
                                    historyList.get(i).getPriceH(),
                                    historyList.get(i).getDateH(),
                                    historyList.get(i).getTimeH()
                            ));

                        }

                        listViewData.setAdapter(
                                new HistoryAdapter(
                                        HistoryProductActivity.this,
                                        R.layout.item_history,
                                        historyList
                                )
                        );

                    } else {
                        Toast.makeText(HistoryProductActivity.this,
                                "An error was encountered", Toast.LENGTH_LONG).show();
                        Log.v("Error", "An error was encountered :"+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<History>> call, Throwable t) {
                    Toast.makeText(HistoryProductActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}