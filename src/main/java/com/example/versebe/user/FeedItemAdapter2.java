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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.versebe.R;

import java.util.ArrayList;

public class FeedItemAdapter2 extends RecyclerView.Adapter implements OnFeedItem2ClickListener{


    Context context;
    ArrayList<FeedItem> items;
    OnFeedItem2ClickListener listener;

    public FeedItemAdapter2(Context context, ArrayList<FeedItem> items)
    {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {


        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_card_layout2, viewGroup,false);

        VH viewHolder = new VH(itemView,this::onItemClick);
        return viewHolder;

    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        FeedItem item = items.get(position);

        Glide.with(context).load(item.getThumb_image()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(vh.image);


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

    //클릭 리스너
    public void setOnItemClicklistener(OnFeedItem2ClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(FeedItemAdapter2.VH holder, View view, int position) {

        if(listener != null){
            listener.onItemClick(holder,view,position);
        }

    }

    public FeedItem getItem(int position){ return items.get(position); }




    public class VH extends RecyclerView.ViewHolder{

        ImageView image;

        public VH(@NonNull View itemView, final OnFeedItem2ClickListener listener)
        {
            super(itemView);
            image = itemView.findViewById(R.id.feed_card_image);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(FeedItemAdapter2.VH.this, v, position);
                    }
                }
            });

        }

    }
}

