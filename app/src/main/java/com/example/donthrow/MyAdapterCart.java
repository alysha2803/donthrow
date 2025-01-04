package com.example.donthrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterCart extends RecyclerView.Adapter<MyAdapterCart.CartViewHolder> {

    private Context context;
    private List<Cart> cartList;

    public MyAdapterCart(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_view, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        holder.nameTextView.setText(cart.getName());
        holder.priceTextView.setText(String.format("$%.2f", cart.getPrice()));
        holder.quantityTextView.setText(String.format("Qty: %d", cart.getQuantity()));
        Picasso.get().load(cart.getImage()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, quantityTextView;
        ImageView productImageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.product_name);
            priceTextView = itemView.findViewById(R.id.product_price);
            quantityTextView = itemView.findViewById(R.id.product_quantity);
            productImageView = itemView.findViewById(R.id.product_image);
        }
    }
}
