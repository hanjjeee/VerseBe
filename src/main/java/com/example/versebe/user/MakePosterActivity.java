package com.example.versebe.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.versebe.R;

//scrap
public class MakePosterActivity extends AppCompatActivity {

    private Button scrap_button;
    private Button album_button;

    private RadioGroup radioGroup;
    private RadioButton naverButton;
    private RadioButton googleButton;
    private RadioButton daumButton;

    private boolean naver_flag, google_flag, daum_flag;




    private TextView store_name_view;

    private String store_name;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrap_dialog);

        radioGroup = findViewById(R.id.scrap_radio_group);
        naverButton = findViewById(R.id.scrap_naver_radioButton);
        googleButton = findViewById(R.id.scrap_google_radioButton);
        daumButton = findViewById(R.id.scrap_daum_radioButton);

        scrap_button = findViewById(R.id.scrap_start_button);
        album_button = findViewById(R.id.scrap_album_button);
        store_name_view = findViewById(R.id.scrap_storename);

        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        store_name = store_name_view.getText().toString();

        Sub n_layout = new Sub(getApplicationContext(), 1);
        LinearLayout con = (LinearLayout)findViewById(R.id.poster_view_l);
        con.addView(n_layout);

        scrap_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //웹 페이지 별 나누기
                if(naver_flag){
                    System.out.println("naver flag == true");
                }
                else if(google_flag){
                    System.out.println("google flag == true");
                }
                else if(daum_flag){
                    System.out.println("daum flag == true");
                }


                //포스터 생성화면으로 넘어간 후 크롤링 시작 하기
                //매개변수 param 은 각 flag 모두 넘기기
                Intent choice_intent = new Intent(MakePosterActivity.this, MakePosterActivity2.class);

                choice_intent.putExtra("store_name", store_name);
                choice_intent.putExtra("naver_flag", naver_flag);
                choice_intent.putExtra("google_flag", google_flag);
                choice_intent.putExtra("daum_flag", daum_flag);

                startActivity(choice_intent);


            }
        });


    }

    //라디오 버튼 클릭 리스너
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override public void onClick(View view) {
            Toast.makeText(MakePosterActivity.this, "button" , Toast.LENGTH_SHORT).show();
        } };

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.scrap_naver_radioButton){
                Toast.makeText(MakePosterActivity.this, "확인을 누르시면 네이버에서 사진을 가져옵니다.", Toast.LENGTH_SHORT).show();

                naver_flag = true;
                daum_flag = false; google_flag = false;

            } else if(i == R.id.scrap_google_radioButton){
                Toast.makeText(MakePosterActivity.this, "확인을 누르시면 구글에서 사진을 가져옵니다", Toast.LENGTH_SHORT).show();

                google_flag=true;
                naver_flag=false; daum_flag=false;
            }
            else if(i == R.id.scrap_daum_radioButton){
                Toast.makeText(MakePosterActivity.this, "확인을 누르시면 다음에서 사진을 가져옵니다.", Toast.LENGTH_SHORT).show();

                daum_flag=true;
                naver_flag=false; google_flag=false;
            }
        }
    };



}
