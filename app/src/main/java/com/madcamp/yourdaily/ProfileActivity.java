package com.madcamp.yourdaily;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext = ProfileActivity.this;

    private SimpleDraweeView profileImage;
    private TextView posts;
    private TextView writes;
    private TextView nickname;
    private List<String> friendEmails;

    private ArrayList<DailyCard> myDailies;
    private ArrayList<Profile> currentProfiles;
    private Profile currentProfile;

    private FirebaseAuth mAuth;
    private ArrayList<String> keys;
    private String mEmail;

    private int postnum = 0;
    private int writenum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupBottomNavigationView();

        profileImage = (SimpleDraweeView) findViewById(R.id.profile_image);
        posts = (TextView) findViewById(R.id.tvPosts);
        writes = (TextView) findViewById(R.id.tvWrites);
        nickname = (TextView) findViewById(R.id.nick_textview);

        mAuth = FirebaseAuth.getInstance();
        currentProfiles = new ArrayList<Profile>();
        currentProfile = new Profile();
        myDailies = new ArrayList<>();
        mEmail = mAuth.getCurrentUser().getEmail();

        new FirebaseDatabaseProfile().readProfiles( new FirebaseDatabaseProfile.DataStatus() {
            @Override
            public void DataIsLoaded(List<Profile> profiles, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                currentProfiles = new ArrayList<Profile>(profiles);
                keys = new ArrayList<String>(keyss);

                for (int i = 0; i < currentProfiles.size(); i++) {
                    if (currentProfiles.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail())){
                        currentProfile=currentProfiles.get(i);
                        break;
                    }
                }

                profileImage.setImageURI(Uri.parse(currentProfile.getProfileImage()));
                //posts.setText(String.valueOf(currentProfile.getPosts()));
                //writes.setText(String.valueOf(currentProfile.getPosts()));
                nickname.setText(currentProfile.getNick());
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

        new FirebaseDatabaseDailyCard().readBooks(new FirebaseDatabaseDailyCard.DataStatus() {
            @Override
            public void DataIsLoaded(List<DailyCard> books, List<String> keys) {
                myDailies = new ArrayList<>(books);

                for (int i=0; i<myDailies.size(); i++){
                    if(mEmail.equals(myDailies.get(i).getUserEmail())){
                        postnum++;
                    }
                    if(mEmail.equals(myDailies.get(i).getWriterEmail())){
                        writenum++;
                    }
                }

                posts.setText(String.valueOf(postnum));
                writes.setText(String.valueOf(writenum));

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
