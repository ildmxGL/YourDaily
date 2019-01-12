package com.madcamp.yourdaily;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Uri> Uris;
    SimpleDraweeView drawee;

    public GridAdapter(Context context, ArrayList<Uri> Uris){
        this.mContext = context;
        this.Uris = Uris;
    }

    @Override
    public int getCount() {
        return Uris.size();
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
        final Uri uri = Uris.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_grid_imageview, null);
        }

        drawee = (SimpleDraweeView) convertView.findViewById(R.id.gridSimpleDrawee);
        drawee.setImageURI(uri);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(21f);
        drawee.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER);


        drawee.setHierarchy(new GenericDraweeHierarchyBuilder(mContext.getResources())
                .setRoundingParams(roundingParams)
                .build());


        return convertView;
    }

    void updateViewSize(@Nullable ImageInfo imageInfo) {
        if (imageInfo != null) {
            drawee.getLayoutParams().width = imageInfo.getWidth();
            drawee.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            drawee.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }







}
