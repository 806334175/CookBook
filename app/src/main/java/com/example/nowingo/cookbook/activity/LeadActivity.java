package com.example.nowingo.cookbook.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class LeadActivity extends BaseActivity{
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_lead);
    }

    @Override
    public void inidata() {

    }

    @Override
    public void iniview() {
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_lead_rl);
    }

    @Override
    public void setview() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setStartOffset(1000);
        alphaAnimation.setFillAfter(true);
        relativeLayout.startAnimation(alphaAnimation);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,4000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    startActivity(MainActivity.class);
                    finish();
                    break;
            }
        }
    };
}
