package com.jsstech.newsappusingapi;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.myviewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRvAdapter(ArrayList<Articles> articlesArrayList,Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRvAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_rv_item,parent,false);
        return new NewsRvAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRvAdapter.myviewHolder holder,int position) {
        Articles articles = articlesArrayList.get(position);
        holder.titletv.setText(articles.getTitle());
        holder.subtitleTv.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsTV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NewsDetailsActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("desc",articles.getDescription());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        private TextView titletv, subtitleTv;
        private ImageView newsTV;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            titletv = itemView.findViewById(R.id.tvheadline);
            subtitleTv = itemView.findViewById(R.id.tvSubheading);
            newsTV = itemView.findViewById(R.id.newsImageView);
        }
    }
}
