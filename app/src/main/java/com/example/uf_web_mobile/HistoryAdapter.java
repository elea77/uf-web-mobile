package com.example.uf_web_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.uf_web_mobile.models.AccountResult;
import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class HistoryAdapter extends ArrayAdapter<History>  {
    private int resId;
    private String userToken;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://ufweb-backend-grp1.herokuapp.com/";



    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<History> objects, String token) {
        super(context, resource, objects);

        resId = resource;
        userToken = token;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);



        // déclaration du ViewHolder
        HistoryAdapter.ViewHolder myViewHolder;

        if(convertView == null) {
            myViewHolder = new ViewHolder(); //instance

            convertView = LayoutInflater.from(getContext()).inflate(resId, null);

            myViewHolder.dateView = convertView.findViewById(R.id.dateView);
            myViewHolder.priceView = convertView.findViewById(R.id.priceView);
            myViewHolder.userView = convertView.findViewById(R.id.userView);

            convertView.setTag(myViewHolder);

        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }

        History item = getItem(position);


        myViewHolder.dateView.setText(item.getDateH()+" "+item.getTimeH());
        myViewHolder.priceView.setText(item.getPriceH()+" €");


        // get user email
        Call<AccountResult> call = retrofitInterface.getUserById(userToken, item.getUser());


        call.enqueue(new Callback<AccountResult>() {
            @Override
            public void onResponse(Call<AccountResult> call, Response<AccountResult> response) {
                if(response.isSuccessful()) {


                    Log.v("User", "Results"+ response.body());

                    AccountResult user = response.body();

                    // mise à jour des informations
                    myViewHolder.userView.setText(user.getEmail());


                }
            }

            @Override
            public void onFailure(Call<AccountResult> call, Throwable t) {
                Log.v("User", "Error");

            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView dateView;
        TextView priceView;
        TextView userView;
    }
}
