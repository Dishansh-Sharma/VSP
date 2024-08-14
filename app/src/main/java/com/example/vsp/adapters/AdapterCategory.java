package com.example.vsp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsp.RvListenerCategory;
import com.example.vsp.databinding.RawCategoryBinding;
import com.example.vsp.models.ModelCategory;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Random;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.HolderCategory> {
    private RawCategoryBinding binding;
    private Context context;
    private ArrayList<ModelCategory> categoryArrayList;
    private RvListenerCategory rvListenerCategory;

    public AdapterCategory(Context context, ArrayList<ModelCategory> categoryArrayList, RvListenerCategory rvListenerCategory) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.rvListenerCategory = rvListenerCategory;
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = RawCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderCategory(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategory holder, int position) {

        ModelCategory modelCategory = categoryArrayList.get(position);

        String category = modelCategory.getCategory();
        int icon = modelCategory.getIcon();

        Random random = new Random();
        int color;
        color = Color.argb(255, random.nextInt(255), random.nextInt(255) , random.nextInt(255));
        holder.categoryIconiv.setImageResource(icon);
        holder.categoryTitleTv.setText(category);

        holder.categoryIconiv.setBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvListenerCategory.onCategoryClick(modelCategory);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }


    class HolderCategory extends RecyclerView.ViewHolder {
        ShapeableImageView categoryIconiv;
        TextView categoryTitleTv;
        public HolderCategory(@NonNull View itemView) {
            super(itemView);


            categoryIconiv = binding.categoryIconiv;
            categoryTitleTv = binding.categoryTitleTv;
        }
    }
}


















