package com.example.uf_web_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.uf_web_mobile.models.History;
import com.example.uf_web_mobile.models.Product;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<History>  {
    private int resId;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<History> objects) {
        super(context, resource, objects);

        resId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        // déclaration du ViewHolder
        HistoryAdapter.ViewHolder myViewHolder;

        if(convertView == null) {
            myViewHolder = new ViewHolder(); //instance

            convertView = LayoutInflater.from(getContext()).inflate(resId, null);

            myViewHolder.dateView = convertView.findViewById(R.id.dateView);
            myViewHolder.priceView = convertView.findViewById(R.id.priceView);

            convertView.setTag(myViewHolder);

        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }

        History item = getItem(position);


        myViewHolder.dateView.setText(item.getDateH()+" "+item.getTimeH());
        myViewHolder.priceView.setText(item.getPriceH()+" €");

        return convertView;
    }

    class ViewHolder {
        TextView dateView;
        TextView priceView;
    }
}
