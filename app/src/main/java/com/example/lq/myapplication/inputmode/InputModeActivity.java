package com.example.lq.myapplication.inputmode;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.utils.ToastUtil2;
import com.example.lq.myapplication.utils.UIHelper;

public class InputModeActivity extends AppCompatActivity {

    private EditText bottom_et;
    private int i = 0;
    private EditTextWithSoftKeyBoardListener inputEt;
    private TextView inputOkTv;
    private View inputClose;
    private AlertDialog alertDialog;

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
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                if (i % 3 == 0) {
                    popupWindow.setInputMethodMode(1);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    ToastUtil2.showToast("SOFT_INPUT_ADJUST_RESIZE");
                } else if (i % 3 == 1) {
                    popupWindow.setInputMethodMode(1);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    ToastUtil2.showToast("SOFT_INPUT_ADJUST_PAN");
                } else {
                    popupWindow.setInputMethodMode(1);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    ToastUtil2.showToast("SOFT_INPUT_ADJUST_NOTHING");
                }
                i ++ ;
                popupWindow.setAnimationStyle(R.style.anim_popup_dir);
                popupWindow.showAtLocation(findViewById(R.id.bottom_view), Gravity.BOTTOM,0,0);
            }
        });
        bottom_et = findViewById(R.id.bottom_et);
        findViewById(R.id.force).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UIHelper.showKeyboard(InputModeActivity.this,bottom_et);
            }
        });

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(InputModeActivity.this, R.layout.input_invite_code_layout, null);
                initInputView(dialogView);
                AlertDialog.Builder ab = new AlertDialog.Builder(InputModeActivity.this,R.style.dialog);
                //AlertDialog.Builder ab = new AlertDialog.Builder(InputModeActivity.this);
                alertDialog = ab.create();
                alertDialog.setView(dialogView, 0, 0, 0, 0);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                Window window = alertDialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    attributes.height = UIHelper.dip2px(252);
                    Log.e("testTheme", attributes.softInputMode + "");
                    window.setAttributes(attributes);
                }
            }
        });

    }

    private void initInputView(View view) {
        inputEt = view.findViewById(R.id.input_invite_code_et);
        inputEt.setOnKeyBoardHideListener(new EditTextWithSoftKeyBoardListener.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                Log.e("testTheme","hide");
            }
        });
        inputOkTv = view.findViewById(R.id.input_invite_code_ok);
        inputClose = view.findViewById(R.id.input_invite_code_close);
        inputClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    inputOkTv.setTextColor(0xffb3b3b3);
                    inputOkTv.setClickable(false);
                } else {
                    inputOkTv.setTextColor(0xffffffff);
                    inputOkTv.setClickable(true);
                }
            }
        });
        inputOkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = inputEt.getText().toString().trim();
            }
        });
    }
}
