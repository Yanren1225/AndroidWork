package ren.imyan.danmakuflame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity {

    private boolean isDanmakusShow = false;

    private LinearLayout layoutSend;
    private EditText editText;
    private DanmakuView danmakuView;
    private Button btnSend;

    private MediaPlayer mediaPlayer;

    private DanmakuContext danmakuContext;

    private final BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideBar();
        initView();
        initDanmakus();
    }

    private void hideBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
                insetsController.hide(WindowInsets.Type.systemBars());
                insetsController.hide(WindowInsets.Type.navigationBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    private void initView() {
        SurfaceView surfaceView = findViewById(R.id.video_view);
        layoutSend = findViewById(R.id.layout_send);
        editText = findViewById(R.id.input);
        danmakuView = findViewById(R.id.danmaku);
        btnSend = findViewById(R.id.send);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                playVideo(holder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });

    }

    private void playVideo(SurfaceHolder holder) {
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.video);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(arg0 -> {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            });
            mediaPlayer.setDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDanmakus() {
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                isDanmakusShow = true;
                danmakuView.start();
                generateDanmakus();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

        danmakuContext = DanmakuContext.create();
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.prepare(parser, danmakuContext);
        danmakuView.setOnClickListener(v -> {
            if (layoutSend.getVisibility() == View.GONE) {
                layoutSend.setVisibility(View.VISIBLE);
            } else {
                layoutSend.setVisibility(View.GONE);
                hideBar();
            }
        });

        btnSend.setOnClickListener(v -> {
            String content = editText.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                addDanmaku(content, true);
                editText.setText("");
            }
        });
    }

    private void addDanmaku(CharSequence danmakuContent, boolean isBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmakuContent;
        danmaku.padding = 6;
        danmaku.textSize = 30;
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (isBorder) {
            danmaku.borderColor = Color.RED;
        }
        danmakuView.addDanmaku(danmaku);
    }

    private void generateDanmakus() {
        new Thread(() -> {
            while (isDanmakusShow) {
                int num = new Random().nextInt(400);
                String content = num + "";
                addDanmaku(content, false);
                try {
                    Thread.sleep(num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared()) {
            if (danmakuView.isPaused()) {
                danmakuView.resume();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }
}