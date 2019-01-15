package com.madcamp.yourdaily;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class WriteOnDailyActivity extends AppCompatActivity {
    private static final String TAG = "WriteOnDailyActivity";

    private SimpleDraweeView toWriteDrawee;
    private TextView getTitle;
    private TextView getHashtag;
    private EditText contentET;
    private Button submitButton;

    private Intent mIntent;
    private FirebaseAuth mAuth;

    private String ProfileUri;
    private FriendCard mFriendCard;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_on_daily);

        toWriteDrawee = (SimpleDraweeView) findViewById(R.id.to_write_drawee);
        getTitle = (TextView) findViewById(R.id.title_content);
        getHashtag = (TextView) findViewById(R.id.hash_content);
        contentET = (EditText) findViewById(R.id.content_edittext);

        mIntent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mFriendCard = new FriendCard();

        toWriteDrawee.setImageURI(Uri.parse(mIntent.getStringExtra("ImageUri")));
        getTitle.setText(mIntent.getStringExtra("Title"));
        getHashtag.setText(mIntent.getStringExtra("Hashtag"));
        ProfileUri = "";
        key = "";

        submitButton = (Button)findViewById(R.id.submitDailyButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DailyCard daily = new DailyCard();
                daily.setContent(contentET.getText().toString());
                daily.setImageUri(mIntent.getStringExtra("ImageUri"));
                daily.setTitle(mIntent.getStringExtra("Title"));
                daily.setUserEmail(mIntent.getStringExtra("UserEmail"));
                daily.setUserNick(mIntent.getStringExtra("UserNick"));
                daily.setWriterEmail(mAuth.getCurrentUser().getEmail());
                daily.setWriterNick("");
                new FirebaseDatabaseDailyCard().addBook(daily, new FirebaseDatabaseDailyCard.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<DailyCard> Dailies, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(WriteOnDailyActivity.this, "Accepted Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });


                new FirebaseDatabaseFriendCard().readFriendCards(new FirebaseDatabaseFriendCard.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<FriendCard> friendCards, List<String> keys) {
                        Log.d(TAG, "DataIsLoaded: Loop Point3");
                        for (int i=0; i<friendCards.size(); i++){
                            if(friendCards.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                key = keys.get(i);
                                mFriendCard = friendCards.get(i);
                                if (mFriendCard.getCard1Uri().isEmpty()){
                                    mFriendCard.setCard1Uri(mIntent.getStringExtra("ImageUri"));
                                }else if (mFriendCard.getCard2Uri().isEmpty()){
                                    mFriendCard.setCard2Uri(mIntent.getStringExtra("ImageUri"));
                                }else if (mFriendCard.getCard3Uri().isEmpty()){
                                    mFriendCard.setCard3Uri(mIntent.getStringExtra("ImageUri"));
                                }

                                Log.d(TAG, "DataIsLoaded: Loop Point 1");
                                new FirebaseDatabaseFriendCard().updateFriendCard(key, mFriendCard, new FirebaseDatabaseFriendCard.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(List<FriendCard> friendCards, List<String> keys) {

                                    }

                                    @Override
                                    public void DataIsInserted() {

                                    }

                                    @Override
                                    public void DataIsUpdated() {
                                        Log.d(TAG, "DataIsUpdated: Loop point2");
                                    }

                                    @Override
                                    public void DataIsDeleted() {

                                    }
                                });
                                break;
                            }
                        }



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



                finish();
            }
        });
    }
}
