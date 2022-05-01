package com.example.versebe.user;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.versebe.R;

import java.util.ArrayList;

public class ScrapItemAdapter extends RecyclerView.Adapter{


    Context context;
    ArrayList<ScrapItem> items;

    public ScrapItemAdapter(Context context, ArrayList<ScrapItem> items)
    {
        this.context = context;
        this.items = items;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup,false);
        VH viewHolder = new VH(itemView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        ScrapItem item = items.get(position);
        Glide.with(context).load(item.getImage_path()).into(vh.image);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView image;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.card_image);
            image.setTag("드래그 이미지");
            image.setOnLongClickListener(new MakePosterActivity2.LongClickListener());

        }




    }



}
