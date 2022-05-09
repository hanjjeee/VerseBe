package com.example.versebe.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private Intent intent;
    private String cur_user_id;
    private String hash_tag;

    private RecyclerView recyclerView,recyclerView2, recyclerView3 ;
    private LinearLayoutManager layoutManager,layoutManager2,layoutManager3;

    //해시태그, 텍스트 추가하기
    private ArrayList<CommentItem> items;
    private CommentItemAdapter adapter;




    private int article_num;
    private String type;
    private String content;
    private String user_id;
    private String content0;

    private String image_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView(R.layout.activity_comment);

        intent = getIntent();
        article_num=intent.getExtras().getInt("ARTICLE_NUM");
        cur_user_id=intent.getExtras().getString("cur_user_id");
        type =intent.getExtras().getString("TYPE");
        hash_tag = intent.getExtras().getString("CONTENT");


        //레이아웃
        items = new ArrayList<CommentItem>();


        recyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        //recyclerView2 = (RecyclerView) findViewById(R.id.text_recyclerview);
        //recyclerView3 = (RecyclerView) findViewById(R.id.hashtag_recyclerview);

        adapter = new CommentItemAdapter(this, items);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //구현

        System.out.println("response listener 전");

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {



            @Override
            public void onResponse(JSONArray response) {

                try {

                    System.out.println("in onResponse");
                    System.out.println("hanjiyoon " + response);


                    for(int i = 0; i < response.length(); i++){

                        JSONObject jsonObject = response.getJSONObject(i);
                        System.out.println(response);

                        article_num = jsonObject.getInt("ARTICLE_NUM");
                        type = jsonObject.getString("TYPE");
                        content = jsonObject.getString("CONTENT");
                        content0 = jsonObject.getString("CONTENT0");
                        user_id = jsonObject.getString("USER_ID");

                        image_path = "http://hanjiyoon.dothome.co.kr/profile/"+user_id+".jpg";

                        items.add(0, new CommentItem(article_num, type, content, user_id, image_path, content0)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);

                    }

                    Toast.makeText(getApplicationContext(), String.format("%s번 글!!!", article_num), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {

                    System.out.println("not try");
                    e.printStackTrace();

                }

            }

        };


        System.out.println("response listener 후-"+article_num+"번글");

        CommentRequest commentRequest = new CommentRequest( article_num+"", responseListener );
        RequestQueue queue = Volley.newRequestQueue( CommentActivity.this );
        queue.add( commentRequest );


    }



}
