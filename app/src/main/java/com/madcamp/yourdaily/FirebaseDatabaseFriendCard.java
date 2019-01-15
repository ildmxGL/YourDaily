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

public class FirebaseDatabaseFriendCard {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceBooks;
    private List<FriendCard> friendCards = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<FriendCard> friendCards, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDatabaseFriendCard(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceBooks = mDatabase.getReference("FriendCard");
    }

    public void readFriendCards(final DataStatus dataStatus){
        mReferenceBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendCards.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    FriendCard friendCard = keyNode.getValue(FriendCard.class);
                        friendCards.add(friendCard);
                }
                dataStatus.DataIsLoaded(friendCards, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addFriendCard(FriendCard friendCard, final DataStatus dataStatus){
        String key = mReferenceBooks.push().getKey();
        mReferenceBooks.child(key).setValue(friendCard)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void updateFriendCard(String key, FriendCard friendCard, final DataStatus dataStatus){
        mReferenceBooks.child(key).setValue(friendCard)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteFriendCard(String key, final DataStatus dataStatus){
        mReferenceBooks.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
