package com.example.versebe.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.example.versebe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PosterActivity extends AppCompatActivity {

    private String image_path;
    private ImageView poster_image;
    private ImageButton like_button;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private ArrayList<CommentItem> items;
    private CommentItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);


        //임시
        image_path = "longcat.jpg";
        image_path = "http://hanjiyoon.dothome.co.kr/app_image/"+image_path;

        poster_image = findViewById(R.id.poster_image);
        like_button = findViewById(R.id.posterpage_like_button);

        like_button.setOnClickListener(new View.OnClickListener(){

            //유저 db에 해당 게시글의 고유넘버 pk 추가
            @Override
            public void onClick(View view) {





            }


        });


        Glide.with(this).load(image_path).into(poster_image);



        items = new ArrayList<CommentItem>();

        recyclerView = findViewById(R.id.comment_recyclerview);
        adapter = new CommentItemAdapter(getApplicationContext(), items);


        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);


        //댓글 임시
        //서버주소
        String url = "http://hanjiyoon.dothome.co.kr/loadDB.php";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용

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

                        String id = jsonObject.getString("title");

                        String comment = jsonObject.getString("detail");

                        String image_path = jsonObject.getString("image_path");


                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;

                        items.add(0, new CommentItem(image_path, id, comment)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);




    }
}
