package com.example.admin.whereareyou.cartlist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.whereareyou.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends ArrayAdapter<CartInfo> {
    Context context;
    int resource;
    public CartAdapter(@NonNull Context context, int resource, @NonNull List<CartInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource, null);
        ((ImageView)convertView.findViewById(R.id.list_image)).setImageResource(getItem(position).getList_image());
        ((TextView)convertView.findViewById(R.id.list_category)).setText(getItem(position).getList_category());
        ((TextView)convertView.findViewById(R.id.list_name)).setText(getItem(position).getList_name());
        ((TextView)convertView.findViewById(R.id.list_pricetext)).setText(getItem(position).getList_pricetext());
        return convertView;
    }
}
