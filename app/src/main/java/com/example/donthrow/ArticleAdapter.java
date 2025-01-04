package com.example.donthrow;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.donthrow.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        // Bind article data
        holder.title.setText(article.getTitle());

        String imageUrl = article.getImage();
        Log.d("ArticleAdapter", "Loading image from URL: " + imageUrl);
        Picasso.get().load(imageUrl).into(holder.image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Log.d("ArticleAdapter", "Image loaded successfully");
            }

            @Override
            public void onError(Exception e) {
                Log.e("ArticleAdapter", "Error loading image", e);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleFragment.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("imageUrl", article.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            image = itemView.findViewById(R.id.articleImage);
        }
    }
}
