package com.example.donthrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Store;

import java.util.ArrayList;
import java.util.List;

public class SitesFragment extends Fragment {

    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_sites, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the store list
        storeList = new ArrayList<>();

        // Get the latitude and longitude from the arguments
        if (getArguments() != null) {
            double latitude = getArguments().getDouble("latitude");
            double longitude = getArguments().getDouble("longitude");

            // For demonstration, we are adding hardcoded stores nearby the given location
            storeList.add(new Store("Thrift Store 1", "Address 1"));
            storeList.add(new Store("Recycling Center 1", "Address 2"));
            storeList.add(new Store("Secondhand Shop 1", "Address 3"));
            storeList.add(new Store("Bundle Shop 1", "Address 4"));
            // Add more stores as needed
        }

        // Set the adapter
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
