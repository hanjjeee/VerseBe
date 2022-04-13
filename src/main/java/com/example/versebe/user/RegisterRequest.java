package com.example.versebe.user;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://hanjiyoon.dothome.co.kr/register.php";
    private Map<String, String> map;


    public RegisterRequest(String getUserId, String getUserEmail, String getUserPhone, String getUserPw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId", getUserId);
        map.put("userEmail",getUserEmail);
        map.put("userPhone", getUserPhone);
        map.put("userPassword", getUserPw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }



}
