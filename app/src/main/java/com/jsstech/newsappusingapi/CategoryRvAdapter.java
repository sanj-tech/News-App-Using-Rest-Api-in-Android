package com.jsstech.newsappusingapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryRvAdapter extends RecyclerView.Adapter<CategoryRvAdapter.ViewHolder> {
    private ArrayList<CategoryModel> categoryRVModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRvAdapter(ArrayList<CategoryModel> categoryRVModels,Context context,CategoryClickInterface categoryClickInterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }


    @NonNull
    @Override
    public CategoryRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcategorielayout,parent,false);
        return new CategoryRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRvAdapter.ViewHolder holder,int position) {

        CategoryModel categoryRvModel=categoryRVModels.get(position);
        holder.categoryTv.setText(categoryRvModel.getCategory());
        Picasso.get().load(categoryRvModel.getCategoryImageUrl()).into(holder.categoryImV);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        categoryClickInterface.onCreategoryClick(position);
    }
});
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }

    public interface CategoryClickInterface{
        void onCreategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView categoryTv;
       private ImageView categoryImV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv=itemView.findViewById(R.id.tvCatogory);
            categoryImV=itemView.findViewById(R.id.imgCat);
        }
    }
}
