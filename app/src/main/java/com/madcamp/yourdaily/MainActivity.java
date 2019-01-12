package com.madcamp.yourdaily;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayList<Uri> sampleURIs;

    private RecyclerView mRecycle1;
    RecyclerView.LayoutManager mLayoutManager;

    //private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //testText = (TextView)findViewById(R.id.testtxt);

        mRecycle1 = (RecyclerView) findViewById(R.id.image_recycle1);
        mRecycle1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycle1.setLayoutManager(mLayoutManager);

        sampleURIs = new ArrayList<>();

        sampleURIs.add(Uri.parse("https://file2.nocutnews.co.kr/newsroom/image/2018/11/03/20181103154544539681_0_750_1050.jpg"));
        sampleURIs.add(Uri.parse("https://img.insight.co.kr/static/2018/07/24/700/524xml6u4y702kq18208.jpg"));
        sampleURIs.add(Uri.parse("http://img.etoday.co.kr/pto_db/2018/02/20180207224139_1184559_600_819.jpg"));
        sampleURIs.add(Uri.parse("https://t1.daumcdn.net/cfile/tistory/230D734057B6F3910B"));
        sampleURIs.add(Uri.parse("https://img.huffingtonpost.com/asset/5ac0443f1e00003b137b0438.jpeg?cache=j2lK2hCwZ3&ops=scalefit_630_noupscale"));
        sampleURIs.add(Uri.parse("http://img.etoday.co.kr/pto_db/2018/03/20180308141418_1193544_600_819.jpg"));
        sampleURIs.add(Uri.parse("http://newsimg.hankookilbo.com/2017/07/14/201707140970054463_1.jpg"));
        sampleURIs.add(Uri.parse("http://tenasia.hankyung.com/webwp_kr/wp-content/uploads/2015/09/2015091109232247445-540x360.jpg"));
        sampleURIs.add(Uri.parse("http://image.chosun.com/sitedata/image/201808/10/2018081000670_0.jpg"));
        sampleURIs.add(Uri.parse("https://i.pinimg.com/originals/4b/b5/48/4bb548cb205e2aa0373700506f183ef4.jpg"));
        sampleURIs.add(Uri.parse("https://newsimg.sedaily.com/2017/11/16/1ONLOGFG8I_1.jpg"));
        sampleURIs.add(Uri.parse("http://the-star.co.kr/site/data/img_dir/2017/11/16/2017111602664_0.jpg"));

        ImageRecycleAdapter myAdapter = new ImageRecycleAdapter(sampleURIs);
        mRecycle1.setAdapter(myAdapter);

        //Login Activity if not logged in
        if(mAuth.getCurrentUser()==null){
            Intent intent = new Intent(MainActivity.this, LoginChooseActivity.class);
            startActivity(intent);
            finish(); return;
        }

        //Show Logged in Email
        //testText.setText(mAuth.getCurrentUser().getEmail());
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}
