package com.example.donthrow;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderCart extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, priceView;

    public MyViewHolderCart(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        nameView = itemView.findViewById(R.id.product_name);
        priceView = itemView.findViewById(R.id.product_price);
    }
}
