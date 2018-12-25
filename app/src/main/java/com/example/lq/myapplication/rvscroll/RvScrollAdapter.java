package com.example.lq.myapplication.rvscroll;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lq.myapplication.R;

import java.util.List;
import java.util.Random;

public class RvScrollAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private Random random = new Random();

    public RvScrollAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer s) {
        final TextView tv = baseViewHolder.getView(R.id.tv);
        tv.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        tv.setText(s + "");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
                layoutParams.height = 300;
                tv.setLayoutParams(layoutParams);
            }
        });
    }
}
