package com.example.versebe.user;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    private ArrayList<CommentItem> items;
    private RecyclerView recyclerView;
    private CommentItemAdapter adapter;
    private LinearLayoutManager layoutManager;

    //json 추가
    private String title;
    private String email;
    private String image_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        items = new ArrayList<CommentItem>();


        recyclerView = (RecyclerView)findViewById(R.id.comment_recyclerview);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new CommentItemAdapter(this, items);



        recyclerView.setLayoutManager(layoutManager);




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

                        title = jsonObject.getString("title");

                        email = jsonObject.getString("detail");

                        image_path = jsonObject.getString("image_path");


                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        image_path = "http://hanjiyoon.dothome.co.kr/app_image/" + image_path;

                        items.add(0, new CommentItem(image_path, title, email)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
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

        //////end of server

        recyclerView.setAdapter(adapter);


    }

}
