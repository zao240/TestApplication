package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SharedPreferenceActivity extends AppCompatActivity {
    private static final String TAG = "SharedPreferenceActivit";
    private EditText editTextAccount,editTextPwd;
    private Button button;

    private EditText editTextInput;
    private Button buttonInput,buttonOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        int i = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(i != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        editTextAccount = findViewById(R.id.edittext_account);
        editTextPwd = findViewById(R.id.edittext_pwd);
        button = findViewById(R.id.button_login);
        editTextInput = findViewById(R.id.edittext_inputtext);
        buttonInput = findViewById(R.id.button_input);
        buttonOutput = findViewById(R.id.button_output);
        SharedPreferences preferences = getSharedPreferences("myshare", MODE_PRIVATE);
        //参数1：key  参数2：如果第一个参数不存在，则使用什么代替
        String account = preferences.getString("account", "");
        String pwd = preferences.getString("pwd", "");
        editTextAccount.setText(account);
        editTextPwd.setText(pwd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString();
                String pwd = editTextPwd.getText().toString();
                if("admin".equals(account) && "123".equals(pwd)){
                    //参数1：文件名 参数2：模式（私有模式表示别的应用无法访问）
                    SharedPreferences preferences = getSharedPreferences("myshare", MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("account",account);
                    edit.putString("pwd",pwd);
                    edit.commit();
                    Toast.makeText(SharedPreferenceActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SharedPreferenceActivity.this,"密码或用户名不正确",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickInOutput(View view) {
        String environmentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/immoc.txt";
        String FilesDirPath = getExternalFilesDir(null).getAbsolutePath()+ "/immoc.txt";
        Log.d(TAG, "onClickInOutput: " + environmentPath);
        Log.d(TAG, "onClickInOutput: " + FilesDirPath);
        //判断内存卡目录是否存在
        Log.d(TAG, "onClickInOutput: " + Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
        switch (view.getId()){
            case R.id.button_input:
                String inputText = editTextInput.getText().toString();
                File file = new File(FilesDirPath);
                try{
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file, true);
                    fos.write(inputText.getBytes());
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                break;
            case R.id.button_output:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}