package com.example.lq.myapplication.choice;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lq.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wx on 2018/1/18.
 */

public class ChoiceDialog {
    public static final int STATUS_QUESTION = 0;
    public static final int STATUS_CORRECT = 1;
    public static final int STATUS_WRONG = 2;
    public static final int STATUS_NO_SELECT = 3;
    public static final int STATUS_WATCH = 4;
    public static AlertDialog show(Activity activity, QABean qaBean, int currentStatus, final OnClickAnswerListener onClickAnswerListener) {

        View view = View.inflate(activity, R.layout.choice_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        params.width = 1000;
        params.height = 1000;
        dialog.getWindow().setAttributes(params);

        final CircleProgressBar circleProgressBar = view.findViewById(R.id.qa_progress);
        circleProgressBar.setMaxProgress(1000);
        circleProgressBar.setProgressStrokeWidth(15);
        circleProgressBar.setPaintCap(Paint.Cap.ROUND);

        ImageView avatar = view.findViewById(R.id.qa_avatar);

        final ProgressBarWithText progressBarWithText1 = view.findViewById(R.id.progress_with_text1);
        final ProgressBarWithText progressBarWithText2 = view.findViewById(R.id.progress_with_text2);
        final ProgressBarWithText progressBarWithText3 = view.findViewById(R.id.progress_with_text3);
        TextView questionTv = view.findViewById(R.id.question);
        questionTv.setText(qaBean.questionNum + "." + qaBean.question);
        if (currentStatus == STATUS_QUESTION) {
            progressBarWithText1.setProgressDrawable(activity.getResources().getDrawable(R.drawable.choice_progress_question_bg));
            progressBarWithText2.setProgressDrawable(activity.getResources().getDrawable(R.drawable.choice_progress_question_bg));
            progressBarWithText3.setProgressDrawable(activity.getResources().getDrawable(R.drawable.choice_progress_question_bg));
            progressBarWithText1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickAnswerListener != null) {
                        onClickAnswerListener.onClickAnswer(progressBarWithText1.getNormalText());
                    }
                    progressBarWithText1.setProgress(1000);
                    progressBarWithText2.setSecondaryProgress(1000);
                    progressBarWithText3.setSecondaryProgress(1000);
                    progressBarWithText1.setOnClickListener(null);
                    progressBarWithText2.setOnClickListener(null);
                    progressBarWithText3.setOnClickListener(null);
                }
            });
            progressBarWithText2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickAnswerListener != null) {
                        onClickAnswerListener.onClickAnswer(progressBarWithText2.getNormalText());
                    }
                    progressBarWithText2.setProgress(1000);
                    progressBarWithText1.setSecondaryProgress(1000);
                    progressBarWithText3.setSecondaryProgress(1000);
                    progressBarWithText1.setOnClickListener(null);
                    progressBarWithText2.setOnClickListener(null);
                    progressBarWithText3.setOnClickListener(null);
                }
            });
            progressBarWithText3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickAnswerListener != null) {
                        onClickAnswerListener.onClickAnswer(progressBarWithText3.getNormalText());
                    }
                    progressBarWithText3.setProgress(1000);
                    progressBarWithText2.setSecondaryProgress(1000);
                    progressBarWithText1.setSecondaryProgress(1000);
                    progressBarWithText1.setOnClickListener(null);
                    progressBarWithText2.setOnClickListener(null);
                    progressBarWithText3.setOnClickListener(null);
                }
            });

            progressBarWithText1.setNormalTextAttrs(qaBean.answer1, 60,0xff7886a4,0,90,0);
            progressBarWithText2.setNormalTextAttrs(qaBean.answer2, 60,0xff7886a4,0,90,0);
            progressBarWithText3.setNormalTextAttrs(qaBean.answer3, 60,0xff7886a4,0,90,0);
        } else if (currentStatus == STATUS_CORRECT) {

        }else {

            progressBarWithText1.setTextAttrs("12345 人答对", 40, 0xff543eff, 0xff54ff3e, 2, 0, 90);
            progressBarWithText2.setTextAttrs("4321 人答错", 40, 0xff543eff, 0xff54ff3e, 2, 0, 90);
            progressBarWithText3.setTextAttrs("2332 人答错", 40, 0xff543eff, 0xff54ff3e, 2, 0, 90);
            progressBarWithText1.setNormalTextAttrs(qaBean.answer1, 60, 0xff000000, 0, 90, 0);
            progressBarWithText2.setNormalTextAttrs(qaBean.answer2, 60, 0xff000000, 0, 90, 0);
            progressBarWithText3.setNormalTextAttrs(qaBean.answer3, 60, 0xff000000, 0, 90, 0);
        }
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0,1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                progressBarWithText1.setProgress((Integer) animation.getAnimatedValue());
//                progressBarWithText2.setProgress((int)((Integer) animation.getAnimatedValue() / 2.0f));
//                progressBarWithText3.setProgress((int)((Integer) animation.getAnimatedValue() / 3.0f));
                //circleProgressBar.setProgress((Integer) animation.getAnimatedValue());
                circleProgressBar.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(9000);
        valueAnimator.start();

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                if (i >= 12) {
                    dialog.dismiss();
                }
                i ++;
            }
        };
        timer.schedule(timerTask,0,1000);


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("aaa","cancel" );
//                valueAnimator.cancel();
                timer.cancel();
            }
        });
        return dialog;
    }

    public interface OnClickAnswerListener {
        void onClickAnswer(String answer);
    }

}
