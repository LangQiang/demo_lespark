package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lq.myapplication.R;
import com.example.lq.myapplication.swipeback.SwipeBackActivity;
import com.example.lq.myapplication.utils.UIHelper;
import com.example.lq.myapplication.widget.FinishActivity;

import java.util.ArrayList;
import java.util.List;

public class StickyListActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private RecyclerView lv;
    private MyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);
        lv = findViewById(R.id.sticky_list_view);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("aaa");
        }
        final ImageView bgIv = findViewById(R.id.bg_iv);
        linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(R.layout.sticky_test_item_layout,list);
        lv.setAdapter(adapter);
        View view = View.inflate(this,R.layout.sticky_header_layout,null);
        adapter.addHeaderView(view);
        lv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int i = linearLayoutManager.findFirstVisibleItemPosition();
                View v = linearLayoutManager.findViewByPosition(i);

                if (i == 0 && v != null) {
                    int top = v.getTop();
                    bgIv.setTranslationY(top);
                    Log.e("top",top + "");
                }
                if (i != 0) {
                    bgIv.setTranslationY(-UIHelper.dip2px(224));
                }
            }
        });
    }

    class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(R.id.tv,s);
        }
    }


}
