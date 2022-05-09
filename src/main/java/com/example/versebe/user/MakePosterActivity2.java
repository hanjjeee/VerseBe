package com.example.versebe.user;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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

    private boolean naver_flag;
    private boolean google_flag;
    private boolean daum_flag;
    private String store_name;

     TextView page_storename;
     TextView page_name;
     TextView page_category;
     TextView page_location;
     TextView page_tel;


    String page_name_s;
    String page_location_s;
    String page_category_s;
    String page_tel_s;







    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_poster);

        intent = getIntent();
        cur_user_id = intent.getExtras().getString("cur_user_id");
        naver_flag = intent.getExtras().getBoolean("naver_flag");
        google_flag = intent.getExtras().getBoolean("google_flag");
        daum_flag = intent.getExtras().getBoolean("daum_flag");
        store_name = intent.getExtras().getString("store_name");

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


         page_name_s="";
         page_location_s="";
         page_category_s="";
         page_tel_s="";





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

        //포스터 레이아웃 지정하기
        if(article_num==1){

            System.out.println("article 1");

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

            //텍스트 구역
            page_name = findViewById(R.id.page_name);
            page_storename = findViewById(R.id.page_storename);
            page_tel = findViewById(R.id.page_tel);
            page_category = findViewById(R.id.page_category);
            page_location = findViewById(R.id.page_location);



            /*
            for(int i=0;i<img_arr.length;i++){

                ScrapItem item = new ScrapItem(img_arr[i].toString());

                items.add(0, item);
                adapter.notifyItemInserted(0);
            }*/







        }
        //포스터 레이아웃 지정



        //크롤링 시작
        //네이버
        if(naver_flag==true){

            String url = "https://m.search.naver.com/search.naver?sm=mtb_hty.top&where=m&oquery=d&tqi=hFP7ksprv2NssS%2Bi1MsssssssA4-057492&query="
                    +store_name;



            System.out.println(store_name);

            //이미지 개수 가져오기
            //쓰레드 시작
            new Thread(new Runnable() {

                @Override public void run() {


                    System.out.println("thread start..");

                    try {

                        Document document = Jsoup.connect(url).get();
                        Element location_element = document.select("#place-main-section-root > div > div.place_section.no_margin._18vYz > div > ul > li._1M_Iz._1aj6- > div > a > span._2yqUQ").first();

                        Element category_element = document.select("#_title > a > span._3ocDE").first();
                        Element name_element = document.select("#_title > a > span._3XamX").first();
                        Element tel_element = document.select("#place_main_ct > div > section > div > div.ct_box_area > div.bizinfo_area > div > div:nth-child(1) > div").first();
                        Elements tel_elements = document.getElementsByClass(".ct_box_area");

                        //test
                        //System.out.println(tel_elements.text().toString());

                        //Elements img_elements = document.getElementsByAttributeValueContaining("src","https://search.pstatic.net/");
                        Element img_elements = document.getElementById("ibu_1");

                        //item_num = img_elements.size();
                        //System.out.println(item_num+"개");







                        if(location_element!=null){
                            page_location_s += location_element.text().toString();
                            System.out.println(page_location_s);

                        }

                        if(category_element!=null){
                            page_category_s += category_element.text().toString();
                            System.out.println(page_category_s);
                        }

                        if(tel_element!=null){
                            page_tel_s += tel_element.text().toString();
                            System.out.println(page_tel_s);
                        }

                        if(name_element!=null){
                            page_name_s += name_element.text().toString();
                            System.out.println(page_name_s);
                        }


                        //이미지
                        if(img_elements!=null){

                            System.out.println("img for문 전");

                            for(int i=1;i<=item_num;i++){
                                //Element img_element =
                                //System.out.println( img_elements.get(i).toString());
                                System.out.println(img_elements.toString());
                            }
                        }


                        runOnUiThread(new Runnable() { public void run() {

                        //뷰 세팅
                        if(!page_location_s.isEmpty()){
                            page_location.setText("Loc. "+page_location_s);
                        }
                        if(!page_category_s.isEmpty()){
                            page_category.setText("Category. "+page_category_s);
                        }
                        if(!page_name_s.isEmpty()){
                            page_name.setText("Name. "+page_name_s);
                            page_storename.setText(page_name_s);
                        }
                        if(!page_tel_s.isEmpty()){
                            page_tel.setText("#. "+page_tel_s);
                        }

                        } });




                    }catch(IOException e){
                        e.printStackTrace();

                    }

                }//end of run



            }).start();

            //쓰레드 끝













        }//네이버 크롤링 끝







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