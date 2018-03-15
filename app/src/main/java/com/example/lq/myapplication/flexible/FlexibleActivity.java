package com.example.lq.myapplication.flexible;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.utils.UIHelper;

public class FlexibleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible);
        final TextView followTv = findViewById(R.id.follow_tv);
        final ImageView followIv = findViewById(R.id.follow_iv);
        final View chat = findViewById(R.id.chat);
        final View follow = findViewById(R.id.follow);
        int screenWidth = UIHelper.getScreenWidth();
        final int realWidth = screenWidth - UIHelper.dip2px(10);
        final ViewGroup.LayoutParams l1 = chat.getLayoutParams();
        l1.width = realWidth / 10 * 3;
        chat.setLayoutParams(l1);
        final int chatWidth = l1.width;

        final ViewGroup.LayoutParams l2 = follow.getLayoutParams();
        l2.width = realWidth / 10 * 7;
        follow.setLayoutParams(l2);
        final int followWidth = l2.width;
        chat.post(new Runnable() {
            @Override
            public void run() {
                final int changeWidth = l2.width - 100;
                findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        followIv.setAlpha(0f);
                        followTv.setAlpha(1f);
                        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0,changeWidth);
                        valueAnimator.setDuration(1000);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int value = (int) valueAnimator.getAnimatedValue();
                                l1.width = chatWidth + value;
                                chat.setLayoutParams(l1);
                                l2.width = followWidth - value;
                                follow.setLayoutParams(l2);
                            }
                        });
                        valueAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(followIv,"alpha",1f);
                                objectAnimator.setDuration(300);
                                objectAnimator.start();
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(followTv,"alpha",0f);
                                objectAnimator.setDuration(300);
                                objectAnimator.start();
                            }
                        });
                        valueAnimator.start();

                    }
                });
            }
        });
    }
}
