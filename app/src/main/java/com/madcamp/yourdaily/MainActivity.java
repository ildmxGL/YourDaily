package com.madcamp.yourdaily;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = MainActivity.this;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayList<Uri> sampleURIs;
    private ArrayList<DailyCard> dailies;
    private ArrayList<String> keys;

    private GridView gridView;
    private ImageView logoutImageView;

    //For swipe view
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private AlertDialog.Builder builder;


    //private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        //testText = (TextView)findViewById(R.id.testtxt);

        builder = new AlertDialog.Builder(this);
        dailies = new ArrayList<>();
        final ArrayList<Uri> imgURLs = new ArrayList<>();

        gridView = (GridView)findViewById(R.id.gridView);

        setupBottomNavigationView();
        logoutImageView = (ImageView)findViewById(R.id.rocket_icon);

        /*
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Log Out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                Intent loginIntent = new Intent(MainActivity.this, UserLoginActivity.class);
                                startActivity(loginIntent);

                                Toast.makeText(getApplicationContext(),"Successfully logged out", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"log out canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("LogOut");
                alert.show();
            }
        });
        */
        new FirebaseDatabaseDailyCard().readBooks( new FirebaseDatabaseDailyCard.DataStatus() {
            @Override
            public void DataIsLoaded(List<DailyCard> books, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                dailies = new ArrayList<DailyCard>(books);
                Log.d(TAG, "DataIsLoaded: dailies recived : " + dailies.get(0).getImageUri());
                keys = new ArrayList<String>(keyss);

                for (int i = 0; i < dailies.size(); i++) {
                    imgURLs.add(Uri.parse(dailies.get(i).getImageUri()));
                }

                GridAdapter myAdapter = new GridAdapter(MainActivity.this, imgURLs);
                gridView.setAdapter(myAdapter);

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




        /*
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
        */



        //Login Activity if not logged in
        if(mAuth.getCurrentUser()==null){
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);
            finish(); return;
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, ViewDaily.class);
                myIntent.putExtra("ImageUri", dailies.get(position).getImageUri());
                myIntent.putExtra("Title", dailies.get(position).getTitle());
                myIntent.putExtra("Content", dailies.get(position).getContent());
                myIntent.putExtra("UserEmail", dailies.get(position).getUserEmail());
                myIntent.putExtra("UserNick", dailies.get(position).getUserNick());
                myIntent.putExtra("WriterEmail", dailies.get(position).getWriterEmail());
                myIntent.putExtra("WriterNick", dailies.get(position).getWriterNick());
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
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }






}
