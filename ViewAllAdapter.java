package com.hudazamov.virtualshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.activites.DetailedActivity;
import com.hudazamov.virtualshop.model.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    Context context;
    List<ViewAllModel> modelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(modelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(modelList.get(position).getName());
        holder.description.setText(modelList.get(position).getDescription());
        holder.rating.setText(modelList.get(position).getRating());
        holder.price.setText(modelList.get(position).getPrice()+"/kg");

        if (modelList.get(position).getType().equals("egg")){
             holder.price.setText(modelList.get(position).getPrice()+"/1 dona");
        }
        if (modelList.get(position).getType().equals("milk")){
             holder.price.setText(modelList.get(position).getPrice()+"/1 litr");
        }
        if (modelList.get(position).getType().equals("product")){
             holder.price.setText(modelList.get(position).getPrice()+"/1 dona");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, DetailedActivity.class);
                intent.putExtra("detail",modelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,description,price,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.view_all_img);
            name =itemView.findViewById(R.id.view_all_name);
            description =itemView.findViewById(R.id.view_all_desc);
            rating =itemView.findViewById(R.id.view_all_rating);
            price =itemView.findViewById(R.id.view_all_price);

        }
    }
}
