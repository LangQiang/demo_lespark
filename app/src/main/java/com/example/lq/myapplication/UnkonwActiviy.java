package com.example.lq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lq.myapplication.utils.UIHelper;
import com.example.lq.myapplication.widget.VarietyView;

public class UnkonwActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unkonw_activiy);
        VarietyView varietyView = findViewById(R.id.variety_view);

        for (int i = 0; i < 5; i++) {
            TextView tv1 = new TextView(getBaseContext());
            tv1.setText(i + "aaaaaaaa");
            tv1.setMaxWidth(500);
            tv1.setPadding(UIHelper.dip2px(2),0,UIHelper.dip2px(2),0);
            tv1.setEllipsize(TextUtils.TruncateAt.END);
            tv1.setSingleLine(true);
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundColor(0xff3e54ff);
            tv1.setTextColor(0xffffffff);
            varietyView.addView(tv1,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        final TextView tv = findViewById(R.id.tv);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
//                layoutParams.width = 150;
//                tv.setLayoutParams(layoutParams);
//                tv.invalidate();
                ((TextView)tv).setMaxWidth(150);
            }
        });

        final View view = findViewById(R.id.bgIv);
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.width = 6000;
                view.setLayoutParams(lp);
                view.setTranslationX(-2000);
            }
        });
    }
}
