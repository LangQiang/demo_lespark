package com.example.lq.myapplication.retractable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.utils.ToastUtil2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetractableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retractable);
        TextView tv = findViewById(R.id.textTv);

        String str =
                "#心情# #我怎么还没对象# " +
                "#心情# #我怎么还没对象# " +
                "#心情# #我怎么还没对象# " +
                "#心情# #我怎么还没对象# " +
                "#随手拍# #认识点朋友# #等一个人# #我们#";

        SpannableString spannableString = refreshSpannableString((ExpandableTextViewNew) tv, str, this);
        SpannableStringBuilder ssbExpand = new SpannableStringBuilder();
        ssbExpand.append(spannableString);
//        SpannableString ss = new SpannableString(" 收起");
//        ss.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//
//            }
//        },0,ss.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        ssbExpand.append(ss);
        tv.setText(spannableString);
        TextView tv2 = findViewById(R.id.textTv2);
        tv2.setText(ssbExpand);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static SpannableString refreshSpannableString(ExpandableTextViewNew editText, String content, final Context context
                                              ) {
        editText.clearComposingText();

        try {
            SpannableString spannableString = new SpannableString(content);
            Pattern r;
            Matcher m;
            r = Pattern.compile("#[^#]{1,60}#");
            m = r.matcher(content);
            ClickableSpan span;
            int i = 0;
            ArrayList<ClickableSpan> spans = new ArrayList<ClickableSpan>();
            while (m.find()) {
                final String tag = content.substring(m.start() + 1, m.end() - 1);
                if (TextUtils.isEmpty(tag.trim())){
                    i++;
                    continue;
                }

                span = new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.parseColor("#ff6699cc"));
                        ds.setUnderlineText(true);
                    }
                    @Override
                    public void onClick(View widget) {
                        ToastUtil2.showToast(tag);
                    }
                };
                spans.add(span);
                spannableString.setSpan(spans.get(i), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannableString.setSpan(new UnderlineSpan(){
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setColor(Color.parseColor("#ff6699cc"));//设置颜色
//                        ds.setUnderlineText(false);//去掉下划线
//                    }
//                }, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                i++;
            }
            return spannableString;
        } catch (Exception e) {
        }
        return null;
    }
}
