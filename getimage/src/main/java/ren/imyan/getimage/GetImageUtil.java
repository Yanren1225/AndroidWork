package ren.imyan.getimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author EndureBlaze/炎忍 https://github.com.EndureBlaze
 * @data 2021-03-04 14:47
 * @website https://imyan.ren
 */
public class GetImageUtil {

    private static Bitmap bitmap = null;

    public static Bitmap getImage(String url) {
        try {
            URL _url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);

            InputStream input = connection.getInputStream();
            byte[] data = readInputStream(input);
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static byte[] readInputStream(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
