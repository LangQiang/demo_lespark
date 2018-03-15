package com.example.lq.myapplication.ninepic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lq.myapplication.R;
import com.example.lq.myapplication.pic.NinePicContainerPersonal;
import com.example.lq.myapplication.pic.PersonalImageBean;

import java.util.ArrayList;

public class NinePicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_pic);
        final NinePicContainerPersonal np = findViewById(R.id.np);
        np.setIntervalPx(9);
        final ArrayList<PersonalImageBean> list = new ArrayList<>();
        list.add(new PersonalImageBean());
        np.addAllPicsUrl(list);
        np.setOnItemClickListener(new NinePicContainerPersonal.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int i = list.size() + 1;
                int j = i % 10;
                if (j == 0) {
                    j++;
                }
                list.clear();

                for (int i1 = 0; i1 < j; i1++) {
                    list.add(new PersonalImageBean());
                }
                np.addAllPicsUrl(list);
            }
        });
    }
}
