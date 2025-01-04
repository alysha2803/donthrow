package com.example.donthrow;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import com.example.donthrow.models.Cart;
import com.example.donthrow.models.Cart_Firebase;

import java.util.List;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView totalValueTextView = view.findViewById(R.id.total_value); // Assuming this is the TextView for total price

        Cart_Firebase.fetchCartWithDetails(new Cart_Firebase.CartDataCallback() {
            @Override
            public void onCartFetched(List<Cart> cartItems, double totalPrice) {
                Log.d(TAG, "Cart fetched successfully with size: " + cartItems.size());
                MyAdapterCart adapter = new MyAdapterCart(requireContext(), cartItems);
                recyclerView.setAdapter(adapter);

                // Update total price dynamically
                String totalPriceText = "RM " + String.format("%.2f", totalPrice); // Format to 2 decimal places
                totalValueTextView.setText(totalPriceText);
            }

            @Override
            public void onCartFetchFailed(Exception exception) {
                Log.e(TAG, "Failed to fetch cart: ", exception);
            }
        });

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
