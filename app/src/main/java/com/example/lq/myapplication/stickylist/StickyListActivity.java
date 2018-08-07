package com.example.lq.myapplication.stickylist;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.swipeback.SwipeBackActivity;
import com.example.lq.myapplication.widget.FinishActivity;

import java.util.ArrayList;

public class StickyListActivity extends SwipeBackActivity {
    private RecyclerView rv;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);
        final ListView lv = findViewById(R.id.sticky_list_view);
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("aaa");
        }
        final MyListAdapter myListAdapter = new MyListAdapter(this);
        lv.setAdapter(myListAdapter);
        ImageView iv = findViewById(R.id.iv);
//        RelativeLayout parent = (RelativeLayout) iv.getParent();
//        parent.removeView(iv);
//        lv.addHeaderView(iv);
//        ScrollContainView scrollContainView = findViewById(R.id.scv);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        list.add("v");
        myListAdapter.notifyDataSetChanged();
        lv.smoothScrollToPosition(myListAdapter.getCount() - 1);

        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
//                list.add("");
//                myListAdapter.notifyDataSetChanged();
//                lv.smoothScrollToPosition(myListAdapter.getCount() - 1);
            }
        },500);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(300);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();

                    }
                });
            }
        }).start();
    }
     class MyListAdapter extends BaseAdapter{
        Context context;
        public MyListAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context,R.layout.sticky_list_item_layout,null);
            }
            return convertView;
        }
    }
}
