package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(findViewById(R.id.ctx_btn));
        findViewById(R.id.ctx_btn2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActionMode(cb);
                return false;
            }
        });
        final Button button = findViewById(R.id.popup_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(MainActivity.this, button);
                menu.getMenuInflater().inflate(R.menu.context,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    ActionMode.Callback cb = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.context,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if(menuItem.getItemId() == R.id.ctx_btn2){
                Toast.makeText(MainActivity.this,"上下文模式",Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(R.id.delete == item.getItemId()){
            Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    public void toActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseFoodActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单资源
//        getMenuInflater().inflate(R.menu.option,menu);
        //参数1：组id；参数2：菜单项id；参数3：序号；参数4：具体title
        menu.add(1,1,1,"添加");
        SubMenu sub = menu.addSubMenu(1, 2, 2, "更多");
        sub.add(2,3,1,"删除");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.save){
            Toast.makeText(this,"保存",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}