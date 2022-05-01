package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class ChoiceLayout extends AppCompatActivity {


    private Intent intent;
    private String cur_user_id;

    private RecyclerView recyclerView;
    private LayoutItemAdapter adapter;
    private ArrayList<LayoutItem> items;
    private GridLayoutManager layoutManager;

    //json
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);

        intent = getIntent();

        //리사이클러뷰
        //그리드 리사이클러 뷰 불러오기
        items = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.choice_layout_recyclerview);

        adapter = new LayoutItemAdapter(this, items);

        recyclerView.setAdapter(adapter);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        //서버주소

        //test 용
        String url = "http://hanjiyoon.dothome.co.kr/layouts.php";

        //String url = "http://hanjiyoon.dothome.co.kr/choice.php";

        //결과를 JsonArray 로 받음
        //JsonArrayRequest를 이용
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않으므로 POST 방식 사용

            @Override
            public void onResponse(JSONArray response) {

                //db 연결 확인용
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();


                //파라미터로 응답받은 결과 JsonArray를 분석
                items.clear();
                adapter.notifyDataSetChanged();

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        type = jsonObject.getString("TYPE");
                        layout_num = jsonObject.getInt("LAYOUT_NUM");
                        thumb_image = jsonObject.getString("THUMBNAIL");
                        user_id = jsonObject.getString("USER_ID");
                        update_date = jsonObject.getString("UPDATE_DATE");
                        last_date = jsonObject.getString("LAST_DATE");
                        article_num = jsonObject.getInt("ARTICLE_NUM");
                        hash_tag = jsonObject.getString("HASH_TAG");
                        title = jsonObject.getString("TITLE");
                        layout_image = jsonObject.getString("LAYOUT_IMAGE");


                        //test
                        //image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;

                        String thumb_image_path = "http://hanjiyoon.dothome.co.kr/layout_thumb/" + thumb_image;
                        String poster_image_path = "http://hanjiyoon.dothome.co.kr/layouts/" + layout_image;

                        items.add(0, new LayoutItem(type, thumb_image_path, user_id, update_date, last_date,
                                poster_image_path, hash_tag, article_num, title));

                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        //클릭 리스너
        adapter.setOnItemClicklistener(new OnLayoutItemClickListener() {

            @Override
            public void onItemClick(LayoutItemAdapter.VH holder, View view, int position) {

                LayoutItem item = adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "아이템 선택 " + item.getUser_id(), Toast.LENGTH_LONG).show();

                Intent make_intent = new Intent(getApplicationContext(), MakePosterActivity2.class);

                make_intent.putExtra("cur_user_id", cur_user_id);

                make_intent.putExtra("TYPE", item.getType());
                make_intent.putExtra("ARTICLE_NUM", item.getArticle_num());
                make_intent.putExtra("THUMBNAIL", item.getThumb_image());
                make_intent.putExtra("USER_ID", item.getUser_id());
                make_intent.putExtra("UPDATE_DATE", item.getUpdate_date());
                make_intent.putExtra("LAST_DATE", item.getLast_date());
                make_intent.putExtra("ARTICLE_NUM", item.getArticle_num());
                make_intent.putExtra("HASH_TAG", item.getHash_tag());
                make_intent.putExtra("POSTER_IMAGE", item.getPoster_image());
                make_intent.putExtra("TITLE", item.getTitle());

                startActivity(make_intent);
            }
        });
    }


}
