package com.madcamp.yourdaily;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;
import java.util.ArrayList;

public class ImageRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView pic;

        MyViewHolder(View view){
            super(view);
            pic = view.findViewById(R.id.recycle_drawee);
        }
    }

    private ArrayList<Uri> draweeArrayList;

    ImageRecycleAdapter(ArrayList<Uri> draweeArrayList){
        this.draweeArrayList = draweeArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_image, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.pic.setImageURI(draweeArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return draweeArrayList.size();
    }
}
