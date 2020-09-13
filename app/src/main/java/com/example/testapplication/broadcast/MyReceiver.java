package com.example.testapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.testapplication.BroadCastActivity;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    //此方法会运行在主线程中，切勿做耗时的操作
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive,action: " + action);
            System.out.println(action);

            if (TextUtils.equals(action, BroadCastActivity.MY_ACTION)) {
                String stringExtra = intent.getStringExtra(BroadCastActivity.BROADCAST_CONTENT);
                Log.d(TAG, "BroadCastContent: " + stringExtra);
            }
        }
    }
}
