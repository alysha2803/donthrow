package com.example.donthrow.models;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Article_Firebase {

    private static final String TAG = "Article_Firebase";

    public static void fetchArticlesFromFirebase(ArticleDataCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference articlesRef = database.getReference("Articles");

        articlesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    List<Article> articles = new ArrayList<>();
                    for (DataSnapshot articleSnapshot : dataSnapshot.getChildren()) {
                        try {
                            Article article = articleSnapshot.getValue(Article.class);
                            if (article != null) {
                                Log.d(TAG, "Article fetched: " + article.getTitle());
                                articles.add(article);
                            } else {
                                Log.w(TAG, "Article is null for snapshot: " + articleSnapshot);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing article data: ", e);
                        }
                    }
                    callback.onDataFetched(articles);
                } else {
                    Log.w(TAG, "No articles found in Firebase");
                    callback.onDataFetched(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error fetching articles: " + databaseError.getMessage());
                callback.onDataFetchFailed(databaseError.toException());
            }
        });
    }

    public interface ArticleDataCallback {
        void onDataFetched(List<Article> articles);
        void onDataFetchFailed(Exception exception);
    }
}
