package com.hudazamov.virtualshop.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hudazamov.virtualshop.MainActivity;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth =FirebaseAuth.getInstance();

        progressBar =findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


         database =FirebaseDatabase.getInstance();
        signIn =findViewById(R.id.sign_in);
        email =findViewById(R.id.email_reg);
        name =findViewById(R.id.name_reg);
        password =findViewById(R.id.password_reg);
        signUp =findViewById(R.id.register_btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUer();
                progressBar.setVisibility(View.VISIBLE);


            }
        });
    }

    private void createUer() {
        String userName =name.getText().toString();
        String userPassword =password.getText().toString();
        String userEmail =email.getText().toString();
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(RegisterActivity.this, "Ismni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }  if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(RegisterActivity.this, "Emailni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName)){
            Toast.makeText(RegisterActivity.this, "Parolni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() <6){
            Toast.makeText(RegisterActivity.this, "Parol kamida 6 ta bo`lishi kerak", Toast.LENGTH_SHORT).show();
       return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel userModel =new UserModel(userName,userEmail,userPassword);
                            String id =task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            progressBar.setVisibility(View.GONE);



                            Toast.makeText(RegisterActivity.this, "Ro`yxatdan o`tqazildi !", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();

                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Xato: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

    }
}