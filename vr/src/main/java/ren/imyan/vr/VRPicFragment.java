package ren.imyan.vr;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;

public class VRPicFragment extends Fragment {

    private VrPanoramaView vrPanoramaView;
    private AsyncTask<Void, Void, Bitmap> asyncTask;

    public VRPicFragment() {
        super(R.layout.fragment_vr_pic);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vrPanoramaView = view.findViewById(R.id.vr_image);
        vrPanoramaView.setEventListener(new VrPanoramaEventListener());
        asyncTask = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    InputStream inputStream = getContext().getAssets().open("andes.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    VrPanoramaView.Options options = new VrPanoramaView.Options();
                    options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
                    vrPanoramaView.loadImageFromBitmap(bitmap, options);
                }
                super.onPostExecute(bitmap);
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    public void onPause() {
        super.onPause();
        vrPanoramaView.pauseRendering();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vrPanoramaView.shutdown();
        if (asyncTask != null) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }
}
