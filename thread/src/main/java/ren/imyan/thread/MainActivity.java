package ren.imyan.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final int SUCCEED = 1;
    private final int ERROR = 2;

    private LinearLayout loading;
    private ImageView imageView;
    private EditText input;
    private Button btn;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCEED:
                    loading.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                case ERROR:
                    loading.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getImageView();
    }

    private void initView() {
        loading = findViewById(R.id.loading);
        imageView = findViewById(R.id.image);

        input = findViewById(R.id.input);
        btn = findViewById(R.id.btn);
    }

    private void getImageView() {
        btn.setOnClickListener(v -> {
            if (!input.getText().toString().trim().isEmpty()) {
                loading.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                new Thread(() -> {
                    Bitmap bitmap = GetImageUtil.getImage(input.getText().toString().trim());

                    Message message = new Message();
                    if (bitmap != null) {
                        message.obj = bitmap;
                        message.what = SUCCEED;
                    }else{
                        message.what = ERROR;
                    }
                    mHandler.sendMessage(message);

                }).start();
            } else {
                Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
            }
        });
    }
}