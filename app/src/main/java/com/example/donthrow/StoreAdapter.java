package com.example.donthrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Store;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> storeList;
    private SitesFragment fragment;

    public StoreAdapter(List<Store> storeList, SitesFragment fragment) {
        this.storeList = storeList;
        this.fragment = fragment;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.nameTextView.setText(store.getName());
        holder.addressTextView.setText(store.getAddress());
        holder.ratingTextView.setText(String.format("Rating: %.1f", store.getRating()));

        holder.rateButton.setOnClickListener(v ->
                fragment.showRatingDialog(store.getID(), store.getName())
        );
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void updateRating(String storeId, float newRating) {
        for (int i = 0; i < storeList.size(); i++) {
            Store store = storeList.get(i);
            if (store.getID().equals(storeId)) {
                store.setRating(newRating);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView addressTextView;
        public TextView ratingTextView;
        public Button rateButton;

        public StoreViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.store_name);
            addressTextView = itemView.findViewById(R.id.store_address);
            ratingTextView = itemView.findViewById(R.id.store_rating);
            rateButton = itemView.findViewById(R.id.rate_button);
        }
    }
}
