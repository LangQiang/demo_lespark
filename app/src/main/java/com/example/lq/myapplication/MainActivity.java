package com.example.lq.myapplication;

import android.animation.TypeEvaluator;
import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lq.myapplication.anims.AnimsActivity;
import com.example.lq.myapplication.choice.ChoiceActivity;
import com.example.lq.myapplication.collapsible.CollapsibleActivity;
import com.example.lq.myapplication.colourful.ColourfulActivity;
import com.example.lq.myapplication.encrypt.EncryptActivity;
import com.example.lq.myapplication.flexible.FlexibleActivity;
import com.example.lq.myapplication.global.App;
import com.example.lq.myapplication.global.Constant;
import com.example.lq.myapplication.levelup.LevelUpActivity;
import com.example.lq.myapplication.likeview.LikeViewActivity;
import com.example.lq.myapplication.ninepic.NinePicActivity;
import com.example.lq.myapplication.notify.NotifyActivity;
import com.example.lq.myapplication.pathanim.PathAnimActivity;
import com.example.lq.myapplication.picpreview.PicPreviewActivity;
import com.example.lq.myapplication.picpreview.PicViewInfo;
import com.example.lq.myapplication.picpreview.TestPicActivity;
import com.example.lq.myapplication.ratio.RatioViewActivity;
import com.example.lq.myapplication.stickylist.StickyListActivity;
import com.example.lq.myapplication.swipeback.SwipeBackActivity;
import com.example.lq.myapplication.swipeback.TestSwipeBackActivity;
import com.example.lq.myapplication.textureview.TextureDemoActivity;
import com.example.lq.myapplication.utils.DeviceInfoManager;
import com.example.lq.myapplication.utils.Tasks;
import com.example.lq.myapplication.utils.ToastUtil2;
import com.example.lq.myapplication.xfermode.XFerModeActivity;

import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements App.ITest{
    final ArrayList<Integer> a = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //b
        Log.e("ippp",Arrays.toString(";".split(";")));

        for (int i = 0; i< 100000;i++) {
            a.add(i);
        }
        try {
            Log.e("ippp", InetAddress.getByName("1.1.1.1").toString());
        } catch (Exception e) {
            Log.e("ippp", e.toString());
        }

        App.getInstance().setITest(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                                Log.e("ippp", Arrays.toString(InetAddress.getAllByName("192.168.1.2;123.132.1.23")));

                    Log.e("ippp", InetAddress.getByAddress(new byte[]{(byte) 192, 1, 1, 1}).toString());
                } catch (Exception e) {
                    Log.e("ippp", e.toString());

                }
            }
        }).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EncryptActivity.class));
            }
        });
        findViewById(R.id.ratio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RatioViewActivity.class));
            }
        });
        findViewById(R.id.choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChoiceActivity.class));
            }
        });

        findViewById(R.id.texture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TextureDemoActivity.class));
            }
        });
        findViewById(R.id.xfermode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, XFerModeActivity.class));
            }
        });

        findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotifyActivity.class));
            }
        });

        findViewById(R. id. sticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StickyListActivity.class));
            }
        });
        findViewById(R.id.flexible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlexibleActivity.class));
            }
        });
        findViewById(R.id.nine_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NinePicActivity.class));
            }
        });
        findViewById(R.id.path_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PathAnimActivity.class));
            }
        });

        findViewById(R.id.level_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelUpActivity.class));
            }
        });

        findViewById(R.id.collapsible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CollapsibleActivity.class));
            }
        });

        findViewById(R.id.swipe_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestSwipeBackActivity.class));
            }
        });

        findViewById(R.id.hot_fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HotFixActivity.class));
            }
        });

        findViewById(R.id.colourful_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ColourfulActivity.class));
            }
        });

        findViewById(R.id.pic_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestPicActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        findViewById(R.id.like_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LikeViewActivity.class));
            }
        });

        findViewById(R.id.cake_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, AnimsActivity.class));
                a.add(2);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Constant.fun(a);
                }catch (Exception e) {
                    Log.e("aa","asf");
                }

            }
        }).start();


    }

    @Override
    public void test() {
        ToastUtil2.showToast("test is run");
    }

}
