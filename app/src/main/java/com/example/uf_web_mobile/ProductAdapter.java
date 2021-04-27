package com.example.uf_web_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.uf_web_mobile.models.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private int resId;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);

        resId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // déclaration du ViewHolder
        ViewHolder myViewHolder;

        if(convertView == null) {
            myViewHolder = new ViewHolder(); //instance

            convertView = LayoutInflater.from(getContext()).inflate(resId, null);


            myViewHolder.titleView = convertView.findViewById(R.id.titleView);
            //myViewHolder.priceView = convertView.findViewById(R.id.priceView);

            convertView.setTag(myViewHolder);

        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }

        Product item = getItem(position);


        myViewHolder.titleView.setText(item.getTitle());
        //myViewHolder.priceView.setText(item.getPrice());

        return convertView;
    }

    class ViewHolder {
        TextView titleView;
        //TextView priceView;

    }
}
