package com.example.versebe.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.versebe.R;

import java.util.ArrayList;

public class FeedItemAdapter extends RecyclerView.Adapter{


    Context context;
    ArrayList<FeedItem> items;

    public FeedItemAdapter(Context context, ArrayList<FeedItem> items)
    {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_card_layout, viewGroup,false);

        VH viewHolder = new VH(itemView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        FeedItem item = items.get(position);

        Glide.with(context).load(item.getImage_path()).into(vh.image);


    }

    //검색 위한 필터 메소드 추가
    public void  filterList(ArrayList<FeedItem> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView image;


        public VH(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.feed_card_image);

        }

    }
}

