package com.example.lq.myapplication.choice;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.ToastUtil2;

import static android.app.usage.NetworkStats.Bucket.STATE_DEFAULT;

public class ChoiceActivity extends AppCompatActivity {
    int [] memory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memory = new int[1024 * 1024 * 30];
        setContentView(R.layout.activity_choice);
        findViewById(R.id.q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QABean qaBean = new QABean();
                qaBean.question = "天上有几颗星?";
                qaBean.answer1 = "1";
                qaBean.answer2 = "7";
                qaBean.answer3 = "数不出来";
                qaBean.questionNum = 1;
                qaBean.type = 0;
                AlertDialog show = ChoiceDialog.show(ChoiceActivity.this, qaBean, ChoiceDialog.STATUS_QUESTION, new ChoiceDialog.OnClickAnswerListener() {
                    @Override
                    public void onClickAnswer(String answer) {
                        Log.e("qa",answer);
                        ToastUtil2.showToast(answer);
                    }
                });
            }
        });
        findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
