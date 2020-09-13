package com.example.testapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ApplicationActivity extends AppCompatActivity {
    private static final String TAG = "MyApplication";
    /**
     * Application的作用：
     *      1、共享全局状态或变量
     *      2、初始化全应用所需的服务
     *      （不过需要注意的是回调函数都是在UI线程中执行，防止ANR不要做耗时的操作）
     */
    /**
     * Application与静态单例的区别：
     *      1、静态单例的模块区分更为明显（符合单一职责）
     *      2、Application有访问资源的能力，但回调函数受系统的控制，单例可以由自己控制
     * 综上：如果能用静态单例实现的功能，就不要用Application去实现
     * 因为提供资源的能力也可以把context对象传给静态单例（此处要防止内存泄漏）
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        Log.d(TAG, "onCreate,application:" + getApplication());
    }
    //当系统配置发生变更时回调，所有回调函数都在UI线程中调用
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }
    //当内存吃紧时回调
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }
}