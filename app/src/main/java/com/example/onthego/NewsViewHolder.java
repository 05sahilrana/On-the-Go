package com.example.onthego;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private String link; // Store the news article link

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        textViewTitle = itemView.findViewById(R.id.titleTextView);
        textViewDescription = itemView.findViewById(R.id.descriptionTextView);

        // Set an OnClickListener for the title
        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (link != null && !link.isEmpty()) {
                    // Open the news article link in a web browser
                    Context context = view.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(intent);
                }
            }
        });
    }

    public void bind(NewsArticle newsArticle) {
        textViewTitle.setText(newsArticle.getTitle());
        textViewDescription.setText(newsArticle.getDescription());
        link = newsArticle.getLink(); // Store the news article link

        // Load image using Picasso (you need to add Picasso as a dependency)
        Picasso.get().load(newsArticle.getImageLink()).placeholder(R.drawable.placeholder_image).into(imageView);
    }
}
