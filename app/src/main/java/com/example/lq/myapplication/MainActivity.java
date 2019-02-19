package com.example.lq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lq.myapplication.anims.AnimsActivity;
import com.example.lq.myapplication.anims.TravelWithYouActivity;
import com.example.lq.myapplication.camerapreview.CameraPreviewBaseActivity;
import com.example.lq.myapplication.choice.ChoiceActivity;
import com.example.lq.myapplication.collapsible.CollapsibleActivity;
import com.example.lq.myapplication.colourful.ColourfulActivity;
import com.example.lq.myapplication.flexible.FlexibleActivity;
import com.example.lq.myapplication.global.App;
import com.example.lq.myapplication.inputmode.InputModeActivity;
import com.example.lq.myapplication.levelup.LevelUpActivity;
import com.example.lq.myapplication.likeview.LikeViewActivity;
import com.example.lq.myapplication.ninepic.NinePicActivity;
import com.example.lq.myapplication.notify.NotifyActivity;
import com.example.lq.myapplication.pathanim.PathAnimActivity;
import com.example.lq.myapplication.picpreview.TestPicActivity;
import com.example.lq.myapplication.retractable.RetractableActivity;
import com.example.lq.myapplication.rvscroll.RvScrollActivity;
import com.example.lq.myapplication.stickylist.StickyListActivity;
import com.example.lq.myapplication.swipeback.TestSwipeBackActivity;
import com.example.lq.myapplication.textureview.TextureDemoActivity;
import com.example.lq.myapplication.utils.MediaHelper;
import com.example.lq.myapplication.utils.ToastUtil2;
import com.example.lq.myapplication.widget.AnimTitleView;
import com.example.lq.myapplication.xfermode.XFerModeActivity;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements App.ITest{


    public void start() {

    }

    public void complete() {
        Log.e("progress","complete");
    }

    public void onProgress(int second,int duration) {
        Log.e("progress",second + " " + duration);
    }

    public native String avcodecinfo();
    public native int ffmpegRun( String[] cmd);
    final ArrayList<Integer> a = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.loadLibrary("avutil-55");
//        Log.e("ffmpeg",avcodecinfo());
        //addWater();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AnimTitleView anim_title_view = findViewById(R.id.anim_title_view);
        final FrameLayout bgView = anim_title_view.findViewById(R.id.bg_view);
        anim_title_view.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,144);
                layoutParams1.gravity = Gravity.BOTTOM;
                bgView.setLayoutParams(layoutParams1);
            }
        });

        Glide.with(this).load("http://lp-qiniu.gaypark.cn/attach/haalcqWqqGYq").into((ImageView) findViewById(R.id.personal_anchor_bg));
        final TextView tttv = findViewById(R.id.encrypt);
        findViewById(R.id.encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, EncryptActivity.class));
                String srcUrl = new File(Environment.getExternalStorageDirectory(),"b.mp4").getAbsolutePath();
                String water = new File(Environment.getExternalStorageDirectory(),"bw.gif").getAbsolutePath();
                String out = new File(Environment.getExternalStorageDirectory(),"output1.mp4").getAbsolutePath();
                MediaHelper.getInstance().addGifWater(srcUrl, water, out,298,253,0.5f,0.5f, new MediaHelper.CallBack() {
                    @Override
                    public void onStart() {
                        Log.e("progress","完成");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tttv.setText("开始");
                            }
                        });
                    }

                    @Override
                    public void onProgress(final int progress, final int duration) {
//                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//                            Log.e("progress","progress UI线程:" + Thread.currentThread().getName());
//
//                        } else {
//                            Log.e("progress","progress 子线程:" + Thread.currentThread().getName());
//                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tttv.setText(progress * 100 / duration + "%");
                            }
                        });
                        if (duration > 0) {
                            Log.e("progress",progress + "  " + duration);
                        }
                    }

                    @Override
                    public void onEnd() {
//                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//                            Log.e("progress","onEnd UI线程:" + Thread.currentThread().getName());
//
//                        } else {
//                            Log.e("progress","onEnd 子线程:" + Thread.currentThread().getName());
//                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tttv.setText("完成");
                            }
                        });

                        Log.e("progress","完成");
                    }
                });
            }
        });

        findViewById(R.id.retractable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetractableActivity.class));
            }
        });

        findViewById(R.id.input_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InputModeActivity.class));
            }
        });

        final TextView mTestTv = findViewById(R.id.ratio);
        mTestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraPreviewBaseActivity.class));
            }
        });
        //startActivity(new Intent(MainActivity.this, RatioViewActivity.class));
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
        findViewById(R.id.rv_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RvScrollActivity.class));
            }
        });
        findViewById(R.id.sb_xuqiu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UnkonwActiviy.class));
            }
        });

        long txTime = SystemClock.currentThreadTimeMillis() / 1000 + 2323232;
        String liveCode = 23636 + "_" + getStreamIdFromPushUrl("rtmp://23636.livepush.myqcloud.com/live/23636_562dafbac2fa510d99f93aea?txSecret=303b7222f25bb2d69085d087bf0a7f88&txTime=5B62BF58&bizid=23636").subID;
        String txSecret = Utils.getMD5("66654b35a1fed8c9e9aab52db23109e2" + liveCode + Long.toHexString(txTime).toUpperCase());
        String ext = "?bizid=" + 23636 + "&txSecret=" + txSecret + "&txTime=" + Long.toHexString(txTime).toUpperCase();
        String accPlayUrl = "rtmp://" + 23636 + ".liveplay.myqcloud.com/live/" + liveCode + ext;
        Log.e("rtmp",accPlayUrl);
    }

    private void addWater() {
        String srcUrl = new File(Environment.getExternalStorageDirectory(),"a1.mp4").getAbsolutePath();
        String water = new File(Environment.getExternalStorageDirectory(),"water.png").getAbsolutePath();
        String out = new File(Environment.getExternalStorageDirectory(),"output3.mp4").getAbsolutePath();
        String cmd = "ffmpeg -i "+ srcUrl +" -i "+water+" -filter_complex [1:v]scale=378:93[water1];[0:v][water1]overlay=10:10 -y "+out;
        //cmd = "ffmpeg -i " + srcUrl;
        final String[] cmds = cmd.split(" ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ffmpegRun(cmds);
                ToastUtil2.showToast("水印添加完成");
            }
        }).start();
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
