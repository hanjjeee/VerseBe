package com.example.versebe.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.example.versebe.R;

public class Sub extends ScrollView {

    public Sub(Context context, AttributeSet attrs, int num) {
        super(context, attrs);

        if(num==1)
        {
            init1(context);
        }
        else if(num==2)
        {
            init2(context);
        }
        else if(num==3)
        {
            init3(context);
        }
        else;
    }

    public Sub(Context context, int num) {

        super(context);

        if(num==1)
        {
            init1(context);
        }
        else if(num==2)
        {
            init2(context);
        }
        else if(num==3)
        {
            init3(context);
        }
        else;

    }

    private void init1(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_basic,this,true);
    }

    private void init2(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_colorful,this,true);
    }

    private void init3(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_basic,this,true);
    }

}



