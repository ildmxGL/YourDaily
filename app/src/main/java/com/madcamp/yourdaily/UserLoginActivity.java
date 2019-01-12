package com.madcamp.yourdaily;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class UserLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText mEmail_editText;
    private EditText mPassword_editText;

    private Button mSignIn_btn;
    private Button mRegister_btn;
    private Button mBack_btn;


    private ProgressBar mProgress_bar;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "SignInActivity";

    private static final int RC_SIGN_IN = 1000;
    SignInButton Google_Login;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();

        mEmail_editText = (EditText) findViewById(R.id.email_editText);
        mPassword_editText = (EditText) findViewById(R.id.password_edtiText);

        mSignIn_btn = (Button)findViewById(R.id.signin_button);
        mRegister_btn = (Button)findViewById(R.id.register_button);
        mBack_btn = (Button)findViewById(R.id.back_button);


        mProgress_bar = (ProgressBar)findViewById(R.id.loading_progressBar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        Google_Login = (SignInButton) findViewById(R.id.google_login);

        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });



        mSignIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()) return;
                inProgress(true);
                mAuth.signInWithEmailAndPassword(mEmail_editText.getText().toString(), mPassword_editText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(UserLoginActivity.this, "User signned in", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish(); return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(UserLoginActivity.this, "Sign in failed due to "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });




    }


    private void inProgress(boolean x){
        if (x){
            mProgress_bar.setVisibility(View.VISIBLE);
            mBack_btn.setEnabled(false);
            mSignIn_btn.setEnabled(false);
            mRegister_btn.setEnabled(false);
        }else{
            mProgress_bar.setVisibility(View.GONE);
            mBack_btn.setEnabled(true);
            mSignIn_btn.setEnabled(true);
            mRegister_btn.setEnabled(true);
        }
    }

    private boolean isEmpty(){
        if(TextUtils.isEmpty(mEmail_editText.getText().toString())){
            mEmail_editText.setError("Required to write an Email address!");
            return true;
        }
        if (TextUtils.isEmpty(mPassword_editText.getText().toString())){
            mPassword_editText.setError("Required to write a password!");
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(UserLoginActivity.this, "User signned in", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); return;
            }
            else{
                Toast.makeText(UserLoginActivity.this, "Google Login Failed", Toast.LENGTH_LONG).show();
                //구글 로그인 실패
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(UserLoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UserLoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
