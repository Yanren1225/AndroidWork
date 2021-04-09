package com.demo.imageasync;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ProgressBar progressBar;
    private EditText input;
    private LinearLayout load;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getImage();
    }

    private void initView(){
        btn = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progress);
        input = findViewById(R.id.input);
        load = findViewById(R.id.loading);
        imageView = findViewById(R.id.image);
    }

    private void getImage(){
        btn.setOnClickListener(v -> {
            if (!input.getText().toString().trim().isEmpty()) {
                GetImageUtil getImageUtil = new GetImageUtil(progressBar,load, imageView);
                getImageUtil.execute(input.getText().toString().trim());
            } else {
                Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
            }
        });
    }
}