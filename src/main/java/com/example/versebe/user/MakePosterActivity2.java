package com.example.versebe.user;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//make poster
public class MakePosterActivity2 extends AppCompatActivity {

    private Intent intent;
    private RecyclerView recyclerView;
    private ScrapItemAdapter adapter;
    private ArrayList<ScrapItem> items;
    private LinearLayoutManager layoutManager;

    private int item_num;
    private String tmp;

    private String cur_user_id;

    private String type;
    private int layout_num;
    private String thumb_image;
    private String user_id;
    private String update_date;
    private String last_date;
    private int article_num;
    private String hash_tag;
    private String layout_image;
    private String title;





    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_poster);

        intent = getIntent();
        cur_user_id = intent.getExtras().getString("cur_user_id");

        type = intent.getExtras().getString("TYPE");
        layout_num = intent.getExtras().getInt("LAYOUT_NUM");;
        thumb_image= intent.getExtras().getString("THUMBNAIL");;
        user_id = intent.getExtras().getString("USER_ID");;
        update_date= intent.getExtras().getString("UPDATE_DATE");;
        last_date = intent.getExtras().getString("LAST_DATE");;
        article_num = intent.getExtras().getInt("ARTICLE_NUM");;
        hash_tag = intent.getExtras().getString("HASH_TAG");;
        layout_image = intent.getExtras().getString("LAYOUT_IMAGE");;
        title = intent.getExtras().getString("TITLE");



        //리사이클러뷰
        //그리드 리사이클러 뷰 불러오기
        items = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.image_recyclerview);

        adapter = new ScrapItemAdapter(this, items);

        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        item_num=10;

        items.clear();

        adapter.notifyDataSetChanged();

        for(int i=0;i<item_num;i++){

            tmp="https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200702_194%2F1593670875426adwKT_JPEG%2FUCqWaYWBENm8UxSWZemPkIff.jpg";
            ScrapItem item = new ScrapItem(tmp);

            items.add(0, item);
            adapter.notifyItemInserted(0);

        }

        //if(layout_num==1){

            //버튼 클릭시 해당 xml 가져오기
            Sub n_layout = new Sub(getApplicationContext(), 1);
            LinearLayout con = (LinearLayout)findViewById(R.id.poster_view_l);
            con.addView(n_layout);


            //드래그 앤 드롭 구역
            findViewById(R.id.imageView_1).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_2).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_3).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_4).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_5).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_6).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_7).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_8).setOnDragListener(  new DragListener());
            findViewById(R.id.imageView_9).setOnDragListener(  new DragListener());

       // }



    }


    //드래그 앤 드롭 구현
    public static class LongClickListener implements View.OnLongClickListener {

        public boolean onLongClick(View view) {

            // 태그 생성
            ClipData.Item item = new ClipData.Item(
                    (CharSequence) view.getTag());

            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(view.getTag().toString(),
                    mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);

            view.startDrag(data, // data to be dragged
                    shadowBuilder, // drag shadow
                    view, // 드래그 드랍할  Vew
                    0 // 필요없은 플래그
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class DragListener implements View.OnDragListener {
        Drawable normalShape = getResources().getDrawable(
                R.color.light_gray2);
        Drawable targetShape = getResources().getDrawable(
                R.color.black);

        @SuppressLint("NewApi")
        @RequiresApi(api = Build.VERSION_CODES.M)
        public boolean onDrag(View v, DragEvent event) {

            // 이벤트 시작
            switch (event.getAction()) {

                // 이미지를 드래그 시작될때
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;

                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");

                    // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
                    if(v.getId() != findViewById(R.id.image_recyclerview).getId())
                        v.setBackground(targetShape);
                    break;

                // 드래그한 이미지가 영역을 빠져 나갈때
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");

                    if(v.getId() != findViewById(R.id.image_recyclerview).getId())
                        v.setBackground(normalShape);

                    break;

                // 이미지를 드래그해서 드랍시켰을때
                case DragEvent.ACTION_DROP:

                    Log.d("DragClickListener", "ACTION_DROP");

                    if ( v == findViewById(R.id.imageView_1) ||
                            v == findViewById(R.id.imageView_2) ||
                            v == findViewById(R.id.imageView_3)||
                            v == findViewById(R.id.imageView_4)||
                            v == findViewById(R.id.imageView_5)||
                            v == findViewById(R.id.imageView_6)||
                            v == findViewById(R.id.imageView_7)||
                            v == findViewById(R.id.imageView_8)||
                            v == findViewById(R.id.imageView_9)






                    ){

                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);

                        // change the text
                        /*
                        TextView text = (TextView) v
                                .findViewById(R.id.text);
                        text.setText("이미지가 드랍되었습니다.");
                        */



                        FrameLayout containView = (FrameLayout) v;


                        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(containView.getMeasuredWidth(),containView.getMeasuredHeight());

                        view.setLayoutParams(p);

                        containView.addView(view);

                        view.setVisibility(View.VISIBLE);

                    }else if (v == findViewById(R.id.image_recyclerview)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view
                                .getParent();
                        viewgroup.removeView(view);


                        view.setVisibility(View.INVISIBLE);

                    }else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context,
                                "이미지를 다른 지역에 드랍할수 없습니다.",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
                    if(!(v.getId() == findViewById(R.id.image_recyclerview).getId()))
                        v.setBackground(normalShape); // go back to normal shape

                default:
                    break;
            }
            return true;
        }
    }



}