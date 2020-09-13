package com.example.testapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTwoActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    private int[] mLayoutIds = {
            R.layout.layout_first,
            R.layout.layout_second,
            R.layout.layout_third
    };
    private List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager2 = findViewById(R.id.view_pager);
        list = new ArrayList<>();
        for (int index = 0; index < mLayoutIds.length; index++) {
            View view = getLayoutInflater().inflate(mLayoutIds[index], null);
            list.add(view);
        }
        viewPager2.setAdapter(adapter);
    }
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_first, parent, false);
            return new RecyclerView.ViewHolder(view){

            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mLayoutIds.length;
        }
    };
}