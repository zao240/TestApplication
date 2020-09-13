package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        final TextView textView = findViewById(R.id.textview_show_text);
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1001){
                    textView.setText("变变变");
                }
            }
        };
        findViewById(R.id.button_change_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1001);
                        Message message = Message.obtain();
                        message.what = 1002;
                        message.arg1 = 1003;
                        message.arg2 = 1004;
                        message.obj = HandlerActivity.this;
                        handler.sendMessage(message);
                        //定时任务
                        handler.sendMessageAtTime(message,SystemClock.uptimeMillis() + 2000);
                        handler.sendMessageDelayed(message,2000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }).start();
            }
        });
    }
    public static class MyHandler extends Handler{
        final WeakReference<HandlerActivity> activity;

        public MyHandler(HandlerActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity handlerActivity = activity.get();
            //todo
        }
    }
}