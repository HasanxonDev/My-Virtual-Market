package com.hudazamov.virtualshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudazamov.virtualshop.adapter.NavCategoryAdapter;
import com.hudazamov.virtualshop.adapter.NavCategoryDetailedAdapter;
import com.hudazamov.virtualshop.adapter.ViewAllAdapter;
import com.hudazamov.virtualshop.model.HomeCategory;
import com.hudazamov.virtualshop.model.NavCategoryDetaliedModel;
import com.hudazamov.virtualshop.model.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NavCategoryDetaliedModel> list;
    NavCategoryDetailedAdapter adapter;
    FirebaseFirestore db;
    Button addToCart;
    ImageView addItem,removeItem;
    Toolbar toolbar;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ProgressBar progressBar;
    int totalQuantity  =1;
    int totalPrice =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);
        progressBar = findViewById(R.id.progressbarvy);
        progressBar.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.nav_cat_recycel);
        String type = getIntent().getStringExtra("type");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);


        //geting Drinks
        if (type != null && type.equalsIgnoreCase("drink")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "drink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        NavCategoryDetaliedModel detaliedModel = documentSnapshot.toObject(NavCategoryDetaliedModel.class);
                        list.add(detaliedModel);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                    }

                }
            });


        }

        //geting Breakfast
        if (type != null && type.equalsIgnoreCase("breakfast")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        NavCategoryDetaliedModel detaliedModel = documentSnapshot.toObject(NavCategoryDetaliedModel.class);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        list.add(detaliedModel);
                        adapter.notifyDataSetChanged();
                    }

                }
            });


        }

        //geting Breakfast
        if (type != null && type.equalsIgnoreCase("fruit")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        NavCategoryDetaliedModel detaliedModel = documentSnapshot.toObject(NavCategoryDetaliedModel.class);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        list.add(detaliedModel);
                        adapter.notifyDataSetChanged();
                    }

                }
            });


        }


        //geting Breakfast
        if (type != null && type.equalsIgnoreCase("meat")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "meat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        NavCategoryDetaliedModel detaliedModel = documentSnapshot.toObject(NavCategoryDetaliedModel.class);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        list.add(detaliedModel);
                        adapter.notifyDataSetChanged();
                    }

                }
            });


        }



    }
}