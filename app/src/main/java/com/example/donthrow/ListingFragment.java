package com.example.donthrow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.donthrow.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ListingFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView productImage;
    private EditText productName, productPrice, productLocation, productPostage;
    private Spinner categorySpinner, conditionSpinner;
    private Button uploadButton, saveButton;

    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listing, container, false);

        // Initialize Firebase references
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        storageReference = FirebaseStorage.getInstance().getReference("products_image");

        // Initialize views
        productImage = view.findViewById(R.id.productImage);
        productName = view.findViewById(R.id.productName);
        productPrice = view.findViewById(R.id.productPrice);
        productLocation = view.findViewById(R.id.productLocation);
        productPostage = view.findViewById(R.id.productPostage);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        conditionSpinner = view.findViewById(R.id.conditionSpinner);
        uploadButton = view.findViewById(R.id.uploadImageButton);
        saveButton = view.findViewById(R.id.saveProductButton);

        // Set up spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_conditions, android.R.layout.simple_spinner_item);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);

        // Set listeners
        uploadButton.setOnClickListener(v -> openFileChooser());
        saveButton.setOnClickListener(v -> saveProductToDatabase());

        return view;
    }

    private void openFileChooser() {
        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Product Image"), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (productImage != null) {
                productImage.setImageURI(imageUri);
            }
        }
    }

    private void saveProductToDatabase() {
        if (getContext() == null) return;

        String name = productName.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String condition = conditionSpinner.getSelectedItem().toString();
        String location = productLocation.getText().toString().trim();
        String postage = productPostage.getText().toString().trim();
        String priceString = productPrice.getText().toString().trim();

        if (imageUri == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(postage) || TextUtils.isEmpty(priceString)) {
            Toast.makeText(getContext(), "Please fill in all fields and upload an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid price format!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload image to Firebase Storage
        String productId = UUID.randomUUID().toString();
        StorageReference fileReference = storageReference.child(productId + ".jpg");
        fileReference.putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Save product details to Firebase Realtime Database
                    Product product = new Product(productId, name, category, price, condition, location, postage, imageUrl);
                    databaseReference.child(productId).setValue(product).addOnCompleteListener(databaseTask -> {
                        if (databaseTask.isSuccessful()) {
                            Toast.makeText(getContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(getContext(), "Failed to add product. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {
                Toast.makeText(getContext(), "Failed to upload image. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        if (productImage != null) productImage.setImageResource(R.drawable.placeholder_image);
        productName.setText("");
        productPrice.setText("");
        productLocation.setText("");
        productPostage.setText("");
        categorySpinner.setSelection(0);
        conditionSpinner.setSelection(0);
        imageUri = null;
    }
}
