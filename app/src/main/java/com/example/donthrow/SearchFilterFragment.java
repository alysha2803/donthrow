package com.example.donthrow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFilterFragment extends SearchFragment{
    private RecyclerView RVCategories;
    private TextView TVCategoryFilter;
    private TextView TVPriceRangeFilter;
    private EditText ETNumMinRange;
    private EditText editTextNumber2; // Consider renaming this to something more descriptive
    private TextView TVRangeFromMinToMax;
    private TextView TVConditionFilter;
    private RadioGroup radioGroup;
    private RadioButton RBNeverUsed;
    private RadioButton RBLightlyUsed;
    private RadioButton RBHeavilyUsed;
    private Button BtnApplyFilter;

    public SearchFilterFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_searchfilter, container, false);

        //get references to UI elements
        RVCategories = view.findViewById(R.id.RVCategories);
        TVCategoryFilter = view.findViewById(R.id.TVCategoryFilter);
        TVPriceRangeFilter = view.findViewById(R.id.TVPriceRangeFilter);
        ETNumMinRange = view.findViewById(R.id.ETNumMinRange);
        editTextNumber2 = view.findViewById(R.id.editTextNumber2); // Rename this variable
        TVRangeFromMinToMax = view.findViewById(R.id.TVRangeFromMinToMax);
        TVConditionFilter = view.findViewById(R.id.TVConditionFilter);
        radioGroup = view.findViewById(R.id.radioGroup);
        RBNeverUsed = view.findViewById(R.id.RBNeverUsed);
        RBLightlyUsed = view.findViewById(R.id.RBLightlyUsed);
        RBHeavilyUsed = view.findViewById(R.id.RBHeavilyUsed);
        BtnApplyFilter = view.findViewById(R.id.BtnApplyFilter);

        return view;

        //Populating the Recycler View with Categories (fetch from database)

        //applyfilterbutton trigger
        BtnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View Log.v) {
                // Handle button click
                applyFilters();
            }

        //display filtered Data list RV

    }



    private void applyFilters(){

    }

}
