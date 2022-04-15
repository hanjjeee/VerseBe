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

public class FollowItemAdapter extends RecyclerView.Adapter implements OnMemberItemClickListener{


    Context context;
    ArrayList<FollowItem> items;
    OnMemberItemClickListener listener;


    public FollowItemAdapter(Context context, ArrayList<FollowItem> items)
    {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.follow_card_layout, viewGroup,false);
        VH viewHolder = new VH(itemView,this::onItemClick);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VH vh = (VH)viewHolder;
        FollowItem item = items.get(position);
        vh.id.setText(item.getId());
        vh.email.setText(item.getDetail());
        Glide.with(context).load(item.getImage_path()).into(vh.image);


    }

    //검색 위한 필터 메소드 추가
    public void  filterList(ArrayList<FollowItem> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

    //클릭 리스너
    public void setOnItemClicklistener(OnMemberItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(VH holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public FollowItem getItem(int position){ return items.get(position); }




    @Override
    public int getItemCount() {

        return items.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        ImageView image;
        TextView id;
        TextView email;

        public VH(@NonNull View itemView,final OnMemberItemClickListener listener)
        {
            super(itemView);

            id = itemView.findViewById(R.id.follow_card_id);
            email = itemView.findViewById(R.id.follow_card_email);
            image = itemView.findViewById(R.id.follow_card_image);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(VH.this, v, position);
                    }
                }
            });



        }

    }
}
