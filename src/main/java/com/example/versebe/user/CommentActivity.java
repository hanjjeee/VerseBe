package com.example.versebe.user;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    //포스터 정보 받는 것
    private Intent intent;
    private String cur_user_id;
    private String hash_tag;
    private String content;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView text_view;
    private TextView hash_tag_view;
    private EditText comment_input;
    private Button comment_input_button;

    private String comment_input_s;

    //해시태그, 텍스트 추가하기
    private ArrayList<CommentItem> items;
    private CommentItemAdapter adapter;




    //comment 서버에서 받을 것
    private int article_num;
    private String type;
    private String user_id;
    private String comment;


    private String image_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);

        intent = getIntent();
        article_num = intent.getExtras().getInt("ARTICLE_NUM");
        cur_user_id = intent.getExtras().getString("cur_user_id");
        type = intent.getExtras().getString("TYPE");
        hash_tag = intent.getExtras().getString("HASH_TAG");
        content = intent.getExtras().getString("CONTENT");


        //레이아웃
        items = new ArrayList<CommentItem>();


        recyclerView = (RecyclerView) findViewById(R.id.comment_recyclerview);
        hash_tag_view = findViewById(R.id.hash_tag_view);
        text_view = findViewById(R.id.text_view);
        comment_input = findViewById(R.id.comment_input);
        comment_input_button = findViewById(R.id.comment_input_button);


        //해시태그, 커멘트 는 세팅
        hash_tag_view.setText(hash_tag);
        text_view.setText(content);


        //댓글 구현
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
                    System.out.println("hanjiyoon " + response + response.length());


                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        System.out.println(response);

                        article_num = jsonObject.getInt("ARTICLE_NUM");
                        type = jsonObject.getString("TYPE");
                        comment = jsonObject.getString("CONTENT");
                        user_id = jsonObject.getString("USER_ID");

                        image_path = "http://hanjiyoon.dothome.co.kr/profile/" + user_id + ".jpg";

                        items.add(0, new CommentItem(article_num, type, comment, user_id, image_path)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);

                    }

                    Toast.makeText(getApplicationContext(), String.format("%s번 글!!!", article_num), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {

                    System.out.println("not try");
                    e.printStackTrace();

                }

            }

        };


        System.out.println("response listener 후-" + article_num + "번글");

        String URL = "http://hanjiyoon.dothome.co.kr/comments.php";
        Map mMap = new HashMap();
        mMap.put("ARTICLE_NUM", article_num + "");
        CommentRequest commentRequest =
                new CommentRequest(URL, responseListener, null, mMap);
        RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
        queue.add(commentRequest);


        //댓글 등록 버튼
        comment_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                comment_input_s = comment_input.getText().toString();


                if (comment_input_s.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("hanjiyoon" + response);
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                // 댓글 등록성공
                                Toast.makeText(getApplicationContext(), "댓글 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent2 = getIntent();
                                finish();
                                startActivity(intent2);




                            } else {
                                // 회원등록실패
                                Toast.makeText(getApplicationContext(), "댓글 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                // 서버로 Volley를 통해 연결
                CommentInputRequest commentInputRequest = new CommentInputRequest(article_num+"", type, comment_input_s, cur_user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
                queue.add(commentInputRequest);


            }//end of onClick


        });


    }




}
