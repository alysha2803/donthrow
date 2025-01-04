package com.example.donthrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ArticleFragment extends Fragment {

    private TextView articleTitle, articleDescription;
    private ImageView articleImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        // Initialize views
        articleTitle = view.findViewById(R.id.articleTitle);
        articleDescription = view.findViewById(R.id.articleDescription);
        articleImage = view.findViewById(R.id.articleImage);

        // Retrieve passed data from intent
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("articleTitle");
            String description = args.getString("articleDescription");
            String imageUrl = args.getString("articleImageUrl");

            articleTitle.setText(title);
            articleDescription.setText(description);
            Picasso.get().load(imageUrl).into(articleImage);
        }

        return view;
    }
}
