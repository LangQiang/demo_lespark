package com.example.lq.myapplication.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lq.myapplication.R;


public class AnimTitleView extends FrameLayout {
    private Context context;
    private ImageView leftIvWhite;
    private ImageView leftIvBlack;
    private ImageView rightIvWhite;
    private ImageView rightIvBlack;
    private TextView centerTv;
    private AnimationSet animationSetWhite;
    private AnimatorSet whiteSet;
    private AnimatorSet darkSet;
    private View bg_view;

    public AnimTitleView(@NonNull Context context) {
        this(context,null);
    }

    public AnimTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = inflate(context, R.layout.anim_title_view,null);
        addView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        leftIvWhite = findViewById(R.id.left_icon_white);
        leftIvBlack = findViewById(R.id.left_icon_black);
        rightIvWhite = findViewById(R.id.right_icon_white);
        rightIvBlack = findViewById(R.id.right_icon_black);
        centerTv = findViewById(R.id.center_tv);
        bg_view = findViewById(R.id.bg_view);
        initAnim();
    }

    private void initAnim() {
        PropertyValuesHolder alpha1Holder = PropertyValuesHolder.ofFloat(ALPHA, 1f, 0f);
        PropertyValuesHolder alpha2Holder = PropertyValuesHolder.ofFloat(ALPHA, 0f, 1f);

        //2: 0->1  1: 1->0
        ObjectAnimator leftIvWhiteAnim1 = ObjectAnimator.ofPropertyValuesHolder(leftIvWhite,alpha1Holder);
        ObjectAnimator leftIvWhiteAnim2 = ObjectAnimator.ofPropertyValuesHolder(leftIvWhite, alpha2Holder);
        ObjectAnimator leftIvDarkAnim1 = ObjectAnimator.ofPropertyValuesHolder(leftIvBlack,alpha1Holder);
        ObjectAnimator leftIvDarkAnim2 = ObjectAnimator.ofPropertyValuesHolder(leftIvBlack,alpha2Holder);

        ObjectAnimator rightIvWhiteAnim1 = ObjectAnimator.ofPropertyValuesHolder(rightIvWhite,alpha1Holder);
        ObjectAnimator rightIvWhiteAnim2 = ObjectAnimator.ofPropertyValuesHolder(rightIvWhite, alpha2Holder);
        ObjectAnimator rightIvDarkAnim1 = ObjectAnimator.ofPropertyValuesHolder(rightIvBlack,alpha1Holder);
        ObjectAnimator rightIvDarkAnim2 = ObjectAnimator.ofPropertyValuesHolder(rightIvBlack,alpha2Holder);

        ObjectAnimator bgViewAnim1 = ObjectAnimator.ofPropertyValuesHolder(bg_view,alpha1Holder);
        ObjectAnimator bgViewAnim2 = ObjectAnimator.ofPropertyValuesHolder(bg_view,alpha2Holder);

        whiteSet = new AnimatorSet();
        whiteSet.setDuration(600);
        whiteSet.play(leftIvWhiteAnim2);
        whiteSet.play(leftIvDarkAnim1).with(leftIvWhiteAnim2);
        whiteSet.play(rightIvDarkAnim1).with(leftIvWhiteAnim2);
        whiteSet.play(rightIvWhiteAnim2).with(leftIvWhiteAnim2);
        whiteSet.play(bgViewAnim1).with(leftIvWhiteAnim2);

        darkSet = new AnimatorSet();
        darkSet.setDuration(600);
        darkSet.play(leftIvWhiteAnim1);
        darkSet.play(leftIvDarkAnim2).with(leftIvWhiteAnim1);
        darkSet.play(rightIvDarkAnim2).with(leftIvWhiteAnim1);
        darkSet.play(rightIvWhiteAnim1).with(leftIvWhiteAnim1);
        darkSet.play(bgViewAnim2).with(leftIvWhiteAnim1);
    }

    public void showLightTitle() {
        if (darkSet.isRunning()) {
            darkSet.cancel();
        }
        whiteSet.start();
    }

    public void showDarkTitle() {
        if (whiteSet.isRunning()) {
            whiteSet.cancel();
        }
        darkSet.start();
    }

    public void setLeftIvWhiteIcon(@DrawableRes int id) {
        leftIvWhite.setImageDrawable(getResources().getDrawable(id));
    }

    public void setLeftIvBlackIcon(@DrawableRes int id) {
        leftIvBlack.setImageDrawable(getResources().getDrawable(id));
    }

    public void setRightIvWhiteIcon(@DrawableRes int id) {
        rightIvWhite.setImageDrawable(getResources().getDrawable(id));
    }

    public void setRightIvBlackIcon(@DrawableRes int id) {
        rightIvBlack.setImageDrawable(getResources().getDrawable(id));
    }

}
