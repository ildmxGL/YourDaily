package com.madcamp.yourdaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginChooseActivity extends AppCompatActivity {

    ImageButton userLogin;
    ImageButton writerLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_user);

        userLogin = (ImageButton)findViewById(R.id.user_login);
        writerLogin = (ImageButton)findViewById(R.id.writer_login);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginChooseActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish(); return;
            }
        });
    }
}
