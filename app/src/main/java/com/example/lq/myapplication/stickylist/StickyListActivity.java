package com.example.lq.myapplication.stickylist;

import android.content.Context;
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

public class StickyListActivity extends AppCompatActivity {
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_list);
        ListView lv = findViewById(R.id.sticky_list_view);
        lv.setAdapter(new MyListAdapter(this));
        ImageView iv = findViewById(R.id.iv);
        RelativeLayout parent = (RelativeLayout) iv.getParent();
        parent.removeView(iv);
        lv.addHeaderView(iv);
        ScrollContainView scrollContainView = findViewById(R.id.scv);

    }
    static class MyListAdapter extends BaseAdapter{
        Context context;
        public MyListAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return 100;
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
