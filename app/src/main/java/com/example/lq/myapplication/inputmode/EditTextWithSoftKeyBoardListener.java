package com.example.lq.myapplication.inputmode;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextWithSoftKeyBoardListener extends android.support.v7.widget.AppCompatEditText {
    public EditTextWithSoftKeyBoardListener(Context context) {
        super(context);
    }

    public EditTextWithSoftKeyBoardListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextWithSoftKeyBoardListener(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.e("testTheme","click key");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            super.onKeyPreIme(keyCode, event);
            if (onKeyBoardHideListener != null) {
                onKeyBoardHideListener.onKeyHide(keyCode, event);
            }
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
    private OnKeyBoardHideListener onKeyBoardHideListener;
    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public interface OnKeyBoardHideListener{
        void onKeyHide(int keyCode, KeyEvent event);
    }
}
