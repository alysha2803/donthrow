package com.example.donthrow.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart_Firebase {

    private static final String TAG = "Cart_Firebase";

    public static void fetchCartWithDetails(CartDataCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

        if (userId == null) {
            callback.onCartFetchFailed(new Exception("User is not logged in."));
            return;
        }

        DatabaseReference cartRef = database.getReference("Registered Users").child(userId).child("cart");
        DatabaseReference productsRef = database.getReference("Products");

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot cartSnapshot) {
                if (!cartSnapshot.exists()) {
                    callback.onCartFetched(new ArrayList<>(), 0);
                    return;
                }

                List<Cart> cartItems = new ArrayList<>();
                final double[] totalPrice = {0};

                for (DataSnapshot cartItemSnapshot : cartSnapshot.getChildren()) {
                    String productId = cartItemSnapshot.getKey();
                    Integer quantity = cartItemSnapshot.child("quantity").getValue(Integer.class);

                    if (productId != null && quantity != null) {
                        productsRef.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot productSnapshot) {
                                if (productSnapshot.exists()) {
                                    String name = productSnapshot.child("name").getValue(String.class);
                                    Double price = productSnapshot.child("price").getValue(Double.class);
                                    String condition = productSnapshot.child("condition").getValue(String.class);
                                    String image = productSnapshot.child("image").getValue(String.class);

                                    Cart cartItem = new Cart(productId, name, price, condition, image, quantity);
                                    cartItems.add(cartItem);

                                    // Add price of this item to total price
                                    if (price != null) {
                                        totalPrice[0] += price * quantity;
                                    }

                                    // Notify callback once all cart items are processed
                                    if (cartItems.size() == cartSnapshot.getChildrenCount()) {
                                        callback.onCartFetched(cartItems, totalPrice[0]);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                callback.onCartFetchFailed(databaseError.toException());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCartFetchFailed(databaseError.toException());
            }
        });
    }

    public interface CartDataCallback {
        void onCartFetched(List<Cart> cartItems, double totalPrice);

        void onCartFetchFailed(Exception exception);
    }
}
