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
    CustomDialog m_oDialog;

    TextView title;
    TextView content;
    TextView hashtag;



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

        m_oDialog = this;



        Button OK = (Button)this.findViewById(R.id.dialog_OK);

        OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //title.getText().toString();
                //content.getText().toString();
                //hashtag.getText().toString();




                onClickBtn(v);
            }
        });
    }

    public void onClickBtn(View _oView)
    {
        this.dismiss();
    }
}

