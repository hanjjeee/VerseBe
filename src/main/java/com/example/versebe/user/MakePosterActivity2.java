package com.example.versebe.user;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

//make poster
public class MakePosterActivity2 extends AppCompatActivity {

    private Intent intent;
    private RecyclerView recyclerView;
    private ScrapItemAdapter adapter;
    private ArrayList<ScrapItem> items;
    private LinearLayoutManager layoutManager;

    private Button poster_save_button;



    private String cur_user_id;

    private String type;
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

    private static final String url_upload = "http://hanjiyoon.dothome.co.kr/upload_poster.php";
    String encodeImageString;
    FileOutputStream fos;
    Bitmap bitmap, bitmap_server;
    String insert_title;
    String insert_content;
    String insert_hashtag;

    int check_index;



    TextView page_storename;
    TextView page_name;
    TextView page_category;
    TextView page_location;
    TextView page_tel;


    String page_name_s;
    String page_location_s;
    String page_category_s;
    String page_tel_s;

    List<String> img_array;

    Element location_element;
    Element category_element;
    Element name_element;
    Element tel_element;







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
        thumb_image= intent.getExtras().getString("THUMBNAIL");;
        user_id = intent.getExtras().getString("USER_ID");;
        update_date= intent.getExtras().getString("UPDATE_DATE");;
        last_date = intent.getExtras().getString("LAST_DATE");;
        article_num = intent.getExtras().getInt("ARTICLE_NUM");;
        hash_tag = intent.getExtras().getString("HASH_TAG");;
        layout_image = intent.getExtras().getString("LAYOUT_IMAGE");;
        title = intent.getExtras().getString("TITLE");


        //텍스트 뷰에 넣을 스트링 - 연결 위해 초기화
        page_name_s="";
        page_location_s="";
        page_category_s="";
        page_tel_s="";


        //이미지 주소 어레이 리스트
        img_array = new ArrayList<>();


        //리사이클러뷰
        //그리드 리사이클러 뷰 불러오기
        items = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.image_recyclerview);
        poster_save_button = findViewById(R.id.poster_save_button);

        adapter = new ScrapItemAdapter(this, items);

        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        items.clear();

        adapter.notifyDataSetChanged();


        //버튼 클릭시 해당 xml 가져오기
        Sub n_layout = new Sub(getApplicationContext(),article_num);
        LinearLayout con = (LinearLayout)findViewById(R.id.poster_view_l);
        con.addView(n_layout);

        //드래그 앤 드롭 구역
        findViewById(R.id.imageView_1).setOnDragListener(  new DragListener());
        findViewById(R.id.imageView_2).setOnDragListener(  new DragListener());
        findViewById(R.id.imageView_3).setOnDragListener(  new DragListener());
        findViewById(R.id.imageView_4).setOnDragListener(  new DragListener());
        findViewById(R.id.imageView_5).setOnDragListener(  new DragListener());
        findViewById(R.id.imageView_6).setOnDragListener(  new DragListener());



        findViewById(R.id.image_recyclerview).setOnDragListener(  new DragListener());

        //텍스트 구역
        page_name = findViewById(R.id.page_name);
        page_storename = findViewById(R.id.page_storename);
        page_tel = findViewById(R.id.page_tel);
        page_category = findViewById(R.id.page_category);
        page_location = findViewById(R.id.page_location);




        //갤러리 연동
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            //Uri uri = intent.getData();
                            try{
                                InputStream in = getContentResolver().openInputStream(intent.getData());

                                Bitmap img = BitmapFactory.decodeStream(in);
                                in.close();

                                //가져온 사진 처리 **camera 1-6 수정
                                ImageView tmp=null;

                                if(check_index == 1){
                                    tmp = findViewById(R.id.camera1);
                                }
                                else if(check_index == 2){
                                    tmp = findViewById(R.id.camera2);
                                }
                                else if(check_index == 3){
                                    tmp = findViewById(R.id.camera3);
                                }
                                else if(check_index == 4){
                                    tmp = findViewById(R.id.camera4);
                                }
                                else if(check_index == 5){
                                    tmp = findViewById(R.id.camera5);
                                }
                                else if(check_index == 6){
                                    tmp = findViewById(R.id.camera6);
                                }

                                tmp.setImageBitmap(img);


                            }catch(Exception e)
                            {

                            }
                        }
                    }
                }
        );


        //갤러리에서 가져오기 버튼
        //1-6 추가
        findViewById(R.id.imageView_1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=1;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });
        findViewById(R.id.imageView_2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=2;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });
        findViewById(R.id.imageView_3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=3;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });
        findViewById(R.id.imageView_4).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=4;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });
        findViewById(R.id.imageView_5).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=5;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });
        findViewById(R.id.imageView_6).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 열리는지 테스트
                //갤러리 호출
                check_index=6;

                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent1);


            }
        });







        //크롤링 시작 : 이미지 추출은 업체출처가 확실하고 사진 정보가 많은 네이버로 고정

        //네이버 크롤링
        if(naver_flag==true){

            //이미지 src 초기화
            img_array.clear();

            //텍스트뷰 추출 위한 url
            String url = "https://m.search.naver.com/search.naver?sm=mtb_hty.top&where=m&oquery=d&tqi=hFP7ksprv2NssS%2Bi1MsssssssA4-057492&query="
                    +store_name;

            //이미지 src 추출 위한 url (이미지-플레이스검색.플레이스는 업체 등록 이미지)
            String url2 = "https://m.search.naver.com/search.naver?where=m_image&mode=imgonly&section=place&query="+store_name;


            //입력받고 전달받은 상호명 확인
            System.out.println(store_name);


            //이미지 개수 가져오기
            //쓰레드 시작
            new Thread(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override public void run() {


                    System.out.println("thread start..");

                    try {

                        //텍스트 위한 document 객체
                        Document document = Jsoup.connect(url).get();
                        //이미지 위한 document 객체
                        Document document2 = Jsoup.connect(url2).get();

                        //기본
                        location_element = document.select("#place-main-section-root > div > div.place_section.no_margin._18vYz > div > ul > li._1M_Iz._1aj6- > div > a > span._2yqUQ").first();
                        category_element = document.select("#_title > a > span._3ocDE").first();
                        name_element = document.select("#_title > a > span._3XamX").first();
                        tel_element = document.select("#place-main-section-root > div > div.place_section.GCwOh > div._374df > div > span:nth-child(1) > a").first();
                        //없는 경우 2차
                        if(location_element==null){
                            location_element = document.select("#loc-main-section-root > div > div:nth-child(5) > ul > li > div._3ZU00._1rBq3 > div > div > span:nth-child(2) > a > span._3hCbH").first();
                        }
                        if(name_element==null){
                            name_element= document.select("#loc-main-section-root > div > div:nth-child(5) > ul > li > div._3ZU00._1rBq3 > a:nth-child(1) > div > div > span._3Apve > mark").first();
                        }
                        if(category_element==null){
                            category_element = document.select("#loc-main-section-root > div > div:nth-child(5) > ul > li > div._3ZU00._1rBq3 > a:nth-child(1) > div > div > span._3B6hV").first();
                        }
                        if(tel_element==null){
                            tel_element =document.select("#loc-main-section-root > div > div:nth-child(5) > ul > li > div._1z35p > div > div > span:nth-child(1) > a").first();
                        }




                        //이미지 페이지 all-test
                        Elements testing = document2.getAllElements();


                        //안나오는 정보 테스팅
                        /*
                        Elements testing2 = document.getAllElements();
                        for(int i=0;i<testing2.size();i++){
                            System.out.println(testing2.get(i).toString());
                        }
                        */


                        //전체 요소 확인, 1개로 추출되는 스크립트 사진 데이터 저장
                        String img_data = "";

                        for(int i=0;i<testing.size();i++){

                            String tmp = testing.get(i).toString();
                            //System.out.println(tmp);

                            if(tmp.contains("var data")){
                                img_data=tmp;
                            }

                        }

                        //성공
                        System.out.println("img_data: " + img_data);


                        //토큰 분리 "" 로 분리 후 https 포함하는 요소를 추가, 어레이테 본 주소 전체 저장
                        StringTokenizer st = new StringTokenizer(img_data,"\"");

                        while(st.hasMoreTokens()) {

                            String token=st.nextToken();

                            if( token.contains("https") && (token.contains(".jpg")||token.contains(".png")) && !token.contains("3Dsc960_832") &&!token.contains("3Da340") ) {

                                img_array.add("https://search.pstatic.net/common/?src="+token);
                            }

                        }

                        //img_array=img_array.stream().distinct().collect(Collectors.toList());

                        for(int i=0;i<img_array.size();i++){
                            System.out.println(img_array.get(i));
                        }



                        //추출한 위치 정보 존재하는 경우 위치 스트링에 문자열 저장
                        if(location_element!=null){
                            page_location_s += location_element.text().toString();
                            System.out.println(page_location_s);

                        }


                        //추출한 카테고리 정보 존재하는 경우 카테고리 스트링에 문자열 저장
                        if(category_element!=null){
                            page_category_s += category_element.text().toString();
                            System.out.println(page_category_s);
                        }

                        //추출한 전화번호 정보 존재하는 경우 전화번호 스트링에 문자열 저장
                        if(tel_element!=null){

                            page_tel_s += tel_element.attr("href");
                            System.out.println(page_tel_s);
                        }

                        //추출한 이름 정보 존재하는 경우 이름 스트링에 문자열 저장
                        if(name_element!=null){
                            page_name_s += name_element.text().toString();
                            System.out.println(page_name_s);
                        }





                        //UI 뷰 세팅 위한 thread
                        runOnUiThread(new Runnable() {
                            public void run() {

                            //오류 출력
                            //오류 출력
                            //일부 정보 크롤링 실패
                            if(location_element==null||category_element==null||name_element==null||
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "일부 텍스트 정보는 가져오지 못했어요!", Toast.LENGTH_SHORT).show();

                            }

                            //전체 정보 크롤링 실패
                            if(location_element==null&&category_element==null&&name_element==null&&
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "등록된 정보가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }

                            //이미지 크롤링 실패
                            if(img_array.isEmpty()){
                                Toast.makeText(getApplicationContext(), "등록된 이미지가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }

                            //스트링이 존재한다면 위치 뷰 세팅
                            if(!page_location_s.isEmpty()){
                                page_location.setText("Loc. "+page_location_s);
                            }

                            //스트링이 존재한다면 카테고리 뷰 세팅
                            if(!page_category_s.isEmpty()){
                                page_category.setText("Category. "+page_category_s);
                            }

                            //스트링이 존재한다면 이름, 제목 뷰 세팅
                            if(!page_name_s.isEmpty()){
                                page_name.setText("Name. "+page_name_s);
                                page_storename.setText(page_name_s);
                            }

                            //스트링이 존재한다면 전화번호 뷰 세팅
                            if(!page_tel_s.isEmpty()){
                                page_tel.setText("#. "+page_tel_s);
                            }

                            //리사이클러뷰
                            for(int i=0;i<img_array.size();i++) {
                                String image_path = img_array.get(i);

                                ScrapItem item = new ScrapItem(image_path);

                                items.add(0, item); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                                adapter.notifyItemInserted(0);



                            }

                        } });




                    }catch(IOException e){
                        e.printStackTrace();

                    }

                }//end of run



            }).start();

            //쓰레드 끝













        }//네이버 크롤링 끝


        //구글 크롤링 시작
        //구글 크롤링
        else if(google_flag==true){

            //이미지 src 초기화
            img_array.clear();

            //텍스트뷰 추출 위한 url
            String url = "https://www.google.com/search?q="
                    +store_name;

            //이미지 src 추출 위한 url (이미지-플레이스검색.플레이스는 업체 등록 이미지)
            String url2 = "https://m.search.naver.com/search.naver?where=m_image&mode=imgonly&section=place&query="+store_name;


            //입력받고 전달받은 상호명 확인
            System.out.println(store_name);


            //이미지 개수 가져오기
            //쓰레드 시작
            new Thread(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override public void run() {


                    System.out.println("thread start..");

                    try {

                        //텍스트 위한 document 객체
                        Document document = Jsoup.connect(url).get();
                        //이미지 위한 document 객체 - 네이버 고정
                        Document document2 = Jsoup.connect(url2).get();

                        //기본
                        location_element = document.getElementsByClass("QsDR1c").first();//ok
                        category_element = document.getElementsByAttributeValue("data-attrid","kc:/local:one line summary").first();//ok
                        name_element = document.getElementsByAttributeValue("data-attrid","title").first();//ok
                        tel_element = document.getElementsByAttributeValue("data-attrid","kc:/collection/knowledge_panels/has_phone:phone").first();//ok







                        //이미지 페이지 all-test
                        Elements testing = document2.getAllElements();


                        //안나오는 정보 테스팅
                        /*
                        Elements testing2 = document.getAllElements();
                        for(int i=0;i<testing2.size();i++){
                            //System.out.println(testing2.get(i).toString());
                        }
                        */




                        //전체 요소 확인, 1개로 추출되는 스크립트 사진 데이터 저장
                        String img_data = "";

                        for(int i=0;i<testing.size();i++){

                            String tmp = testing.get(i).toString();
                            //System.out.println(tmp);

                            if(tmp.contains("var data")){
                                img_data=tmp;
                            }

                        }



                        //성공
                        System.out.println("img_data: " + img_data);



                        //토큰 분리 "" 로 분리 후 https 포함하는 요소를 추가, 어레이테 본 주소 전체 저장
                        StringTokenizer st = new StringTokenizer(img_data,"\"");

                        while(st.hasMoreTokens()) {

                            String token=st.nextToken();

                            if( token.contains("https") && (token.contains(".jpg")||token.contains(".png")) && !token.contains("3Dsc960_832") &&!token.contains("3Da340") ) {

                                img_array.add("https://search.pstatic.net/common/?src="+token);
                            }

                        }

                        //img_array=img_array.stream().distinct().collect(Collectors.toList());

                        for(int i=0;i<img_array.size();i++){
                            System.out.println(img_array.get(i));
                        }






                        //추출한 위치 정보 존재하는 경우 위치 스트링에 문자열 저장
                        if(location_element!=null){
                            page_location_s += location_element.text().toString();
                            System.out.println(page_location_s);

                        }


                        //추출한 카테고리 정보 존재하는 경우 카테고리 스트링에 문자열 저장
                        if(category_element!=null){
                            page_category_s += category_element.text().toString();
                            System.out.println(page_category_s);
                        }

                        //추출한 전화번호 정보 존재하는 경우 전화번호 스트링에 문자열 저장
                        if(tel_element!=null){

                            page_tel_s += tel_element.text().toString();
                            System.out.println(page_tel_s);
                        }

                        //추출한 이름 정보 존재하는 경우 이름 스트링에 문자열 저장
                        if(name_element!=null){
                            page_name_s += name_element.text().toString();
                            System.out.println(page_name_s);
                        }





                        //UI 뷰 세팅 위한 thread
                        runOnUiThread(new Runnable() { public void run() {

                            //오류 출력
                            //오류 출력
                            //일부 정보 크롤링 실패
                            if(location_element==null||category_element==null||name_element==null||
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "일부 텍스트 정보는 가져오지 못했어요!", Toast.LENGTH_SHORT).show();

                            }

                            //전체 정보 크롤링 실패
                            if(location_element==null&&category_element==null&&name_element==null&&
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "등록된 정보가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }


                            //이미지 크롤링 실패
                            if(img_array.isEmpty()){
                                Toast.makeText(getApplicationContext(), "등록된 이미지가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }



                            //스트링이 존재한다면 위치 뷰 세팅
                            if(!page_location_s.isEmpty()){
                                page_location.setText("Loc. "+page_location_s);
                            }

                            //스트링이 존재한다면 카테고리 뷰 세팅
                            if(!page_category_s.isEmpty()){
                                page_category.setText("Category. "+page_category_s);
                            }

                            //스트링이 존재한다면 이름, 제목 뷰 세팅
                            if(!page_name_s.isEmpty()){
                                page_name.setText("Name. "+page_name_s);
                                page_storename.setText(page_name_s);
                            }

                            //스트링이 존재한다면 전화번호 뷰 세팅
                            if(!page_tel_s.isEmpty()){
                                page_tel.setText("#. "+page_tel_s);
                            }


                            //리사이클러뷰
                            for(int i=0;i<img_array.size();i++) {
                                String image_path = img_array.get(i);

                                ScrapItem item = new ScrapItem(image_path);

                                items.add(0, item); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                                adapter.notifyItemInserted(0);

                            }




                        } });




                    }catch(IOException e){
                        e.printStackTrace();

                    }

                }//end of run



            }).start();

            //쓰레드 끝



        }//구글 크롤링 끝


        //다음 크롤링 시작
        //다음 크롤링
        else if(daum_flag==true){

            //이미지 src 초기화
            img_array.clear();

            //텍스트뷰 추출 위한 url
            String url = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&sq=&o=&q="
                    +store_name;

            //이미지 src 추출 위한 url (이미지-플레이스검색.플레이스는 업체 등록 이미지)
            String url2 = "https://m.search.naver.com/search.naver?where=m_image&mode=imgonly&section=place&query="+store_name;


            //입력받고 전달받은 상호명 확인
            System.out.println(store_name);


            //이미지 개수 가져오기
            //쓰레드 시작
            new Thread(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override public void run() {


                    System.out.println("thread start..");

                    try {

                        //텍스트 위한 document 객체
                        Document document = Jsoup.connect(url).get();
                        //이미지 위한 document 객체 - 네이버 고정
                        Document document2 = Jsoup.connect(url2).get();

                        //기본
                        location_element = document.select("#poiColl > div.coll_cont.poi_cont > div.wrap_place.wrap_ad > div.pannel > ul > li > div > div.wrap_cont > div.info_place.ty_layer > div > a > span.txt_address").first();
                        category_element = document.select("#poiColl > div.coll_cont.poi_cont > div.wrap_place.wrap_ad > div.pannel > ul > li > div > div.wrap_cont > div.inner_tit > span").first();
                        name_element = document.select("#poiColl > div.coll_cont.poi_cont > div.wrap_place.wrap_ad > div.pannel > ul > li > div > div.wrap_cont > div.inner_tit > a.fn_tit").first();
                        tel_element = document.select("#poiColl > div.coll_cont.poi_cont > div.wrap_place.wrap_ad > div.pannel > ul > li > div > div.wrap_cont > div.info_place.ty_layer > span").first();







                        //이미지 페이지 all-test
                        Elements testing = document2.getAllElements();


                        //안나오는 정보 테스팅
                        /*
                        Elements testing2 = document.getAllElements();
                        for(int i=0;i<testing2.size();i++){
                            //System.out.println(testing2.get(i).toString());
                        }
                        */




                        //전체 요소 확인, 1개로 추출되는 스크립트 사진 데이터 저장
                        String img_data = "";

                        for(int i=0;i<testing.size();i++){

                            String tmp = testing.get(i).toString();
                            //System.out.println(tmp);

                            if(tmp.contains("var data")){
                                img_data=tmp;
                            }

                        }



                        //성공
                        System.out.println("img_data: " + img_data);



                        //토큰 분리 "" 로 분리 후 https 포함하는 요소를 추가, 어레이테 본 주소 전체 저장
                        StringTokenizer st = new StringTokenizer(img_data,"\"");

                        while(st.hasMoreTokens()) {

                            String token=st.nextToken();

                            if( token.contains("https") && (token.contains(".jpg")||token.contains(".png")) && !token.contains("3Dsc960_832") &&!token.contains("3Da340") ) {

                                img_array.add("https://search.pstatic.net/common/?src="+token);
                            }

                        }

                        //img_array=img_array.stream().distinct().collect(Collectors.toList());

                        for(int i=0;i<img_array.size();i++){
                            System.out.println(img_array.get(i));
                        }






                        //추출한 위치 정보 존재하는 경우 위치 스트링에 문자열 저장
                        if(location_element!=null){
                            page_location_s += location_element.text().toString();
                            System.out.println(page_location_s);

                        }


                        //추출한 카테고리 정보 존재하는 경우 카테고리 스트링에 문자열 저장
                        if(category_element!=null){
                            page_category_s += category_element.text().toString();
                            System.out.println(page_category_s);
                        }

                        //추출한 전화번호 정보 존재하는 경우 전화번호 스트링에 문자열 저장
                        if(tel_element!=null){

                            page_tel_s += tel_element.text().toString();
                            System.out.println(page_tel_s);
                        }

                        //추출한 이름 정보 존재하는 경우 이름 스트링에 문자열 저장
                        if(name_element!=null){
                            page_name_s += name_element.text().toString();
                            System.out.println(page_name_s);
                        }





                        //UI 뷰 세팅 위한 thread
                        runOnUiThread(new Runnable() { public void run() {

                            //오류 출력
                            //오류 출력
                            //일부 정보 크롤링 실패
                            if(location_element==null||category_element==null||name_element==null||
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "일부 텍스트 정보는 가져오지 못했어요!", Toast.LENGTH_SHORT).show();

                            }

                            //전체 정보 크롤링 실패
                            if(location_element==null&&category_element==null&&name_element==null&&
                                    tel_element==null){
                                Toast.makeText(getApplicationContext(), "등록된 정보가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }


                            //이미지 크롤링 실패
                            if(img_array.isEmpty()){
                                Toast.makeText(getApplicationContext(), "등록된 이미지가 없어요! 업체명을 확인해주세요!", Toast.LENGTH_SHORT).show();

                            }



                            //스트링이 존재한다면 위치 뷰 세팅
                            if(!page_location_s.isEmpty()){
                                page_location.setText("Loc. "+page_location_s);
                            }

                            //스트링이 존재한다면 카테고리 뷰 세팅
                            if(!page_category_s.isEmpty()){
                                page_category.setText("Category. "+page_category_s);
                            }

                            //스트링이 존재한다면 이름, 제목 뷰 세팅
                            if(!page_name_s.isEmpty()){
                                page_name.setText("Name. "+page_name_s);
                                page_storename.setText(page_name_s);
                            }

                            //스트링이 존재한다면 전화번호 뷰 세팅
                            if(!page_tel_s.isEmpty()){
                                page_tel.setText("#. "+page_tel_s);
                            }


                            //리사이클러뷰
                            for(int i=0;i<img_array.size();i++) {
                                String image_path = img_array.get(i);

                                ScrapItem item = new ScrapItem(image_path);

                                items.add(0, item); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                                adapter.notifyItemInserted(0);

                            }




                        } });




                    }catch(IOException e){
                        e.printStackTrace();

                    }

                }//end of run



            }).start();

            //쓰레드 끝



        }//구글 크롤링 끝


        //포스터 저장 버튼
        poster_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                CustomDialog content_dialog = new CustomDialog(MakePosterActivity2.this);
                content_dialog.setCancelable(false);
                content_dialog.show();




                content_dialog.OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        insert_title = content_dialog.title.getText().toString();
                        insert_content = content_dialog.content.getText().toString();
                        insert_hashtag = content_dialog.hashtag.getText().toString();

                        if(insert_title.isEmpty()||insert_hashtag.isEmpty()||insert_content.isEmpty()){
                            Toast.makeText(getApplicationContext(), String.format("모든 칸을 입력해 주세요."), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        content_dialog.dismiss();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); //년,월,일,시간 포멧 설정
                        Date time = new Date(); //파일명 중복 방지를 위해 사용될 현재시간
                        String current_time = sdf.format(time); //String형 변수에 저장

                        LinearLayout capture_target_Layout = (LinearLayout) findViewById(R.id.poster_view_l); //캡쳐할 영역의 레이아웃

                        Request_Capture(capture_target_Layout, current_time + "_capture"); //지정한 Layout 영역 사진첩 저장

                        //메인 인텐트로 이동
                        Toast.makeText(getApplicationContext(), String.format("저장이 완료되었습니다."), Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(MakePosterActivity2.this, MainActivity.class);
                        intent2.putExtra("cur_user_id", cur_user_id);
                        startActivity(intent2);

                    }
                });




            }
        });








    }

    //포스터 저장
    public void Request_Capture(View view, String title){
        if(view==null){ //Null Point Exception ERROR 방지
            System.out.println("::::ERROR:::: view == NULL");
            return;
        }

        /* 캡쳐 파일 저장 */
        view.buildDrawingCache(); //캐시 비트 맵 만들기
        bitmap = view.getDrawingCache();
        bitmap_server = view.getDrawingCache();



        /* 저장할 폴더 Setting */
        File uploadFolder = Environment.getExternalStoragePublicDirectory("/DCIM/Camera/"); //저장 경로 (File Type형 변수)



        if (!uploadFolder.exists()) { //만약 경로에 폴더가 없다면
            uploadFolder.mkdir(); //폴더 생성
        }

        /* 파일 저장, 서버 전송추가하기 */
        String Str_Path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/"; //저장 경로 (String Type 변수)

        try{

            //갤러리 저장
            fos = new FileOutputStream(Str_Path+title+".png"); // 경로 + 제목 + .jpg로 FileOutputStream Setting
            bitmap = Bitmap.createScaledBitmap(bitmap, 250, 700, true);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);


            //서버 업로드
            bitmap_server = Bitmap.createScaledBitmap(bitmap_server, 250, 700, true);
            encodeBitmapImage(bitmap_server);
            uploadDataToDB();



        }catch (Exception e){
            e.printStackTrace();
        }

        //캡쳐 파일 미디어 스캔

        MediaScanner ms = MediaScanner.newInstance(getApplicationContext());



        try { // TODO : 미디어 스캔
            ms.mediaScanning(Str_Path + title + ".png");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("::::ERROR:::: "+e);
        }

    }//End Function


    private void uploadDataToDB()
    {

        String name = cur_user_id;

        StringRequest request = new StringRequest(Request.Method.POST, url_upload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(MakePosterActivity2.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(MakePosterActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();

                map.put("userId", name);
                map.put("upload", encodeImageString);

                map.put("title", insert_title);
                map.put("hashtag", insert_hashtag);
                map.put("content", insert_content);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }



    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap_server.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);

    }





    //드래그 앤 드롭 구현
    public static class LongClickListener implements View.OnLongClickListener {

        public boolean onLongClick(View view) {

            // 태그 생성
            ClipData.Item item = new ClipData.Item(
                    (CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(),
                    mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);

            view.startDrag(data, // data to be dragged
                    shadowBuilder, // drag shadow
                    view, // 드래그 드랍할  Vew
                    0
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }


    class DragListener implements View.OnDragListener {
        //Drawable normalShape = getResources().getDrawable(
        //        R.color.light_gray2);
       // Drawable targetShape = getResources().getDrawable(
       //         R.color.black);

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

                    break;

                // 드래그한 이미지가 영역을 빠져 나갈때
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");

                    if(v.getId() != findViewById(R.id.image_recyclerview).getId())


                    break;

                // 이미지를 드래그해서 드랍시켰을때
                case DragEvent.ACTION_DROP:

                    Log.d("DragClickListener", "ACTION_DROP");

                    if ( v == findViewById(R.id.imageView_1) ||
                            v == findViewById(R.id.imageView_2) ||
                            v == findViewById(R.id.imageView_3)||
                            v == findViewById(R.id.imageView_4)||
                            v == findViewById(R.id.imageView_5)||
                            v == findViewById(R.id.imageView_6)


                    ){

                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);


                        FrameLayout containView = (FrameLayout) v;


                        FrameLayout.LayoutParams p =
                                new FrameLayout.LayoutParams(containView.getMeasuredWidth(),containView.getMeasuredHeight());

                        view.setLayoutParams(p);


                        containView.addView(view);

                        view.setVisibility(View.VISIBLE);

                    }else if (v == findViewById(R.id.image_recyclerview)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view
                                .getParent();
                        //viewgroup.removeView(view);




                    }else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context,
                                "이미지를 다른 지역에 드랍할수 없습니다.",
                                Toast.LENGTH_LONG).show();

                        //되돌리기

                        recyclerView.addView(view);

                        break;
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");

                   // if(!(v.getId() == findViewById(R.id.image_recyclerview).getId()))
                       // v.setBackground(normalShape); // go back to normal shape

                default:
                    break;
            }
            return true;
        }
    }





}