package com.madcamp.yourdaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    CoordinatorLayout llBottomSheet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llBottomSheet = (CoordinatorLayout) findViewById(R.id.bottomContext);
    }
}
