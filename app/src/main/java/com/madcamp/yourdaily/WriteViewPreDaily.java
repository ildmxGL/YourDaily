package com.madcamp.yourdaily;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class WriteViewPreDaily extends AppCompatActivity {

    private SimpleDraweeView mainDrawee;
    private TextView mUser;
    private TextView mTitle;
    private TextView mContent;

    private Uri mainUri;
    private FloatingActionButton write_daily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_view_pre_daily);

        final Intent thisIntent = getIntent();

        mUser = (TextView) findViewById(R.id.text_author);
        mTitle = (TextView) findViewById(R.id.text_title);
        mContent = (TextView) findViewById(R.id.text_hash);
        mainDrawee = (SimpleDraweeView) findViewById(R.id.mainDrawee);
        write_daily = (FloatingActionButton) findViewById(R.id.write_on_daily_button);

        mainUri = Uri.parse(thisIntent.getStringExtra("ImageUri"));
        mainDrawee.setImageURI(mainUri);
        mUser.setText(thisIntent.getStringExtra("UserNick"));
        mTitle.setText(thisIntent.getStringExtra("Title"));
        mContent.setText(thisIntent.getStringExtra("Hashtag"));

        write_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WriteViewPreDaily.this, WriteOnDailyActivity.class);
                myIntent.putExtra("ImageUri", thisIntent.getStringExtra("ImageUri"));
                myIntent.putExtra("UserNick", thisIntent.getStringExtra("UserNick"));
                myIntent.putExtra("Title", thisIntent.getStringExtra("Title"));
                myIntent.putExtra("Hashtag", thisIntent.getStringExtra("Hashtag"));
                startActivity(myIntent);
            }
        });



    }
}
