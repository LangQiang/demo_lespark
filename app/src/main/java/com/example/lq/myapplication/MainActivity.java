package com.example.lq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lq.myapplication.choice.ChoiceActivity;
import com.example.lq.myapplication.collapsible.CollapsibleActivity;
import com.example.lq.myapplication.encrypt.EncryptActivity;
import com.example.lq.myapplication.flexible.FlexibleActivity;
import com.example.lq.myapplication.levelup.LevelUpActivity;
import com.example.lq.myapplication.ninepic.NinePicActivity;
import com.example.lq.myapplication.notify.NotifyActivity;
import com.example.lq.myapplication.pathanim.PathAnimActivity;
import com.example.lq.myapplication.ratio.RatioViewActivity;
import com.example.lq.myapplication.stickylist.StickyListActivity;
import com.example.lq.myapplication.swipeback.SwipeBackActivity;
import com.example.lq.myapplication.swipeback.TestSwipeBackActivity;
import com.example.lq.myapplication.textureview.TextureDemoActivity;
import com.example.lq.myapplication.xfermode.XFerModeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //b
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EncryptActivity.class));
            }
        });
        findViewById(R.id.ratio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RatioViewActivity.class));
            }
        });
        findViewById(R.id.choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChoiceActivity.class));
            }
        });

        findViewById(R.id.texture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextureDemoActivity.class));
            }
        });
        findViewById(R.id.xfermode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XFerModeActivity.class));
            }
        });

        findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotifyActivity.class));
            }
        });

        findViewById(R.id.sticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StickyListActivity.class));
            }
        });
        findViewById(R.id.flexible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlexibleActivity.class));
            }
        });
        findViewById(R.id.nine_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NinePicActivity.class));
            }
        });
        findViewById(R.id.path_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PathAnimActivity.class));
            }
        });

        findViewById(R.id.level_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelUpActivity.class));
            }
        });

        findViewById(R.id.collapsible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CollapsibleActivity.class));
            }
        });

        findViewById(R.id.swipe_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestSwipeBackActivity.class));
            }
        });
    }
}
