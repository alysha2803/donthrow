package com.example.donthrow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Article;
import com.example.donthrow.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView articleRecyclerView, productRecyclerView;
    private ArrayList<Article> articleList;
    private ArrayList<Product> productList;
    private ArticleAdapter articleAdapter;
    private ProductAdapter productAdapter;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        articleRecyclerView = view.findViewById(R.id.articleRecyclerView);
        productRecyclerView = view.findViewById(R.id.productRecyclerView);

        articleList = new ArrayList<>();
        productList = new ArrayList<>();

        articleAdapter = new ArticleAdapter(getContext(), articleList);
        productAdapter = new ProductAdapter(getContext(), productList);

        articleRecyclerView.setAdapter(articleAdapter);
        productRecyclerView.setAdapter(productAdapter);

        articleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadArticlesFromFirebase();
        loadProductsFromFirebase();

        return view;
    }

    private void loadArticlesFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Articles");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articleList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Article article = snapshot.getValue(Article.class);
                    if (article != null) {
                        Log.d("HomeFragment", "Article fetched: " + article.getTitle() + ", Image URL: " + article.getImage());
                        articleList.add(new Article(article.getTitle(), article.getDescription(), article.getImage(), article.getArticleId()));
                    }
                }
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load articles: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error fetching articles: " + databaseError.getMessage());
            }
        });
    }

    private void loadProductsFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productList.add(new Product(product.getName(), product.getPrice(), product.getCondition(), product.getImage(), product.getProductId()));
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load products: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
