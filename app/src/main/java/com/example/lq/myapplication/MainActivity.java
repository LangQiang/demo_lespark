package com.example.lq.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;

import com.example.lq.myapplication.choice.ChoiceActivity;
import com.example.lq.myapplication.encrypt.EncryptActivity;
import com.example.lq.myapplication.ratio.RatioViewActivity;
import com.example.lq.myapplication.textureview.TextureDemoActivity;
import com.example.lq.myapplication.xfermode.XFerModeActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

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

        final MyIV myIV = findViewById(R.id.myiv);
        myIV.post(new Runnable() {
            @Override
            public void run() {
                myIV.start();
            }
        });
    }
}
