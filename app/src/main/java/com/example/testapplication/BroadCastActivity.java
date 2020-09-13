package com.example.testapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.broadcast.MyReceiver;

public class BroadCastActivity extends AppCompatActivity {
    private static final String TAG = "BroadCastActivity";

    public static final String MY_ACTION = "com.wwh.test.broadcast";
    public static final String BROADCAST_CONTENT = "broadcast_content";
    private MyReceiver myReceiver;
    private Button sendBroadCastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        sendBroadCastBtn = findViewById(R.id.btn_send_broadcast);

        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(MY_ACTION);
//        intentFilter.addDataScheme("package");
        //两个不同的应用也可以互相通过广播进行通信，只需要他们的action一样即可
        //注册广播
        registerReceiver(myReceiver, intentFilter);


        sendBroadCastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建广播
                Intent intent = new Intent(MY_ACTION);
                intent.putExtra(BROADCAST_CONTENT,"这是一个广播内容");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }
}