package com.example.versebe.user;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.versebe.R;

public class CustomDialog extends Dialog
{

    CustomDialog dialog;

    TextView title;
    TextView content;
    TextView hashtag;
    Button OK;



    public CustomDialog(Context context)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        dialog = this;


        OK = this.findViewById(R.id.customdialog_OK);

        title = findViewById(R.id.customdialog_title);
        content = findViewById(R.id.customdialog_content);
        hashtag = findViewById(R.id.customdialog_hashtag);



    }


}

