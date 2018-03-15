package com.example.lq.myapplication.xfermode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lq.myapplication.MainActivity;
import com.example.lq.myapplication.MyIV;
import com.example.lq.myapplication.R;

public class XFerModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfer_mode);
        findViewById(R.id.src).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XFerModeActivity.this, SrcActivity.class));
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
