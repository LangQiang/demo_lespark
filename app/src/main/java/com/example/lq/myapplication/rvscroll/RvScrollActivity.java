package com.example.lq.myapplication.rvscroll;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.lq.myapplication.R;

import java.util.ArrayList;


public class RvScrollActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RvScrollAdapter rvScrollAdapter;
    private ArrayList<Integer> data = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private View view;

    private int total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_scroll);
        for (int i = 0; i < 100; i++) {
            data.add(i);
        }
        view = findViewById(R.id.view);
        rv = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rvScrollAdapter = new RvScrollAdapter(R.layout.rv_scroll_item,data);
        rv.setAdapter(rvScrollAdapter);
        rvScrollAdapter.addHeaderView(View.inflate(this,R.layout.rv_scroll_header,null));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                total += dy;
                Log.e("rv_scroll", total + "");
                int pos = layoutManager.findFirstVisibleItemPosition();
                if (pos == 0) {
                    Log.e("rv_scroll", "findFirstVisibleItemPosition: " + pos);
                    View view = layoutManager.findViewByPosition(pos);
                    int y = 0 - view.getTop();
                    float alpha = 1 - y / 120f;
                    setAlphaByRvScrollY(alpha < 0 ? 0 : alpha);
                }

            }
        });

    }

    public void setAlphaByRvScrollY(float alpha) {
        Log.e("rv_scroll","alpha: " + alpha);
        view.setAlpha(alpha);
    }
}
