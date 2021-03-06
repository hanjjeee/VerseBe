package com.example.versebe.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.versebe.R;
import com.example.versebe.main_page.MainActivity;

import java.io.InputStream;

//scrap
public class MakePosterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    private Button scrap_button;
    private Button album_button;

    private RadioGroup radioGroup;
    private RadioButton naverButton;
    private RadioButton googleButton;
    private RadioButton daumButton;

    private Intent intent;
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

    private boolean naver_flag, google_flag, daum_flag;



    private TextView store_name_view;

    private String store_name;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrap_dialog);

        intent = getIntent();
        cur_user_id = intent.getExtras().getString("cur_user_id");

        type = intent.getExtras().getString("TYPE");
        thumb_image= intent.getExtras().getString("THUMBNAIL");
        user_id = intent.getExtras().getString("USER_ID");
        update_date= intent.getExtras().getString("UPDATE_DATE");
        last_date = intent.getExtras().getString("LAST_DATE");
        article_num = intent.getExtras().getInt("ARTICLE_NUM");
        hash_tag = intent.getExtras().getString("HASH_TAG");
        layout_image = intent.getExtras().getString("LAYOUT_IMAGE");
        title = intent.getExtras().getString("TITLE");


        radioGroup = findViewById(R.id.scrap_radio_group);
        naverButton = findViewById(R.id.scrap_naver_radioButton);
        googleButton = findViewById(R.id.scrap_google_radioButton);
        daumButton = findViewById(R.id.scrap_daum_radioButton);

        scrap_button = findViewById(R.id.scrap_start_button);

        store_name_view = findViewById(R.id.scrap_storename);

        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        store_name = store_name_view.getText().toString();

        Sub n_layout = new Sub(getApplicationContext(), article_num);
        LinearLayout con = (LinearLayout)findViewById(R.id.poster_view_l);
        con.addView(n_layout);






        scrap_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //??? ????????? ??? ?????????
                if(naver_flag){
                    System.out.println("naver flag == true");
                }
                else if(google_flag){
                    System.out.println("google flag == true");
                }
                else if(daum_flag){
                    System.out.println("daum flag == true");
                }


                store_name = store_name_view.getText().toString();

                //????????? ?????????????????? ????????? ??? ????????? ?????? ??????
                //???????????? param ??? ??? flag ?????? ?????????
                Intent make_intent = new Intent(MakePosterActivity.this, MakePosterActivity2.class);

                make_intent.putExtra("store_name", store_name);
                make_intent.putExtra("naver_flag", naver_flag);
                make_intent.putExtra("google_flag", google_flag);
                make_intent.putExtra("daum_flag", daum_flag);

                make_intent.putExtra("cur_user_id", cur_user_id);


                make_intent.putExtra("TYPE", type);
                make_intent.putExtra("THUMBNAIL", thumb_image);
                make_intent.putExtra("USER_ID", user_id);
                make_intent.putExtra("UPDATE_DATE", update_date);
                make_intent.putExtra("LAST_DATE",last_date);
                make_intent.putExtra("ARTICLE_NUM", article_num);
                make_intent.putExtra("HASH_TAG", hash_tag);
                make_intent.putExtra("LAYOUT_IMAGE", layout_image);
                make_intent.putExtra("TITLE", title);






                startActivity(make_intent);


            }
        });









    }








    //????????? ?????? ?????? ?????????
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            Toast.makeText(MakePosterActivity.this, "button" , Toast.LENGTH_SHORT).show();
        } };

    //????????? ?????? ?????? ?????????
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.scrap_naver_radioButton){
                Toast.makeText(MakePosterActivity.this, "????????? ???????????? ??????????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();

                naver_flag = true;
                daum_flag = false; google_flag = false;

            } else if(i == R.id.scrap_google_radioButton){
                Toast.makeText(MakePosterActivity.this, "????????? ???????????? ???????????? ????????? ???????????????", Toast.LENGTH_SHORT).show();

                google_flag=true;
                naver_flag=false; daum_flag=false;
            }
            else if(i == R.id.scrap_daum_radioButton){
                Toast.makeText(MakePosterActivity.this, "????????? ???????????? ???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();

                daum_flag=true;
                naver_flag=false; google_flag=false;
            }
        }
    };



}
