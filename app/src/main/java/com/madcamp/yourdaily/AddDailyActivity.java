package com.madcamp.yourdaily;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;
import java.util.List;

public class AddDailyActivity extends AppCompatActivity {

    private final int ACTIVITY_NUM = 1;
    private Context mContext = AddDailyActivity.this;
    private static final String TAG = "AddDailyActivity";

//    private ImageButton addImageButton;
    private ArrayList<PreDaily> preDailies;
//    private ArrayList<String> keys;

    private GridView preDailyView;
    private GridView DailyView;

    final ArrayList<Uri> imgURLs = new ArrayList<>();

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new AddDailyCompleteFragment(), "Complete");
        adapter.addFragment(new AddDailyNotYetFragment(), "Not yet");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        setupBottomNavigationView();

        mAuth = FirebaseAuth.getInstance();

        preDailies = new ArrayList<>();

//        addImageButton = (ImageButton) findViewById(R.id.AddDailyButton);
//
//        preDailyView = (GridView) findViewById(R.id.gridPreDaily);
//        DailyView = (GridView) findViewById(R.id.gridDaily);

//        addImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(AddDailyActivity.this, AddPreDaily.class);
//                startActivity(myIntent);
//            }
//        });

        init();

    }

    private void init() {
        new FirebaseDatabasePreDaily().readPreDaily(mAuth.getCurrentUser().getEmail(), new FirebaseDatabasePreDaily.DataStatus() {
            @Override
            public void DataIsLoaded(List<PreDaily> books, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                preDailies = new ArrayList<PreDaily>(books);
                Log.d(TAG, "DataIsLoaded: dailies recived : " + preDailies.get(0).getImageUri());
                keys = new ArrayList<String>(keyss);

                for (int i = 0; i < preDailies.size(); i++) {
                    imgURLs.add(Uri.parse(preDailies.get(i).getImageUri()));
                }

                GridAdapter myAdapter = new GridAdapter(AddDailyActivity.this, imgURLs);
                preDailyView.setAdapter(myAdapter);
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
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
