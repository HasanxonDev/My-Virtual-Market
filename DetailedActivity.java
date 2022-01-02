package com.hudazamov.virtualshop.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.model.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity  =1;
    int totalPrice =0;
    ImageView detailedImg;
    TextView price,rating,description,textView;
    Button addToCart;
    ImageView addItem,removeItem;
    Toolbar toolbar;
   FirebaseFirestore firestore;
   FirebaseAuth auth;
    ViewAllModel  viewAllModel =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar =findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore =FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
        final  Object  object =getIntent().getSerializableExtra("detail");

        if (object instanceof ViewAllModel){
             viewAllModel =(ViewAllModel) object;

        }
          quantity =findViewById(R.id.quantity);
        detailedImg =findViewById(R.id.detailed_img);
        addItem =findViewById(R.id.add_item);
        removeItem =findViewById(R.id.remove_item);


        price =findViewById(R.id.detailed_price);
        rating =findViewById(R.id.detailed_rating);
        description =findViewById(R.id.detailed_dec);
        textView =findViewById(R.id.textView6);

        if (viewAllModel !=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            textView.setText(viewAllModel.getName()+" Haqida");
            price.setText("Narx: "+viewAllModel.getPrice()+"/1kg");

            totalPrice =viewAllModel.getPrice() *totalQuantity;

            if (viewAllModel.getType().equals("egg")){
                price.setText("Narx: "+viewAllModel.getPrice()+"/1dona");
                totalPrice =viewAllModel.getPrice() *totalQuantity;

            }
            if (viewAllModel.getType().equals("milk") ){
                price.setText("Narx: "+viewAllModel.getPrice()+"/1litr");
                totalPrice =viewAllModel.getPrice() *totalQuantity;

            }
            if (viewAllModel.getType().equals("product")){
                price.setText("Narx: "+viewAllModel.getPrice()+"/1dona");
                totalPrice =viewAllModel.getPrice() *totalQuantity;

            }

        }

        addToCart =findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (totalQuantity<100){
                   totalQuantity++;
                   quantity.setText(String.valueOf(totalQuantity));
                   totalPrice =viewAllModel.getPrice() *totalQuantity;

               }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity >1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice =viewAllModel.getPrice() *totalQuantity;

                }

            }
        });

    }

    private void addedToCart() {
    String savedCurrentDate,saveCurrentTime;
        Calendar calForDate =Calendar.getInstance();

        SimpleDateFormat  currentDate =new SimpleDateFormat("MM dd, yyyy");
        savedCurrentDate =currentDate.format(calForDate.getTime());


        SimpleDateFormat  currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calForDate.getTime());


        final HashMap<String,Object>  cartMap =new HashMap<>();
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate",savedCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "My Cart Oynasiga Qo`shildi !", Toast.LENGTH_SHORT).show();
              finish();
            }
        });

    }
}