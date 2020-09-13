package com.example.testapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mLayoutIds.length;
        }

        /**
         * 是否可以复用
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(list.get(position));
        }
    };

    private ViewPager viewPager;

    private int[] mLayoutIds = {
            R.layout.layout_first,
            R.layout.layout_second,
            R.layout.layout_third
    };

    private ViewGroup viewGroup;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<View> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        viewPager = findViewById(R.id.view_pager);
        viewGroup = findViewById(R.id.dot_layout);

        for (int i = 0; i < mLayoutIds.length; i++) {
//            final View view = getLayoutInflater().inflate(mLayoutIds[i], null);
//            list.add(view);
            ImageView view = new ImageView(this);
            view.setImageResource(R.mipmap.ic_launcher);
            list.add(view);

            ImageView dot = new ImageView(this);
            dot.setImageResource(R.mipmap.dialog_bg);
            dot.setMaxHeight(100);
            dot.setMaxWidth(100);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.leftMargin = 20;
            dot.setLayoutParams(layoutParams);
            dot.setEnabled(false);
            viewGroup.addView(dot);
            imageViews.add(dot);
        }
        viewPager.setAdapter(adapter);
        //在页面中存在4个view
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
        setDotImage(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDotImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setDotImage(int position) {
        for (int i = 0; i < imageViews.size(); i++) {
            imageViews.get(i).setImageResource(position == i ? R.mipmap.dialog_bg : R.mipmap.ic_launcher);
        }
    }
}