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
import com.hudazamov.virtualshop.R;

public class LoginActivity extends AppCompatActivity {

           EditText email,password;
           Button signIn;
           TextView signUp;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth =FirebaseAuth.getInstance();


        progressBar =findViewById(R.id.progressbar);
           progressBar.setVisibility(View.GONE);


        signIn =findViewById(R.id.login_btn);
        email =findViewById(R.id.email_log);
        password =findViewById(R.id.password_log);
        signUp =findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                loginUser();

            }
        });

    }

    private void loginUser() {
        String userPassword =password.getText().toString();
        String userEmail =email.getText().toString();
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Emailni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Parolni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() <6){
            Toast.makeText(this, "Parol kamida 6 ta bo`lishi kerak", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(userEmail,userPassword).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Kirish Mumkin !", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "Xato Ro`yxatdan o`tilmagan email ! : "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}