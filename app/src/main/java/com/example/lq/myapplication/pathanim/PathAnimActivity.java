package com.example.lq.myapplication.pathanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.path.ArrowView;

public class PathAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);
        final ArrowView arrow_view = findViewById(R.id.arrow_view);
        arrow_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrow_view.state == 0) {
                    arrow_view.up();
                } else {
                    arrow_view.down();
                }
            }
        });
    }
}
