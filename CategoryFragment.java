package com.hudazamov.virtualshop.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.adapter.NavCategoryAdapter;
import com.hudazamov.virtualshop.adapter.PopularAdapter;
import com.hudazamov.virtualshop.model.NavCategoryModel;
import com.hudazamov.virtualshop.model.PopularModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    FirebaseFirestore db;
    List<NavCategoryModel>categoryModelList;
    NavCategoryAdapter categoryAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category,container,false);
        progressBar =root.findViewById(R.id.progressbarmy);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView=root.findViewById(R.id.cat_rec);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        categoryModelList  =new ArrayList<>();
        categoryAdapter =new NavCategoryAdapter(getActivity(),categoryModelList);
        recyclerView.setAdapter(categoryAdapter);
        db =FirebaseFirestore.getInstance();

        db.collection("NaVcategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategoryModel categoryModel =document.toObject(NavCategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        } else {
                            Toast.makeText(getActivity(), "Xato : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });







        return root;
    }
}