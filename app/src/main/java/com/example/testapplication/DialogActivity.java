package com.example.testapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.testapplication.model.MyDialog;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void alertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确定退出程序吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public void myDialog(View view) {
        MyDialog myDialog = new MyDialog(this,R.style.mydialog);
        myDialog.show();
    }

    public void popupwindow(View view) {
        //准备弹窗需要的视图
        View v = LayoutInflater.from(this).inflate(R.layout.activity_dialog,null);
        //要显示的视图、宽、高、是否可触摸
        final PopupWindow popupWindow = new PopupWindow(v, 200, 200, true);
        //设置弹窗外部能否触摸，默认为true
        popupWindow.setOutsideTouchable(false);
        //设置弹窗能否触摸，即内部的点击事件
        popupWindow.setTouchable(false);
        //设置弹窗背景颜色（这里设置为透明色）
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //创建动画
        popupWindow.setAnimationStyle(R.style.translate_anim);
        //显示；参数：锚、相对位置x轴距离、y轴距离
        popupWindow.showAsDropDown(view,100,100);
    }
}