package com.example.versebe.main_page;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.versebe.R;
import com.example.versebe.user.ModifyActivity_email;
import com.example.versebe.user.ModifyActivity_name;
import com.example.versebe.user.ModifyActivity_password;
import com.example.versebe.user.ModifyActivity_phone;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Fragment1 extends Fragment {

    private Intent intent;
    private TextView my_name, my_email;
    private TextView cur_name, cur_email, cur_phone, cur_password;
    private ImageView my_image;
    private Button cur_name_edit_button, cur_email_edit_button,cur_phone_edit_button,cur_password_edit_button;
    private Button image_edit_button;

    private String my_image_path;
    private String cur_user_id;

    ImageView img;
    Bitmap bitmap_img;
    String encodeImageString;

    private static final String url = "http://hanjiyoon.dothome.co.kr/upload.php";
    private static final String url_profile = "http://hanjiyoon.dothome.co.kr/profile";

    public Fragment1(Intent intent) {

        this.intent = intent;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_mypage, container, false);

        cur_user_id = intent.getExtras().getString("cur_user_id");

        my_name = view.findViewById(R.id.my_name);
        cur_name = view.findViewById(R.id.cur_name);
        my_name.setText(cur_user_id);
        cur_name.setText(cur_user_id);

        my_email = view.findViewById(R.id.my_email);
        cur_email = view.findViewById(R.id.cur_email);
        my_email.setText(intent.getExtras().getString("userEmail"));
        cur_email.setText(intent.getExtras().getString("userEmail"));

        cur_phone = view.findViewById(R.id.cur_phone);
        cur_phone.setText(intent.getExtras().getString("userPhone"));


        cur_name_edit_button = view.findViewById(R.id.cur_name_edit_button);
        cur_email_edit_button = view.findViewById(R.id.cur_email_edit_button);
        cur_phone_edit_button = view.findViewById(R.id.cur_phone_edit_button);
        cur_password_edit_button = view.findViewById(R.id.cur_password_edit_button);
        image_edit_button = view.findViewById(R.id.image_edit_button);


        my_image_path="http://hanjiyoon.dothome.co.kr/profile/"+cur_user_id+".jpg";
        my_image = view.findViewById(R.id.my_image);

        //캐시x
        Glide.with(this).load(my_image_path).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(my_image);

        cur_name_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_email = new Intent(getActivity(), ModifyActivity_name.class);

                intent_email.putExtra( "userEmail", intent.getExtras().getString("userEmail") );

                startActivity( intent_email );

            }
        });

        cur_email_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_email = new Intent(getActivity(),ModifyActivity_email.class);

                intent_email.putExtra( "userEmail", intent.getExtras().getString("userEmail") );

                startActivity( intent_email );

            }
        });

        cur_phone_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_phone = new Intent(getActivity(),ModifyActivity_phone.class);

                intent_phone.putExtra( "userPhone", intent.getExtras().getString("userPhone") );

                startActivity( intent_phone );

            }
        });

        cur_password_edit_button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent_password = new Intent(getActivity(),ModifyActivity_password.class);
                intent_password.putExtra( "userPassword", intent.getExtras().getString("userPassword") );

                startActivity( intent_password );

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
                                bitmap_img = Bitmap.createScaledBitmap(bitmap_img, 150, 150, true);

                                //가져온 사진 처리
                                img = view.findViewById(R.id.my_image);
                                img.setImageBitmap(bitmap_img);

                                //서버 업로드
                                encodeBitmapImage(bitmap_img);
                                uploadDataToDB();
                                in.close();



                            }catch(Exception e)
                            {

                            }
                        }
                    }
                }
        );


        //이미지 수정 버튼 (이미지 세팅, 이미지 서버에 업로드)
        image_edit_button.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                    //갤러리 열리는지 테스트
                    //갤러리 호출
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent1.setAction(Intent.ACTION_PICK);
                    activityResultLauncher.launch(intent1);

            }
        });


        return view;
    }//end of on create view

    private void uploadDataToDB()
    {

        final String name = cur_user_id;

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

                map.put("userId", name);
                map.put("upload", encodeImageString);

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);

    }


    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        //encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }




}