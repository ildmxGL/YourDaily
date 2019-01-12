package com.madcamp.yourdaily;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText emailTextView;
    private EditText nameTextView;
    private EditText passwordTextView;

    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    private AppCompatButton mRegister_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mRegister_btn = (AppCompatButton) findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        emailTextView = (EditText) findViewById(R.id.input_email);
        nameTextView = (EditText) findViewById(R.id.input_username);
        passwordTextView = (EditText) findViewById(R.id.input_password);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.GONE);

        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) {
                    Toast.makeText(RegisterUserActivity.this, "You must fill out all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailTextView.getText().toString(), passwordTextView.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(RegisterUserActivity.this, "User registerd successfully!", Toast.LENGTH_LONG).show();
                                finish();return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(RegisterUserActivity.this, "Register failed due to "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public boolean isEmpty(){
        if(TextUtils.isEmpty(emailTextView.getText().toString())){
            return true;
        }
        if(TextUtils.isEmpty(nameTextView.getText().toString())){
            return true;
        }
        if(TextUtils.isEmpty(passwordTextView.getText().toString())){
            return true;
        }
        return false;
    }



}
