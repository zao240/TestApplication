package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.testapplication.model.Food;
import com.example.testapplication.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ChooseFoodActivity extends AppCompatActivity {
    private EditText nameEditText;
    private RadioGroup radioGroup;
    private CheckBox hotCheckBox, fishCheckBox, sourCheckBox;
    private SeekBar seekBar;
    private Button searchButton;
    private ImageView imageView;
    private ToggleButton toggleButton;
    private List<Food> mFoods;
    private List<Food> foodResult;
    private Person person;
    private boolean isFish;
    private boolean isSour;
    private boolean isHot;
    private int price;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_food);
        findViews();
        initData();
        setListeners();
    }

    private void setListeners() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(person != null){
                    person.setName(editable.toString());
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.maleRadioButton:
                        person.setSex("男");
                        break;
                    case R.id.femaleRadioButton:
                        person.setSex("女");
                        break;
                }
            }
        });
        fishCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isFish = b;
            }
        });
        sourCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSour = b;
            }
        });
        hotCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isHot = b;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("123", "onStopTrackingTouch: ");
                price = seekBar.getProgress();
                Toast.makeText(ChooseFoodActivity.this, "价格:" + price, Toast.LENGTH_SHORT).show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleButton.isChecked()) {
                    currentIndex++;
                    if (currentIndex < foodResult.size()) {
                        imageView.setImageResource(foodResult.get(currentIndex).getPic());
                    }
                } else {
                    if (currentIndex < foodResult.size()) {
                        String foodName = foodResult.get(currentIndex).getName();
                        String personName = nameEditText.getText().toString();
                        String sex = person.getSex();
                        Toast.makeText(ChooseFoodActivity.this, "菜名:" + foodName + ",人名:" + personName + ",性别:" + sex, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChooseFoodActivity.this, "没有啦", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void search() {
        if (foodResult == null) {
            foodResult = new ArrayList<>();
        }
        foodResult.clear();
        currentIndex = 0;
        for (int i = 0; i < mFoods.size(); i++) {
            Food food = mFoods.get(i);
            if (food != null) {
                if (food.getPrice() < price && (food.isHot() == isHot || food.isFish() == isFish
                        || food.isSour() == isSour)) {
                    foodResult.add(food);
                }
            }
        }
        if (currentIndex < foodResult.size()) {
            imageView.setImageResource(foodResult.get(currentIndex).getPic());
        }
    }

    private void initData() {
        mFoods = new ArrayList<>();
        // 初始化添加所有的数据
        mFoods.add(new Food("麻辣香锅", 55, R.drawable.malaxiangguo, true, false, false));
        mFoods.add(new Food("水煮鱼", 48, R.drawable.shuizhuyu, true, true, false));
        mFoods.add(new Food("麻辣火锅", 80, R.drawable.malahuoguo, true, true, false));
        mFoods.add(new Food("清蒸鲈鱼", 68, R.drawable.qingzhengluyu, false, true, false));
        mFoods.add(new Food("桂林米粉", 15, R.drawable.guilin, false, false, false));
        mFoods.add(new Food("上汤娃娃菜", 28, R.drawable.wawacai, false, false, false));
        mFoods.add(new Food("红烧肉", 60, R.drawable.hongshaorou, false, false, false));
        mFoods.add(new Food("木须肉", 40, R.drawable.muxurou, false, false, false));
        mFoods.add(new Food("酸菜牛肉面", 35, R.drawable.suncainiuroumian, false, false, true));
        mFoods.add(new Food("西芹炒百合", 38, R.drawable.xiqin, false, false, false));
        mFoods.add(new Food("酸辣汤", 40, R.drawable.suanlatang, true, false, true));
        person = new Person();
        foodResult = new ArrayList<>();
    }

    private void findViews() {
        nameEditText = findViewById(R.id.nameEditText);
        radioGroup = findViewById(R.id.sexRadioGroup);
        hotCheckBox = findViewById(R.id.hotCheckBox);
        fishCheckBox = findViewById(R.id.fishCheckBox);
        sourCheckBox = findViewById(R.id.sourCheckBox);
        seekBar = findViewById(R.id.seekBar);
        searchButton = findViewById(R.id.searchButton);
        imageView = findViewById(R.id.foodImageView);
        toggleButton = findViewById(R.id.showToggleButton);
        toggleButton.setChecked(true);
        seekBar.setProgress(30);
        price = 30;
    }

    public void toActivity(View view) {
        Intent intent = new Intent(ChooseFoodActivity.this, MainActivity.class);
        startActivity(intent);
    }
}