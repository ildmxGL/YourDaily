package com.madcamp.yourdaily;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class WriteDailyActivity extends AppCompatActivity {
    private static final String TAG = "WriteDailyActivity";
    private static int ACTIVITY_NUM = 2;
    private Context mContext = WriteDailyActivity.this;

    private GridView writtableGrid;

    private ArrayList<PreDaily> preDailies;
    private ArrayList<String> keys;

    private FirebaseAuth mAuth;

    final ArrayList<Uri> imgURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_daily);

        writtableGrid = (GridView) findViewById(R.id.writtableGrid);

        mAuth = FirebaseAuth.getInstance();

        new FirebaseDatabasePreDaily().readPreDaily(new FirebaseDatabasePreDaily.DataStatus() {
            @Override
            public void DataIsLoaded(List<PreDaily> books, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                preDailies = new ArrayList<PreDaily>(books);
                Log.d(TAG, "DataIsLoaded: dailies recived : " + preDailies.get(0).getImageUri());
                keys = new ArrayList<String>(keyss);

                for (int i = 0; i < preDailies.size(); i++) {
                    imgURLs.add(Uri.parse(preDailies.get(i).getImageUri()));
                }

                GridAdapter myAdapter = new GridAdapter(WriteDailyActivity.this, imgURLs);
                writtableGrid.setAdapter(myAdapter);
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

        writtableGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent (WriteDailyActivity.this, WriteOnDailyActivity.class);
                myIntent.putExtra("ImageUri", preDailies.get(position).getImageUri());
                myIntent.putExtra("Hashtag", preDailies.get(position).getHashtag());
                myIntent.putExtra("Title", preDailies.get(position).getTitle());
                myIntent.putExtra("UserEmail", preDailies.get(position).getUserEmail());
                myIntent.putExtra("UserNick", preDailies.get(position).getUserNick());
                myIntent.putExtra("Key", keys.get(position));
                startActivity(myIntent);

            }
        });

        setupBottomNavigationView();
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
