package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PosterActivity extends AppCompatActivity {

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

    private String cur_user_id;

    private ImageView poster_image_view;
    private TextView poster_user_id;
    private ImageButton like_button;
    private Button comment_button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        Intent intent = getIntent();
        cur_user_id = intent.getExtras().getString("cur_user_id");

        type = intent.getExtras().getString("TYPE");
        poster_num = intent.getExtras().getInt("POSTER_NUM");;
        thumb_image= intent.getExtras().getString("THUMBNAIL");;
        user_id = intent.getExtras().getString("USER_ID");;
        update_date= intent.getExtras().getString("UPDATE_DATE");;
        last_date = intent.getExtras().getString("LAST_DATE");;
        article_num = intent.getExtras().getInt("ARTICLE_NUM");;
        hash_tag = intent.getExtras().getString("HASH_TAG");;
        poster_image = intent.getExtras().getString("POSTER_IMAGE");
        title = intent.getExtras().getString("TITLE");
        content = intent.getExtras().getString("CONTENT");
        scrap = intent.getExtras().getInt("SCRAP");






        poster_image_view = findViewById(R.id.poster_image);
        poster_user_id = findViewById(R.id.posterpage_user_id);


        like_button = findViewById(R.id.posterpage_like_button);
        comment_button = findViewById(R.id.comment_button);


        Glide.with(this).load(poster_image).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(poster_image_view);
        poster_user_id.setText(user_id);


        //?????????***
        like_button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

                //????????? ?????? ????????? ?????? ?????? ?????? ?????????
                if(cur_user_id.equals(user_id)){

                    getMenuInflater().inflate(R.menu.menu2, popupMenu.getMenu());
                }
                //?????? ????????? ?????? ??????
                else{
                    getMenuInflater().inflate(R.menu.menu3, popupMenu.getMenu());

                }


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.save_menu) {
                            Toast.makeText(getApplicationContext(), "poster ?????????", Toast.LENGTH_SHORT).show();

                            //????????? ?????? ?????? ???
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {

                                        System.out.println("hanjiyoon"+ response);
                                        JSONObject jsonObject = new JSONObject(response);

                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {

                                            //????????????
                                            String url = "http://hanjiyoon.dothome.co.kr/like.php";

                                            //????????? JsonArray ??? ??????
                                            //JsonArrayRequest??? ??????
                                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                                                //volley ?????????????????? GET????????? ?????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????? POST ?????? ??????

                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    //db ?????? ?????????
                                                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();



                                                    try {

                                                        for (int i = 0; i < response.length(); i++) {

                                                            JSONObject jsonObject = response.getJSONObject(i);

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

                                            //?????? ?????? ????????? ??????????????? ????????? ?????? ??????
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                            //???????????? ?????? ?????? ??????
                                            requestQueue.add(jsonArrayRequest);



                                            Toast.makeText(getApplicationContext(),"???????????????: "+poster_num,Toast.LENGTH_SHORT).show();
                                            return;

                                        } else {

                                            Toast.makeText(getApplicationContext(),"??????",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            // ????????? Volley??? ?????? ??????
                            LikeRequest likeRequest = new LikeRequest(cur_user_id, article_num, type,responseListener);
                            RequestQueue queue = Volley.newRequestQueue(PosterActivity.this);
                            queue.add(likeRequest);

                            //ok


                            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {

                                        System.out.println("hanjiyoon"+ response);
                                        JSONObject jsonObject = new JSONObject(response);

                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {

                                            //????????????
                                            String url = "http://hanjiyoon.dothome.co.kr/like2.php";

                                            //????????? JsonArray ??? ??????
                                            //JsonArrayRequest??? ??????
                                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                                                //volley ?????????????????? GET????????? ?????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????? POST ?????? ??????

                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    //db ?????? ?????????
                                                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();



                                                    try {

                                                        for (int i = 0; i < response.length(); i++) {

                                                            JSONObject jsonObject = response.getJSONObject(i);

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

                                            //?????? ?????? ????????? ??????????????? ????????? ?????? ??????
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                            //???????????? ?????? ?????? ??????
                                            requestQueue.add(jsonArrayRequest);



                                            Toast.makeText(getApplicationContext(),"???????????????: "+poster_num,Toast.LENGTH_SHORT).show();
                                            return;

                                        } else {

                                            Toast.makeText(getApplicationContext(),"??????",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            // ????????? Volley??? ?????? ??????
                            LikeRequest likeRequest2 = new LikeRequest(cur_user_id, article_num, type,responseListener2);
                            RequestQueue queue2 = Volley.newRequestQueue(PosterActivity.this);
                            queue2.add(likeRequest2);

                            //end of ???????????????




                        } else if (menuItem.getItemId() == R.id.save_cancle_menu) {//3??? ?????????
                            Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();


                            Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {

                                        System.out.println("hanjiyoon"+ response);

                                        JSONObject jsonObject = new JSONObject(response);

                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {

                                            //????????????
                                            String url = "http://hanjiyoon.dothome.co.kr/like_cancle.php";

                                            //????????? JsonArray ??? ??????
                                            //JsonArrayRequest??? ??????
                                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                                                //volley ?????????????????? GET????????? ?????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????? POST ?????? ??????

                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    //db ?????? ?????????
                                                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();

                                                    try {

                                                        for (int i = 0; i < response.length(); i++) {

                                                            JSONObject jsonObject = response.getJSONObject(i);

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

                                            //?????? ?????? ????????? ??????????????? ????????? ?????? ??????
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                            //???????????? ?????? ?????? ??????
                                            requestQueue.add(jsonArrayRequest);





                                            Toast.makeText(getApplicationContext(),"???????????????: "+poster_num,Toast.LENGTH_SHORT).show();
                                            return;

                                        } else {

                                            Toast.makeText(getApplicationContext(),"??????",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };


                            // ????????? Volley??? ?????? ??????
                            CancleRequest cancleRequest = new CancleRequest(cur_user_id, responseListener3);
                            RequestQueue queue = Volley.newRequestQueue(PosterActivity.this);
                            queue.add(cancleRequest);




                            Response.Listener<String> responseListener4 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {

                                        System.out.println("hanjiyoon"+ response);
                                        JSONObject jsonObject = new JSONObject(response);

                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {

                                            //????????????
                                            String url = "http://hanjiyoon.dothome.co.kr/like2_cancle.php";

                                            //????????? JsonArray ??? ??????
                                            //JsonArrayRequest??? ??????
                                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                                                //volley ?????????????????? GET????????? ?????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????? POST ?????? ??????

                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    //db ?????? ?????????
                                                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();



                                                    try {

                                                        for (int i = 0; i < response.length(); i++) {

                                                            JSONObject jsonObject = response.getJSONObject(i);

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

                                            //?????? ?????? ????????? ??????????????? ????????? ?????? ??????
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                            //???????????? ?????? ?????? ??????
                                            requestQueue.add(jsonArrayRequest);



                                            Toast.makeText(getApplicationContext(),"???????????????: "+poster_num,Toast.LENGTH_SHORT).show();
                                            return;

                                        } else {

                                            Toast.makeText(getApplicationContext(),"??????",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            // ????????? Volley??? ?????? ?????? ????????????
                            LikeRequest likeRequest2 = new LikeRequest(cur_user_id, article_num, type,responseListener4);
                            RequestQueue queue2 = Volley.newRequestQueue(PosterActivity.this);
                            queue2.add(likeRequest2);

                            //end of ???????????????


                        }

                        //5??? - ??????
                        else if(menuItem.getItemId() == R.id.delete_menu){
                            Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();

                            //????????? ?????? ?????? ???
                            Response.Listener<String> responseListener5 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {

                                        System.out.println("hanjiyoon"+ response);
                                        JSONObject jsonObject = new JSONObject(response);

                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {

                                            //????????????
                                            String url = "http://hanjiyoon.dothome.co.kr/delete.php";

                                            //????????? JsonArray ??? ??????
                                            //JsonArrayRequest??? ??????
                                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                                                //volley ?????????????????? GET????????? ?????? ??????????????? ????????? ?????? ???????????? ??????????????? ???????????? POST ?????? ??????

                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    //db ?????? ?????????
                                                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();



                                                    try {

                                                        for (int i = 0; i < response.length(); i++) {

                                                            JSONObject jsonObject = response.getJSONObject(i);

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

                                            //?????? ?????? ????????? ??????????????? ????????? ?????? ??????
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                            //???????????? ?????? ?????? ??????
                                            requestQueue.add(jsonArrayRequest);



                                            Toast.makeText(getApplicationContext(),"???????????????: "+poster_num,Toast.LENGTH_SHORT).show();
                                            return;

                                        } else {

                                            Toast.makeText(getApplicationContext(),"??????",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };

                            // ????????? Volley??? ?????? ??????
                            //cancle ??? ?????? ????????? ??????
                            CancleRequest deleteRequest = new CancleRequest(cur_user_id,responseListener5);
                            RequestQueue queue = Volley.newRequestQueue(PosterActivity.this);
                            queue.add(deleteRequest);



                        }

                        return false;
                    }
                });

                popupMenu.show();










            }//end of onclick


        });

        comment_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent( PosterActivity.this, CommentActivity.class );


                intent2.putExtra("cur_user_id", cur_user_id);

                intent2.putExtra("TYPE", type);
                intent2.putExtra( "ARTICLE_NUM", poster_num);
                intent2.putExtra("THUMBNAIL", thumb_image);
                intent2.putExtra( "USER_ID", user_id);
                intent2.putExtra( "UPDATE_DATE", update_date);
                intent2.putExtra("LAST_DATE", last_date);
                intent2.putExtra("ARTICLE_NUM", article_num);
                intent2.putExtra("HASH_TAG", hash_tag);
                intent2.putExtra("POSTER_IMAGE", poster_image);
                intent2.putExtra("TITLE", title);
                intent2.putExtra("CONTENT", content);
                intent2.putExtra("SCRAP",scrap);



                startActivity( intent2 );

            }
        });







    }
}
