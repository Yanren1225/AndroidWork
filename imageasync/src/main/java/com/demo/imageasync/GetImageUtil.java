package com.demo.imageasync;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-03-11 14:15
 * @website https://imyan.ren
 */
public class GetImageUtil extends AsyncTask<String, Integer, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private final ProgressBar progressBar;
    private final LinearLayout load;
    private final ImageView imageView;
    private int mCount = 0;

    public GetImageUtil(ProgressBar progressBar, LinearLayout load, ImageView imageView) {
        super();
        this.progressBar = progressBar;
        this.load = load;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setVisibility(View.GONE);
        load.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        load.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
