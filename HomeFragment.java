package com.hudazamov.virtualshop.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.adapter.HomeAdapter;
import com.hudazamov.virtualshop.adapter.NavCategoryDetailedAdapter;
import com.hudazamov.virtualshop.adapter.PopularAdapter;
import com.hudazamov.virtualshop.adapter.RecomendedAdapter;
import com.hudazamov.virtualshop.adapter.ViewAllAdapter;
import com.hudazamov.virtualshop.databinding.FragmentHomeBinding;
import com.hudazamov.virtualshop.model.HomeCategory;
import com.hudazamov.virtualshop.model.NavCategoryDetaliedModel;
import com.hudazamov.virtualshop.model.PopularModel;
import com.hudazamov.virtualshop.model.RecomendedModel;
import com.hudazamov.virtualshop.model.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

ScrollView scrollView;
//Popular
    PopularAdapter popularAdapter;
    RecyclerView populaRec,homeCatRec,recomendedRec;
    List<PopularModel> popularModelList;
    FirebaseFirestore db;
    ///Category Design;
 List<HomeCategory> categoryList;
 HomeAdapter homeAdapter;

 //SearchView
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private List<NavCategoryDetaliedModel> detaliedModels;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;
    private NavCategoryDetailedAdapter detailedAdapter;


 // Recomended
    List<RecomendedModel> recomendedModelList;
    RecomendedAdapter recomendedAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        populaRec =root.findViewById(R.id.pop_rec);
          db =FirebaseFirestore.getInstance();
       homeCatRec =root.findViewById(R.id.explore_rec);
       recomendedRec =root.findViewById(R.id.recomended_rec);
       scrollView =root.findViewById(R.id.scrollView);
       scrollView.setVisibility(View.GONE);

       //Popular
       populaRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList  =new ArrayList<>();
        popularAdapter =new PopularAdapter(getActivity(),popularModelList);
        populaRec.setAdapter(popularAdapter);

        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel =document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                            popularAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Xato : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        //Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList  =new ArrayList<>();
        homeAdapter =new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory =document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Xato : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        //Recomended
        recomendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recomendedModelList  =new ArrayList<>();
        recomendedAdapter =new RecomendedAdapter(getActivity(),recomendedModelList);
        recomendedRec.setAdapter(recomendedAdapter);

        db.collection("Recomended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecomendedModel recomendedModel =document.toObject(RecomendedModel.class);
                                recomendedModelList.add(recomendedModel);
                                recomendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Xato : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        ///search  /// box//// ////  //////////////////////////////
        recyclerViewSearch =root.findViewById(R.id.search_rec);
        search_box =root.findViewById(R.id.search_box);
        viewAllModelList =new ArrayList<>();

        viewAllAdapter =new ViewAllAdapter(getContext(),viewAllModelList);


        viewAllAdapter.notifyDataSetChanged();
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();

                }else {
                    searchProduct(s.toString());
                }

            }
        });



        return root;

    }

    private void searchProduct(String type) {

        if (!type.isEmpty()){
            db.collection("Allproduct").whereEqualTo("name",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() !=null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ViewAllModel viewAllModel =doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }

        }



}