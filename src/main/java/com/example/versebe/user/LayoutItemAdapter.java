package com.example.versebe.user;

import android.content.Context;
import android.text.Layout;
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

public class LayoutItemAdapter extends RecyclerView.Adapter implements OnLayoutItemClickListener{


    Context context;
    ArrayList<LayoutItem> items;
    OnLayoutItemClickListener listener;

    public LayoutItemAdapter(Context context, ArrayList<LayoutItem> items)
    {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.choice_card_layout, viewGroup,false);

        VH viewHolder = new VH(itemView,this::onItemClick);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        LayoutItem item = items.get(position);

        Glide.with(context).load(item.getThumb_image()).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(vh.image);


    }

    //검색 위한 필터 메소드 추가
    public void  filterList(ArrayList<LayoutItem> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //클릭 리스너
    public void setOnItemClicklistener(OnLayoutItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(LayoutItemAdapter.VH holder, View view, int position) {

        if(listener != null){
            listener.onItemClick(holder,view,position);
        }

    }

    public LayoutItem getItem(int position){ return items.get(position); }




    public class VH extends RecyclerView.ViewHolder{

        ImageView image;

        public VH(@NonNull View itemView, final OnLayoutItemClickListener listener)
        {
            super(itemView);
            image = itemView.findViewById(R.id.layout_image
            );

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(LayoutItemAdapter.VH.this, v, position);
                    }
                }
            });

        }

    }
}

