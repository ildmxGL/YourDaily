package com.madcamp.yourdaily;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendCardAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<FriendCard> FriendCards;
    SimpleDraweeView drawee;
    ImageView card1;
    ImageView card2;
    ImageView card3;

    public FriendCardAdapter(Context context, ArrayList<FriendCard> FriendCards){
        this.mContext = context;
        this.FriendCards = FriendCards;
    }

    @Override
    public int getCount() {
        return FriendCards.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final FriendCard thisCard = FriendCards.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_profile_friend, null);
        }

        drawee = (SimpleDraweeView) convertView.findViewById(R.id.profile_image);
        card1 =  (ImageView) convertView.findViewById(R.id.drawee1);
        card2 =  (ImageView) convertView.findViewById(R.id.drawee2);
        card3 =  (ImageView) convertView.findViewById(R.id.drawee3);

        drawee.setImageURI(Uri.parse(thisCard.getProfileUri()));
        if(!thisCard.getCard1Uri().isEmpty()){
            card1.setImageURI(Uri.parse(thisCard.getCard1Uri()));
        }
        if(!thisCard.getCard2Uri().isEmpty()){
            card1.setImageURI(Uri.parse(thisCard.getCard2Uri()));
        }
        if(!thisCard.getCard3Uri().isEmpty()){
            card1.setImageURI(Uri.parse(thisCard.getCard3Uri()));
        }

        return convertView;
    }



}
