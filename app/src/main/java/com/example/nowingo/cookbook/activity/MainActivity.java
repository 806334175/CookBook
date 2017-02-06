package com.example.nowingo.cookbook.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.MainGridViewAdapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.constants.DataConstants;
import com.example.nowingo.cookbook.entity.Menus;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    GridView gridView;
    ArrayList<Menus.ResultBean.ListBean> arrayList;
    ArrayList<Integer> arrayList_iv;
    MainGridViewAdapter mainGridViewAdapter;

    TextView textView_xue,textView_zuo,textView_cai;
    EditText editText;
    ImageView imageView;

    Button button_collect,button_look,button_menu;

    Menus menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void inidata() {
        mainGridViewAdapter = new MainGridViewAdapter(this);
        menus = new Gson().fromJson(DataConstants.menus, Menus.class);
        arrayList = new ArrayList<>();
        arrayList = (ArrayList<Menus.ResultBean.ListBean>) menus.getResult().get(0).getList();
        arrayList_iv = new ArrayList<>();
        arrayList_iv.add(R.drawable.recipe_lable_4_2);
        arrayList_iv.add(R.drawable.recipe_lable_5_3);
        arrayList_iv.add(R.drawable.recipe_lable_1_1);
        arrayList_iv.add(R.drawable.recipe_lable_5_1);
        arrayList_iv.add(R.drawable.recipe_lable_2_3);
        arrayList_iv.add(R.drawable.recipe_lable_4_1);
        arrayList_iv.add(R.drawable.recipe_lable_3_1);
        arrayList_iv.add(R.drawable.recipe_lable_3_2);
        arrayList_iv.add(R.drawable.recipe_lable_1_3);

    }

    @Override
    public void iniview() {
        textView_xue = (TextView) findViewById(R.id.activity_main_xue);
        textView_zuo = (TextView) findViewById(R.id.activity_main_zuo);
        textView_cai = (TextView) findViewById(R.id.activity_main_cai);
        gridView = (GridView) findViewById(R.id.activity_main_gradview);
        editText = (EditText) findViewById(R.id.activity_main_et);
        imageView = (ImageView) findViewById(R.id.activity_main_iv_find);
        button_collect = (Button) findViewById(R.id.activity_main_btn_collect);
        button_look = (Button) findViewById(R.id.activity_main_btn_look);
        button_menu = (Button) findViewById(R.id.activity_main_btn_menu);
    }

    @Override
    public void setview() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/BB.TTF");
        textView_xue.setTypeface(typeface);
        textView_zuo.setTypeface(typeface);
        textView_cai.setTypeface(typeface);
        mainGridViewAdapter.setArrayList(arrayList);
        mainGridViewAdapter.setArrayList_iv(arrayList_iv);
        gridView.setAdapter(mainGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("title",arrayList.get(position).getName());
                bundle.putString("id",arrayList.get(position).getId());
                startActivity(SecondActivity.class,bundle);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")){
                    imageView.setVisibility(View.GONE);
                }else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",editText.getText().toString().trim());
                bundle.putString("id",editText.getText().toString().trim());
                bundle.putString("find","true");
                startActivity(SecondActivity.class,bundle);
            }
        });

        button_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title","我的收藏");
                bundle.putInt("int",0);
                startActivity(CollectAndLookActivity.class,bundle);
            }
        });
        button_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title","最近浏览");
                bundle.putInt("int",1);
                startActivity(CollectAndLookActivity.class,bundle);
            }
        });
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("menu",menus);
                startActivity(MenuActivity.class,bundle);
            }
        });
    }
}
