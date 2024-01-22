package com.example.finalproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    EditText etName, etLastname, etEmail, etPassword;
    Button btnLogin, btnSignup;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String lastname = etLastname.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                            String uid = task.getResult().getUser().getUid();

                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                            CollectionReference reference = database.collection("UserModel");
                            UserModel user = new UserModel(name, lastname, email, password);
                            reference.add(user);

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Signup.this, "Kayıt başarısız", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}