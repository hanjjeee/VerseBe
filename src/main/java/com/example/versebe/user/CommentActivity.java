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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private Intent intent;
    private String cur_user_id;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CommentItem> items;
    private CommentItemAdapter adapter;


    private int article_num;
    private String type;
    private String content;
    private String user_id;
    private String update_date;
    private String last_date;

    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView(R.layout.activity_comment);

        intent = getIntent();
        article_num=intent.getExtras().getInt("ARTICLE_NUM");
        cur_user_id=intent.getExtras().getString("cur_user_id");

        //레이아웃
        items = new ArrayList<CommentItem>();


        recyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        adapter = new CommentItemAdapter(this, items);




        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //서버 연결
        System.out.println("response listener 전");

        Response.Listener<String> responseListener;

        responseListener = response -> {

            try {
                System.out.println("on response 후, json 전");
                System.out.println("hanjiyoon " + response);
                JSONObject jsonObject = new JSONObject(response);

                System.out.println("json 후, success 전");

                boolean success = jsonObject.getBoolean( "success" );
                System.out.println(response+"json 후");


                if (success) {//로그인 성공시


                    type = jsonObject.getString( "TYPE" );
                    content = jsonObject.getString( "CONTENT" );
                    user_id = jsonObject.getString( "USER_ID" );
                    update_date = jsonObject.getString( "UPDATE_DATE" );
                    last_date = jsonObject.getString( "LAST_DATE" );

                    //프로필 이미지는 유저 아이디로 검색
                    image_path = jsonObject.getString( "USER_ID" );

                    Toast.makeText(getApplicationContext(), String.format("고유번호 %s 번 게시물 댓글", article_num), Toast.LENGTH_SHORT).show();
                    //테스트


                }
                else
                {
                    //로그인 실패시
                    Toast.makeText(getApplicationContext(), article_num+"번 게시글 댓글 불러오기 실패", Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (JSONException e) {

                System.out.println("not try");
                e.printStackTrace();
            }

        };

        System.out.println("response listener 후");

        CommentRequest commentRequest = new CommentRequest( article_num,type, responseListener );
        RequestQueue queue = Volley.newRequestQueue( CommentActivity.this );
        queue.add( commentRequest );



    }

}
