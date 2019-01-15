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

public class AddDailyNotYetFragment extends Fragment {
    private GridView notYetGrid;
    private static final String TAG = "AddDailyNotYetFragment";
    final ArrayList<Uri> imgURLs = new ArrayList<>();

    private ArrayList<PreDaily> preDailies;
    private FirebaseAuth mAuth;
    private String mEmail;

    private List<String> keys;

    private GridView preDailyView;
    private HashMap<Integer, Integer> keyHash;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_daily_not_yet, container, false);

        preDailies = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mEmail = mAuth.getCurrentUser().getEmail();
        preDailyView = (GridView) view.findViewById(R.id.gridPreDaily);
        keyHash = new HashMap<>();

        new FirebaseDatabasePreDaily().readPreDaily(new FirebaseDatabasePreDaily.DataStatus() {
            @Override
            public void DataIsLoaded(List<PreDaily> books, List<String> keyss) {
                Log.d(TAG, "DataIsLoaded: data Import");
                preDailies = new ArrayList<PreDaily>(books);
                Log.d(TAG, "DataIsLoaded: dailies recived : " + preDailies.get(0).getImageUri());
                keys = new ArrayList<String>(keyss);

                Integer j = 0;
                for (int i = 0; i < preDailies.size(); i++) {
                    if(mEmail.equals(preDailies.get(i).getUserEmail())) {
                        imgURLs.add(Uri.parse(preDailies.get(i).getImageUri()));
                        keyHash.put(j, i);
                        j++;
                    }
                }

                GridAdapter myAdapter = new GridAdapter(getContext(), imgURLs);
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

        preDailyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getContext(), ViewPreDaily.class);
                myIntent.putExtra("ImageUri", preDailies.get(keyHash.get(position)).getImageUri());
                myIntent.putExtra("Title", preDailies.get(keyHash.get(position)).getTitle());
                myIntent.putExtra("Hashtag", preDailies.get(keyHash.get(position)).getHashtag());
                myIntent.putExtra("UserEmail", preDailies.get(keyHash.get(position)).getUserEmail());
                myIntent.putExtra("UserNick", preDailies.get(keyHash.get(position)).getUserNick());
                startActivity(myIntent);
            }
        });
        return view;

    }


}
