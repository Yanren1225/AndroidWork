package com.demo.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = findViewById(R.id.send);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,SecondActivity.class), 0);

        btnSend.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("nofity", "默认通知", NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notity")
                        .setContentTitle("0410190106 倪昊存")
                        .setContentText("欢迎访问我的网站 https://imyan.ren")
                        .setWhen(System.currentTimeMillis())
                        .setChannelId("nofity")
                        .setSmallIcon(R.drawable.ic_star)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.kirby));
                Notification notification = builder.build();
                manager.notify(1, notification);
            }
        });
    }
}