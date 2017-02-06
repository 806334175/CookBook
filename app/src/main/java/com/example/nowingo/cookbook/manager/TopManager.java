package com.example.nowingo.cookbook.manager;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class TopManager {
    public static void setTop(View view, final AppCompatActivity activity, String title){
        LinearLayout linearLayout_back = (LinearLayout) view.findViewById(R.id.top_back);
        TextView textView_title = (TextView) view.findViewById(R.id.top_title);
        textView_title.setText(title);
        linearLayout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
