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
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
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



    //private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //testText = (TextView)findViewById(R.id.testtxt);



        final ArrayList<Uri> imgURLs = new ArrayList<>();
        imgURLs.add(Uri.parse("https://static.starcraft.com/images/content/share/share-1200x630-ca2d9035c74f9bb10faf142c83f76b47c909d6bbb177522676428d590c92ac16c99a92d1aea0afd4bbbd269e88b4341c4840d183d5555504f305c5a2a484469a.jpg"));
        imgURLs.add(Uri.parse("http://kr.blizzard.com/static/_images/games/sc/wallpapers/wall2/wall2-1920x1200.jpg"));
        imgURLs.add(Uri.parse("https://upload.wikimedia.org/wikipedia/ko/6/6f/%EC%8A%A4%ED%83%80%ED%81%AC%EB%9E%98%ED%94%84%ED%8A%B8_%EC%9E%90%EC%BC%93.jpg"));
        imgURLs.add(Uri.parse("http://t1.daumcdn.net/liveboard/slidee/7dbe6c875ef341a39afaff7747409e27.jpeg"));
        imgURLs.add(Uri.parse("https://kr.battle.net/forums/static/images/game-logos/game-logo-sc2.png"));
        imgURLs.add(Uri.parse("http://bnetcmsus-a.akamaihd.net/cms/blog_header/w6/W68CINWEIMYA1522156038117.jpg"));
        imgURLs.add(Uri.parse("http://www.bloter.net/wp-content/uploads/2017/11/starcraft2-free.jpg"));
        imgURLs.add(Uri.parse("http://img.hani.co.kr/imgdb/resize/2007/0520/117958271733_20070520.JPG"));
        imgURLs.add(Uri.parse("http://www.gameple.co.kr/news/photo/201708/138041_143500_2323.jpg"));
        imgURLs.add(Uri.parse("http://img.tf.co.kr/article/home/2017/04/08/20176465149166656100.jpg"));
        imgURLs.add(Uri.parse("http://www.gametoc.co.kr/news/photo/201708/44865_84462_739.jpg"));
        imgURLs.add(Uri.parse("https://bnetcmsus-a.akamaihd.net/cms/blog_header/o8/O89EFI6EFU1D1542160918343.jpg"));
        imgURLs.add(Uri.parse("https://t1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/IgT/image/38YN-7Z5Dr5p6Uh8W75gXYZffQ0.jpg"));
        imgURLs.add(Uri.parse("http://img.tf.co.kr/article/home/2017/01/02/20175065148336804000.jpg"));

        GridView gridView = (GridView)findViewById(R.id.gridView);
        GridAdapter myAdapter = new GridAdapter(this, imgURLs);
        gridView.setAdapter(myAdapter);

        //Login Activity if not logged in
        if(mAuth.getCurrentUser()==null){
            Intent intent = new Intent(MainActivity.this, LoginChooseActivity.class);
            startActivity(intent);
            finish(); return;
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, ViewDaily.class);
                myIntent.putExtra("Uri", imgURLs.get(position).toString());
                startActivity(myIntent);
            }
        });

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
