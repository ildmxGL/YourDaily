package com.madcamp.yourdaily;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.List;


public class UserLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText mEmail;
    private EditText mPassword;

    private static final int NUM_GRID_COLUMNS = 3;

    private Button mSignIn_btn;
    private TextView mRegister_btn;

    private ProgressBar mProgress_bar;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private Context mContext;

    private static final String TAG = "UserLoginActivity";

    private static final int RC_SIGN_IN = 1000;
    private GoogleSignInButton Google_Login;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();
        mContext = UserLoginActivity.this;

        mEmail = (EditText) findViewById(R.id.email_editText);
        mPassword = (EditText) findViewById(R.id.password_editText);

        mSignIn_btn = (Button)findViewById(R.id.signin_button);
        mRegister_btn = (TextView)findViewById(R.id.register_button);

        mProgress_bar = (ProgressBar)findViewById(R.id.loading_progressBar);

        Google_Login = (GoogleSignInButton) findViewById(R.id.google_login);


        init();
    }
    
    private void init() {

        Log.d(TAG, "init: UserLoginActivity init.");

        mSignIn_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isEmpty()) {
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    inProgress(true);
                    mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(mContext, "User signned in", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            inProgress(false);
                            Toast.makeText(mContext, "Sign in failed due to "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        mProgress_bar.setVisibility(View.GONE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });
        
    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")) {
            return true;
        }
        else {
            return false;
        }
    }

    private void inProgress(boolean x){
        if (x){
            mProgress_bar.setVisibility(View.VISIBLE);
            mSignIn_btn.setEnabled(false);
            mRegister_btn.setEnabled(false);
        }else{
            mProgress_bar.setVisibility(View.GONE);
            mSignIn_btn.setEnabled(true);
            mRegister_btn.setEnabled(true);
        }
    }

    private boolean isEmpty(){
        if(TextUtils.isEmpty(mEmail.getText().toString())){
            mEmail.setError("Required to write an Email address!");
            return true;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())){
            mPassword.setError("Required to write a password!");
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: ");
        
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(mContext, "User signned in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //Do something after 100ms
                finish(); return;
            }
            else{
                Toast.makeText(mContext, "Google Login Failed", Toast.LENGTH_LONG).show();
                //구글 로그인 실패
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle: get goole authentication.");
        
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(mContext, "인증 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            String Email = task.getResult().getUser().getEmail();
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
                            newProfile.setEmail(Email);
                            newProfile.setNick(Email);
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
                            Toast.makeText(mContext, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connect failed.");

    }



}
