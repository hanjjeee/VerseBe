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

public class CommentItemAdapter extends RecyclerView.Adapter{


    Context context;
    ArrayList<CommentItem> items;



    public CommentItemAdapter(Context context, ArrayList<CommentItem> items)
    {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_card_layout, viewGroup,false);
        VH viewHolder = new VH(itemView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        CommentItem item = items.get(position);

        vh.id.setText(item.getId());
        vh.comment.setText(item.getComment());
        Glide.with(context).load(item.getImage_path()).into(vh.image);


    }


    public CommentItem getItem(int position){ return items.get(position); }


    @Override
    public int getItemCount() {

        return items.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        ImageView image;
        TextView id;
        TextView comment;

        public VH(@NonNull View itemView)
        {
            super(itemView);

            id = itemView.findViewById(R.id.comment_id);
            comment = itemView.findViewById(R.id.comment_text);
            image = itemView.findViewById(R.id.comment_imageview);

        }

    }
}
