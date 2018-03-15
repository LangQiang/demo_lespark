package com.example.lq.myapplication.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lq.myapplication.BuildConfig;
import com.example.lq.myapplication.MainActivity;
import com.example.lq.myapplication.R;
import com.example.lq.myapplication.path.ArrowView;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notify);
        final ArrowView arrow_view = findViewById(R.id.arrow_view);
        arrow_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrow_view.state == 0) {
                    arrow_view.up();
                } else {
                    arrow_view.down();
                }
            }
        });
        findViewById(R.id.notify1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //android 8.0 必须写channel
                NotificationChannel channel = new NotificationChannel("1",
                        "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(true); //是否在桌面icon右上角展示小红点
                channel.setLightColor(Color.GREEN); //小红点颜色
                channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                mNotifyMgr.createNotificationChannel(channel);
                Intent intent = new Intent(NotifyActivity.this, MainActivity.class);
                intent.putExtra("aa","text1");
                PendingIntent pendingIntent = PendingIntent.getActivity(NotifyActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                final Notification notificationCompat = new NotificationCompat.Builder(NotifyActivity.this,"1")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setContentText("text1")
                        .setPriority(0)
                        .build();

                mNotifyMgr.notify(0,notificationCompat);
            }
        });
        findViewById(R.id.notify2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifyActivity.this, MainActivity.class);
                intent.putExtra("aa","text2");
                PendingIntent pendingIntent = PendingIntent.getActivity(NotifyActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                final Notification notificationCompat = new NotificationCompat.Builder(NotifyActivity.this,"aa")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setContentText("text2")
                        .setPriority(0)
                        .build();
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(1,notificationCompat);
            }
        });
    }
}
