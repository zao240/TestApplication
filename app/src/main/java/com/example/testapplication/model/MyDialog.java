package com.example.testapplication.model;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.testapplication.R;

public class MyDialog extends Dialog {

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_layout);
        findViewById(R.id.yes_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //由于没法调用finish方法
                System.exit(0);
            }
        });
        findViewById(R.id.no_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消对话框
                dismiss();
            }
        });
    }
}
