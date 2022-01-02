package com.hudazamov.virtualshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudazamov.virtualshop.NavCategoryActivity;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.activites.ViewAllActivity;
import com.hudazamov.virtualshop.model.NavCategoryDetaliedModel;
import com.hudazamov.virtualshop.model.NavCategoryModel;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {

    Context context;
    List<NavCategoryModel> categoryModelList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(categoryModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(categoryModelList.get(position).getName());
        holder.decription.setText(categoryModelList.get(position).getDescription());
        holder.discount.setText(categoryModelList.get(position).getDiscount());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type",categoryModelList.get(position).getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,decription,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.cat_nav_img);
            name =itemView.findViewById(R.id.nav_cat_name);
            decription =itemView.findViewById(R.id.nav_description);
            discount =itemView.findViewById(R.id.nav_cat_discount);


        }
    }
}
