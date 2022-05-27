package com.example.versebe.main_page;

import static com.example.versebe.R.layout.mainpage_main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.versebe.R;
import com.example.versebe.user.ChoiceLayout;
import com.example.versebe.user.CustomDialog;
import com.example.versebe.user.FeedItem;
import com.example.versebe.user.FeedItemAdapter;
import com.example.versebe.user.FollowItem;
import com.example.versebe.user.FollowItemAdapter;
import com.example.versebe.user.LoginActivity;
import com.example.versebe.user.MakePosterActivity;
import com.example.versebe.user.MakePosterActivity2;
import com.example.versebe.user.RegisterActivity;
import com.example.versebe.user.SearchActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment0 extends Fragment {

    private Intent intent;

    public View view;
    private ImageView profile_view;

    private TextView mainpage_id;

    private TabLayout tabLayout2;
    private TabItem tabItem2_1;
    private TabItem tabItem2_2;
    private TabItem tabItem2_3;

    private ViewPager viewPager2;
    private FragmentAdapter adapter2;
    private Button search_button;
    private Button add_button;
    private Button upload_button;

    private String profile_image_path;
    private String cur_user_id;

    String insert_title;
    String insert_content;
    String insert_hashtag;

    Bitmap bitmap_img;
    String encodeImageString;
    String url = "http://hanjiyoon.dothome.co.kr/upload_poster.php";




    public Fragment0(Intent intent) {

        this.intent = intent;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(mainpage_main, container, false);

        cur_user_id = intent.getExtras().getString("cur_user_id");

        profile_view = view.findViewById(R.id.mainpage_image_view);
        mainpage_id = view.findViewById(R.id.mainpage_name);
        mainpage_id.setText(cur_user_id);

        profile_image_path = "http://hanjiyoon.dothome.co.kr/profile/"+cur_user_id+".jpg";

        search_button = view.findViewById(R.id.search_button);
        add_button = view.findViewById(R.id.mainpage_add_button);
        upload_button = view.findViewById(R.id.mainpage_upload_button);

        //캐시 o
        //Glide.with(this).load(profile_image_path).into(profile_view);

        //캐시 x
        Glide.with(this).load(profile_image_path).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(profile_view);




        search_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent search_intent = new Intent( getActivity(), SearchActivity.class);
                startActivity( search_intent );

            }
        });

        add_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.menu1, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.poster) {
                            Toast.makeText(getActivity(), "poster 클릭", Toast.LENGTH_SHORT).show();

                            //포스터 생성 인텐트로 넘어가기
                            Intent poster_intent = new Intent(getContext(), ChoiceLayout.class);
                            poster_intent.putExtra("cur_user_id",cur_user_id);
                            startActivity(poster_intent);


                        } else if (menuItem.getItemId() == R.id.layout) {
                            Toast.makeText(getActivity(), "layout 클릭", Toast.LENGTH_SHORT).show();


                            ////포스터 생성 인텐트로 넘어가기


                        }

                        return false;
                    }
                });

                popupMenu.show();
            }

        });


        //갤러리 연동
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == getActivity().RESULT_OK) {
                            Intent intent = result.getData();

                            try{

                                InputStream in = getActivity().getContentResolver().openInputStream(intent.getData());

                                bitmap_img = BitmapFactory.decodeStream(in);


                                CustomDialog content_dialog = new CustomDialog(getContext());
                                content_dialog.setCancelable(false);
                                content_dialog.show();

                                content_dialog.poster.setImageBitmap(bitmap_img);


                                //다이얼로그
                                content_dialog.OK.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {

                                        insert_title = content_dialog.title.getText().toString();
                                        insert_content = content_dialog.content.getText().toString();
                                        insert_hashtag = content_dialog.hashtag.getText().toString();


                                        if(insert_title.isEmpty()||insert_hashtag.isEmpty()||insert_content.isEmpty()){
                                            Toast.makeText(getContext(), String.format("모든 칸을 입력해 주세요."), Toast.LENGTH_SHORT).show();
                                            return;

                                        }

                                        //서버 업로드
                                        //bitmap_img = Bitmap.createScaledBitmap(bitmap_img, 250, 300, false);
                                        try {
                                            encodeBitmapImage(bitmap_img);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        uploadDataToDB();

                                        try {
                                            in.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                        content_dialog.dismiss();



                                    }//end of ok Click
                                });//end of ok button






                            }catch(Exception e)
                            {

                            }
                        }
                    }
                }
        );



        //서버 업로드
        upload_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //갤러리 호출
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent1.setAction(Intent.ACTION_PICK);

                activityResultLauncher.launch(intent1);

            }

        });

        ////내부 프레그먼트
        //getFragmentManager()가 아닌, getChildFragmentManager() 를 사용
        tabLayout2 = (TabLayout) view.findViewById(R.id.main_tab);


        tabItem2_1 = view.findViewById(R.id.mainpage_poster_tab);
        tabItem2_2 = view.findViewById(R.id.mainpage_layout_tab);
        tabItem2_3 = view.findViewById(R.id.mainpage_my_tab);


        viewPager2 = view.findViewById(R.id.feed_frame);

        adapter2 = new FragmentAdapter(getChildFragmentManager(), 1);

        adapter2.addFragment(new Fragment0_1(intent));
        adapter2.addFragment(new Fragment0_2(intent));
        adapter2.addFragment(new Fragment0_3(intent));

        viewPager2.setAdapter(adapter2);


        tabLayout2.setupWithViewPager(viewPager2);

        tabLayout2.getTabAt(0).setText("POSTER");
        tabLayout2.getTabAt(1).setText("LAYOUT");
        tabLayout2.getTabAt(2).setText("MY");


        return view;


    }


    private void uploadDataToDB()
    {



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();

                map.put("userId", cur_user_id);
                map.put("upload", encodeImageString);

                map.put("content",insert_content);
                map.put("hashtag",insert_hashtag);
                map.put("title",insert_title);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);

    }


    private void encodeBitmapImage(Bitmap bitmap) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);


        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();


        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);

        byteArrayOutputStream.close();



    }



}



