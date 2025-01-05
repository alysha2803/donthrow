package com.example.donthrow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitesFragment extends Fragment {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_sites, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the store list
        storeList = new ArrayList<>();

        // Get the latitude and longitude from the arguments
        if (getArguments() != null) {
            double latitude = getArguments().getDouble("latitude");
            double longitude = getArguments().getDouble("longitude");

            // load stores and their ratings
            loadStoresWithRatings();
        }

        // Set the adapter
        storeAdapter = new StoreAdapter(storeList, this);
        recyclerView.setAdapter(storeAdapter);

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void loadStoresWithRatings() {
        // For demonstration, we are adding hardcoded stores nearby the given location
        storeList.add(new Store("Thrift Store 1", "Address 1"));
        storeList.add(new Store("Recycling Center 1", "Address 2"));
        storeList.add(new Store("Secondhand Shop 1", "Address 3"));
        storeList.add(new Store("Bundle Shop 1", "Address 4"));
        // Add more stores as needed

        // load ratings for each store
        for (Store store : storeList) {
            loadStoreRating(store);
        }
    }

    private void loadStoreRating(Store store) {
        db.collection("ratings")
                .whereEqualTo("storeID", store.getID())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                        float totalRating = 0;
                        int count = 0;

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            totalRating += doc.getDouble("rating").floatValue();
                            count++;
                        }

                        float averageRating = count > 0 ? totalRating / count : 0;
                        store.setRating(averageRating);
                        storeAdapter.notifyDataSetChanged();
                });
    }
    public void showRatingDialog(String storeId, String storeName) {
        View dialogView = getLayoutInflater().inflate(R.layout.centre_rating_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        AlertDialog dialog = builder.setView(dialogView)
                .setTitle("Rate " + storeName)
                .create();

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        EditText feedbackEditText = dialogView.findViewById(R.id.feedbackEditText);
        Button submitRatingBtn = dialogView.findViewById(R.id.submitRatingBtn);

        submitRatingBtn.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = feedbackEditText.getText().toString();

            if (rating == 0) {
                Toast.makeText(getContext(), "Please select a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> ratingData = new HashMap<>();
            ratingData.put("storeID", storeId);
            ratingData.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            ratingData.put("rating", rating);
            ratingData.put("feedback", feedback);
            ratingData.put("timestamp", System.currentTimeMillis());

            db.collection("ratings")
                    .add(ratingData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        // Refresh the store's rating
                        for(Store store : storeList) {
                            if (store.getID().equals(storeId)) {
                                loadStoreRating(store);
                                break;
                            }
                        }
    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to submit rating", Toast.LENGTH_SHORT).show();
                    });
        });
        dialog.show();
    }
}
