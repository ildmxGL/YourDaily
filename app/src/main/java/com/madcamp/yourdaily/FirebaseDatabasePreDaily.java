package com.madcamp.yourdaily;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabasePreDaily {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceBooks;
    private List<PreDaily> preDailies = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<PreDaily> preDailies, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDatabasePreDaily(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceBooks = mDatabase.getReference("preDaily");
    }

    public void readPreDaily(final String UserEmail, final DataStatus dataStatus){
        mReferenceBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                preDailies.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    PreDaily preDaily = keyNode.getValue(PreDaily.class);
                    if(UserEmail.isEmpty()) {
                        preDailies.add(preDaily);
                    } else if (UserEmail.equals(preDaily.getUserEmail())){
                        preDailies.add(preDaily);
                    }
                }
                dataStatus.DataIsLoaded(preDailies, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addPreDaily(PreDaily preDaily, final DataStatus dataStatus){
        String key = mReferenceBooks.push().getKey();
        mReferenceBooks.child(key).setValue(preDaily)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void updatePreDaily(String key, PreDaily preDaily, final DataStatus dataStatus){
        mReferenceBooks.child(key).setValue(preDaily)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deletePreDaily(String key, final DataStatus dataStatus){
        mReferenceBooks.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
