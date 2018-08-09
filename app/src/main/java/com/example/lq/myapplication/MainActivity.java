package com.example.lq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lq.myapplication.anims.AnimsActivity;
import com.example.lq.myapplication.anims.TravelWithYouActivity;
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
import com.example.lq.myapplication.picpreview.TestPicActivity;
import com.example.lq.myapplication.ratio.RatioViewActivity;
import com.example.lq.myapplication.stickylist.StickyListActivity;
import com.example.lq.myapplication.swipeback.TestSwipeBackActivity;
import com.example.lq.myapplication.textureview.TextureDemoActivity;
import com.example.lq.myapplication.utils.ToastUtil2;
import com.example.lq.myapplication.xfermode.XFerModeActivity;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements App.ITest{
    final ArrayList<Integer> a = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //b
        Log.e("ippp", Arrays.toString(";".split(";")));

        for (int i = 0; i < 100000; i++) {
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

        Glide.with(this).load("http://lp-qiniu.gaypark.cn/attach/haalcqWqqGYq").into((ImageView) findViewById(R.id.personal_anchor_bg));

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

        findViewById(R.id.sticky).setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(MainActivity.this, AnimsActivity.class));
//                a.add(2);
            }
        });

        findViewById(R.id.travel_with_you).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TravelWithYouActivity.class));
            }
        });
        try {
            throw new UnsatisfiedLinkError();
        } catch (Error e) {

        }
        long txTime = SystemClock.currentThreadTimeMillis() / 1000 + 2323232;
        String liveCode = 23636 + "_" + getStreamIdFromPushUrl("rtmp://23636.livepush.myqcloud.com/live/23636_562dafbac2fa510d99f93aea?txSecret=303b7222f25bb2d69085d087bf0a7f88&txTime=5B62BF58&bizid=23636").subID;
        String txSecret = Utils.getMD5("66654b35a1fed8c9e9aab52db23109e2" + liveCode + Long.toHexString(txTime).toUpperCase());
        String ext = "?bizid=" + 23636 + "&txSecret=" + txSecret + "&txTime=" + Long.toHexString(txTime).toUpperCase();
        String accPlayUrl = "rtmp://" + 23636 + ".liveplay.myqcloud.com/live/" + liveCode + ext;
        Log.e("rtmp",accPlayUrl);
    }

    @Override
    public void test() {
        ToastUtil2.showToast("test is run");
    }

    public StreamIDS getStreamIdFromPushUrl(String pushUrl) {
        int index = pushUrl.indexOf("?");
        if (index == -1)
            return null;
        String substr = pushUrl.substring(0, index);
        int index_2 = substr.lastIndexOf("/");
        String streamID = substr.substring(index_2 + 1, index);
        String prefix = 23636 + "_";
        String subID = streamID.substring(prefix.length(), streamID.length());
        StreamIDS ids = new StreamIDS();
        ids.setStreamID(streamID);
        ids.setSubID(subID);
        return ids;
    }

    class StreamIDS {
        private String streamID = "";
        private String subID = "";

        public String getStreamID() {
            return streamID;
        }

        public void setStreamID(String streamID) {
            this.streamID = streamID;
        }

        public String getSubID() {
            return subID;
        }

        public void setSubID(String subID) {
            this.subID = subID;
        }
    }

     static class Utils {
        public static String getMD5(String str) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                return byteArrayToHex(md.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }

        public static String byteArrayToHex(byte[] byteArray) {
            char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
            char[] resultCharArray =new char[byteArray.length * 2];
            int index = 0;
            for (byte b : byteArray) {
                resultCharArray[index++] = hexDigits[b>>>4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            return new String(resultCharArray);
        }

        public static String S4 () {
            return UUID.randomUUID().toString().substring(9, 13);
        }

        /**
         * 随机生成user_id
         */
        public static String genUserIdByRandom () {
            return "user_" + (S4() + S4() + "_" + S4());
        }

        /**
         * 随机生成room_id
         */
        public static String genRoomIdByRandom () {
            return "room_" + (S4() + S4() + "_" + S4());
        }


    }

}
