package com.madcamp.yourdaily;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class ViewDaily extends AppCompatActivity {

    private SimpleDraweeView mainDrawee;
    private TextView mWriter;
    private TextView mTitle;
    private TextView mContent;

    private Uri mainUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily);

        Intent thisIntent = getIntent();

        mWriter = (TextView) findViewById(R.id.text_author);
        mTitle = (TextView) findViewById(R.id.text_title);
        mContent = (TextView) findViewById(R.id.text_context);
        mainDrawee = (SimpleDraweeView) findViewById(R.id.mainDrawee);

        mainUri = Uri.parse(thisIntent.getStringExtra("ImageUri"));
        mainDrawee.setImageURI(mainUri);
        mWriter.setText(thisIntent.getStringExtra("WriterNick"));
        mTitle.setText(thisIntent.getStringExtra("Title"));
        mContent.setText(thisIntent.getStringExtra("Content"));



    }
}
