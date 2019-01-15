package com.madcamp.yourdaily;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddDailyCompleteFragment extends Fragment {

    private GridView completeDiaryGrid;
    private static final String TAG = "AddDailyCompleteFragmen";

    private ArrayList<Uri> imgURLs;
    private ArrayList<DailyCard> dailies;
    private ArrayList<String> keys;
    private String mEmail;
    private HashMap<Integer, Integer> keyHash;

    FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_daily_complete, container, false);

        completeDiaryGrid = (GridView) view.findViewById(R.id.gridDaily);

        mAuth = FirebaseAuth.getInstance();
        mEmail = mAuth.getCurrentUser().getEmail();
        imgURLs = new ArrayList<>();
        keyHash = new HashMap<>();

        new FirebaseDatabaseDailyCard().readBooks( new FirebaseDatabaseDailyCard.DataStatus() {
            @Override
            public void DataIsLoaded(List<DailyCard> books, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                dailies = new ArrayList<DailyCard>(books);
                //Log.d(TAG, "DataIsLoaded: dailies recived : " + dailies.get(0).getImageUri());
                keys = new ArrayList<String>(keyss);

                Integer j = 0;
                for (int i = 0; i < dailies.size(); i++) {
                    if(mEmail.equals(dailies.get(i).getUserEmail())) {
                        imgURLs.add(Uri.parse(dailies.get(i).getImageUri()));
                        keyHash.put(j, i);
                        j++;
                    }
                }

                GridAdapter myAdapter = new GridAdapter(getContext(), imgURLs);
                completeDiaryGrid.setAdapter(myAdapter);

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

        completeDiaryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getContext(), ViewDaily.class);
                myIntent.putExtra("ImageUri", dailies.get(keyHash.get(position)).getImageUri());
                myIntent.putExtra("Title", dailies.get(keyHash.get(position)).getTitle());
                myIntent.putExtra("UserEmail", dailies.get(keyHash.get(position)).getUserEmail());
                myIntent.putExtra("UserNick", dailies.get(keyHash.get(position)).getUserNick());
                myIntent.putExtra("WriterEmail", dailies.get(keyHash.get(position)).getWriterEmail());
                myIntent.putExtra("WriterNick", dailies.get(keyHash.get(position)).getWriterNick());
                myIntent.putExtra("Content", dailies.get(keyHash.get(position)).getContent());
                startActivity(myIntent);
            }
        });


        return view;
    }


}
