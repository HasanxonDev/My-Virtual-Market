package com.hudazamov.virtualshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hudazamov.virtualshop.activites.PlaceOrderActivity;
import com.hudazamov.virtualshop.adapter.MyCArtAdapter;
import com.hudazamov.virtualshop.model.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView  ovverTotalammount;
    Button buyNow;

    RecyclerView recyclerView;
    MyCArtAdapter myCArtAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar progressBar;

    public MyCartsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

       progressBar =root.findViewById(R.id.progressbarmy);
        progressBar.setVisibility(View.VISIBLE);
        db =FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
        buyNow =root.findViewById(R.id.buy_no1w);
        recyclerView =root.findViewById(R.id.recycelview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ovverTotalammount =root.findViewById(R.id.totalAmount);


         cartModelList =new ArrayList<>();
         myCArtAdapter =new MyCArtAdapter(getActivity(),cartModelList);
         recyclerView.setAdapter(myCArtAdapter);

         db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                     .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
             @Override
             public void onComplete(@NonNull Task<QuerySnapshot> task) {
                 if (task.isSuccessful()){
                     for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                         String documentId  =documentSnapshot.getId();
                         MyCartModel cartModel =documentSnapshot.toObject(MyCartModel.class);
                         cartModel.setDocumentId(documentId);
                         cartModelList.add(cartModel);
                       myCArtAdapter.notifyDataSetChanged();
                       progressBar.setVisibility(View.GONE);
                       recyclerView.setVisibility(View.VISIBLE);
                         calculateTotalAmmount(cartModelList);
                     }
                 }else  if (task==null){
                     Toast.makeText(getActivity(), "Sizning Savatingiz Bosh !", Toast.LENGTH_LONG).show();
                 }

                 calculateTotalAmmount(cartModelList);

             }


         });



         buyNow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(getContext(), PlaceOrderActivity.class);
                 intent.putExtra("itemlist",(Serializable) cartModelList);
                 startActivity(intent);
             }
         });

        return root;
    }

    private void calculateTotalAmmount(List<MyCartModel> cartModelList) {

        double totalAmmmount =0.0;
        for (MyCartModel myCartModel : cartModelList){
            totalAmmmount+=myCartModel.getTotalPrice();
            ovverTotalammount.setText("Umumiy Xisob: "+totalAmmmount);
        }


    }


}
