package com.hudazamov.virtualshop.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hudazamov.virtualshop.MainActivity;
import com.hudazamov.virtualshop.R;

public class HomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        auth =FirebaseAuth.getInstance();

        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            Toast.makeText(HomeActivity.this, "Kuting....", Toast.LENGTH_SHORT).show();
       finish();
        }


    }

    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));

    }

    public void rehidter(View view) {
        startActivity(new Intent(HomeActivity.this, RegisterActivity.class));


    }
}