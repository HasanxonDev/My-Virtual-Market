package com.hudazamov.virtualshop.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.adapter.ViewAllAdapter;
import com.hudazamov.virtualshop.model.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;

    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

            toolbar =findViewById(R.id.toobar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);


        firestore =FirebaseFirestore.getInstance();
        String type =getIntent().getStringExtra("type");
        recyclerView =findViewById(R.id.view_all_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList =new ArrayList<>();

        viewAllAdapter =new ViewAllAdapter(this,viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);

        //geting Fruits
        if (type!= null && type.equalsIgnoreCase("fruit")) {
            firestore.collection("Allproduct").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        viewAllModelList.add(viewAllModel);
                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });


        }

            //geting vegatable
            if (type!= null && type.equalsIgnoreCase("vegatable")){
                firestore.collection("Allproduct").whereEqualTo("type","vegatable").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                            ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                            viewAllModelList.add(viewAllModel);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            viewAllAdapter.notifyDataSetChanged();

                        }

                    }
                });


        }


        //geting
        if (type!= null && type.equalsIgnoreCase("fish")){
            firestore.collection("Allproduct").whereEqualTo("type","fish").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });

        }


        //geting Egg
        if (type!= null && type.equalsIgnoreCase("egg")){
            firestore.collection("Allproduct").whereEqualTo("type","egg").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });


        }
        //geting Milk
        if (type!= null && type.equalsIgnoreCase("milk")){
            firestore.collection("Allproduct").whereEqualTo("type","milk").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });


        }


        //geting Product
        if (type!= null && type.equalsIgnoreCase("products")){
            firestore.collection("Allproduct").whereEqualTo("type","products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
               progressBar.setVisibility(View.GONE);
               recyclerView.setVisibility(View.VISIBLE);
                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });


        }
        //geting Product
        if (type!= null && type.equalsIgnoreCase("products")){
            firestore.collection("Allproduct").whereEqualTo("type","product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        ViewAllModel viewAllModel =documentSnapshot.toObject(ViewAllModel.class);
                        viewAllModelList.add(viewAllModel);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        viewAllAdapter.notifyDataSetChanged();

                    }

                }
            });


        }

    }
}