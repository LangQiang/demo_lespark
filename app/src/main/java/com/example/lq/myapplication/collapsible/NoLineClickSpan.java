package com.example.lq.myapplication.collapsible;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class NoLineClickSpan extends ClickableSpan {


    public NoLineClickSpan() {
        super();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false); //去掉下划线
    }

}
