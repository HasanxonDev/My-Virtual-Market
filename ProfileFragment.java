package com.hudazamov.virtualshop.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hudazamov.virtualshop.MainActivity;
import com.hudazamov.virtualshop.R;
import com.hudazamov.virtualshop.activites.RegisterActivity;
import com.hudazamov.virtualshop.model.UserModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name, email, number, adress;
    Button upDate;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        auth =FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        storage =FirebaseStorage.getInstance();
        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        number = root.findViewById(R.id.profile_number);
        adress = root.findViewById(R.id.profile_adress);

        upDate = root.findViewById(R.id.update);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel  =snapshot.getValue(UserModel.class);

                        Glide.with(getContext()).load(userModel.getProfileImg())
                                .into(profileImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);

            }
        });

        upDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
                createUer();
            }
        });



        return root;

    }



    private void updateUserProfile() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() !=null){
            Uri profileUri =data.getData();
            profileImg.setImageURI(profileUri);

        final StorageReference reference =storage.getReference().child("profile_picture")
                .child(FirebaseAuth.getInstance().getUid());

        reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "O`rnatildi", Toast.LENGTH_SHORT).show();

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                .child("profileImg").setValue(uri.toString());
                        Toast.makeText(getActivity(), "Profile rasmi Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        }

    }

    private void createUer() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userNumber = number.getText().toString();
        String userAdress = adress.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "Ismni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getActivity(), "Emailni Kiriting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "Ismni kiiting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userNumber)) {
            Toast.makeText(getActivity(), "Raqamni  kiiting !", Toast.LENGTH_SHORT).show();
            return;
        } if (TextUtils.isEmpty(userAdress)) {
            Toast.makeText(getActivity(), "Manzilni  kiiting !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number.length()>13){
            Toast.makeText(getActivity(), "Raqamni noto`g`ri kiritildi !", Toast.LENGTH_SHORT).show();
        return;
        }

    }
}

