package com.example.lq.myapplication.inputmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.utils.ToastUtil2;

public class InputModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mode);
        findViewById(R.id.adjust_nothing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                ToastUtil2.showToast("set SOFT_INPUT_ADJUST_NOTHING");
            }
        });
        findViewById(R.id.adjust_pan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                ToastUtil2.showToast("set SOFT_INPUT_ADJUST_PAN");

            }
        });
        findViewById(R.id.adjust_resize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                ToastUtil2.showToast("set SOFT_INPUT_ADJUST_RESIZE");

            }
        });
        findViewById(R.id.show_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow();
                View view = View.inflate(InputModeActivity.this,R.layout.input_pop,null);
                popupWindow.setContentView(view);
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM,0,0);
            }
        });

    }
}
