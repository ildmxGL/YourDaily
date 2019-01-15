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

import java.util.List;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText emailTextView;
    private EditText nameTextView;
    private EditText passwordTextView;
    private EditText checkPasswordTextView;

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
        checkPasswordTextView = (EditText) findViewById(R.id.input_password_check);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mProgressBar.setVisibility(View.GONE);

        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) {
                    Toast.makeText(RegisterUserActivity.this, "You must fill out all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!checkPasswordTextView.getText().toString().equals(passwordTextView.getText().toString())){
                    Toast.makeText(RegisterUserActivity.this, "Password is not same.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailTextView.getText().toString(), passwordTextView.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(RegisterUserActivity.this, "User registerd successfully!", Toast.LENGTH_LONG).show();
                                FriendCard newFriendCard = new FriendCard();
                                newFriendCard.setProfileUri("https://firebasestorage.googleapis.com/v0/b/your-daily.appspot.com/o/images%2Fyour_daily.png?alt=media&token=2e98a556-9951-4387-9fd8-f7056d983751");
                                newFriendCard.setCard1Uri("");
                                newFriendCard.setCard2Uri("");
                                newFriendCard.setCard3Uri("");
                                new FirebaseDatabaseFriendCard().addFriendCard(newFriendCard, new FirebaseDatabaseFriendCard.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<FriendCard> friendCards, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                                Profile newProfile = new Profile();
                                newProfile.setEmail(emailTextView.getText().toString());
                                newProfile.setNick(nameTextView.getText().toString());
                                newProfile.setProfileImage("https://firebasestorage.googleapis.com/v0/b/your-daily.appspot.com/o/images%2Fyour_daily.png?alt=media&token=2e98a556-9951-4387-9fd8-f7056d983751");
                                new FirebaseDatabaseProfile().addPreDaily(newProfile, new FirebaseDatabaseProfile.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<Profile> profiles, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {

                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });

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
        if(TextUtils.isEmpty(checkPasswordTextView.getText().toString())){
            return true;
        }
        return false;
    }




}
