package com.example.versebe.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.example.versebe.user.FeedItem;
import com.example.versebe.user.FeedItemAdapter;
import com.example.versebe.user.FollowItem;
import com.example.versebe.user.FollowItemAdapter;
import com.example.versebe.user.MemberActivity;
import com.example.versebe.user.OnFeedItemClickListener;
import com.example.versebe.user.PosterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment0_1 extends Fragment {

    private Intent intent;
    private String cur_user_id;

    public View view;

    private RecyclerView recyclerView;
    private FeedItemAdapter adapter;
    private ArrayList<FeedItem> items;
    private  GridLayoutManager layoutManager;

    //json
    private String type;
    private int poster_num;
    private String thumb_image;
    private String user_id;
    private String update_date;
    private String last_date;
    private int article_num;
    private String hash_tag;
    private String poster_image;
    private String title;
    private String content;
    private int scrap;





    public Fragment0_1(Intent intent) {

        this.intent = intent;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mainpage_main_feed1, container, false);
        cur_user_id = intent.getExtras().getString("cur_user_id");

        //그리드 리사이클러 뷰 불러오기
        items = new ArrayList<FeedItem>();

        recyclerView = (RecyclerView) view.findViewById(R.id.mainpage_recyclerview);

        adapter = new FeedItemAdapter(getContext(), items);

        recyclerView.setAdapter(adapter);

        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);


        //서버주소

        //test 용
        String url = "http://hanjiyoon.dothome.co.kr/posters.php";

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
                        poster_num = jsonObject.getInt("POSTER_NUM");
                        thumb_image = jsonObject.getString("THUMBNAIL");
                        user_id = jsonObject.getString("USER_ID");
                        update_date= jsonObject.getString("UPDATE_DATE");
                        last_date= jsonObject.getString("LAST_DATE");
                        article_num = jsonObject.getInt("ARTICLE_NUM");
                        hash_tag = jsonObject.getString("HASH_TAG");
                        title = jsonObject.getString("TITLE");
                        poster_image = jsonObject.getString("POSTER_IMAGE");
                        content = jsonObject.getString("CONTENT");
                        scrap = jsonObject.getInt("SCRAP");


                        //test
                        //image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;

                        //String thumb_image_path = "http://hanjiyoon.dothome.co.kr/poster_thumb/" + thumb_image;
                        String thumb_image_path = "http://hanjiyoon.dothome.co.kr/posters/" + thumb_image;
                        String poster_image_path = "http://hanjiyoon.dothome.co.kr/posters/" + poster_image;

                        items.add(0, new FeedItem(type,thumb_image_path,user_id,update_date,last_date,
                                poster_image_path,hash_tag,article_num,title, content, scrap));

                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        //클릭 리스너
        adapter.setOnItemClicklistener(new OnFeedItemClickListener() {

            @Override
            public void onItemClick(FeedItemAdapter.VH holder, View view, int position) {

                FeedItem item = adapter.getItem(position);

                Toast.makeText(getContext(),"아이템 선택 " + item.getUser_id(), Toast.LENGTH_LONG).show();

                Intent feed_intent = new Intent(getContext(), PosterActivity.class);

                feed_intent.putExtra( "cur_user_id", cur_user_id);

                feed_intent.putExtra("TYPE", item.getType());
                feed_intent.putExtra( "ARTICLE_NUM", item.getArticle_num());
                feed_intent.putExtra("THUMBNAIL", item.getThumb_image());
                feed_intent.putExtra( "USER_ID", item.getUser_id());
                feed_intent.putExtra( "UPDATE_DATE", item.getUpdate_date());
                feed_intent.putExtra("LAST_DATE", item.getLast_date());
                feed_intent.putExtra("ARTICLE_NUM", item.getArticle_num());
                feed_intent.putExtra("HASH_TAG", item.getHash_tag());
                feed_intent.putExtra("POSTER_IMAGE", item.getPoster_image());
                feed_intent.putExtra("TITLE", item.getTitle());
                feed_intent.putExtra("CONTENT", item.getContent());
                feed_intent.putExtra("SCRAP", item.getScrap());

                startActivity(feed_intent);
            }
        });

        return view;

    }
}
