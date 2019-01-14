package com.madcamp.yourdaily;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class ViewDaily extends AppCompatActivity {

    private SimpleDraweeView mainDrawee;
    private TextView mUser;
    private TextView mWriter;
    private TextView mTitle;
    private TextView mContent;

    private Uri mainUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily);

        Intent thisIntent = getIntent();

        mUser = (TextView) findViewById(R.id.user_id);
        mWriter = (TextView) findViewById(R.id.writer_id);
        mTitle = (TextView) findViewById(R.id.title);
        mContent = (TextView) findViewById(R.id.main_text);
        mainDrawee = (SimpleDraweeView) findViewById(R.id.mainDrawee);

        mainUri = Uri.parse(thisIntent.getStringExtra("ImageUri"));
        mainDrawee.setImageURI(mainUri);
        mUser.setText(thisIntent.getStringExtra("UserNick"));
        mWriter.setText(thisIntent.getStringExtra("WriterNick"));
        mTitle.setText(thisIntent.getStringExtra("Title"));
        mContent.setText(thisIntent.getStringExtra("Content"));



    }
}
