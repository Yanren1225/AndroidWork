package ren.imyan.vr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        toVrImageFragment();
    }

    private void initToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("VR 测试");
    }

    private void toVrImageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.load_fragment, new VRPicFragment()).commit();
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("VR 图片");
    }

    private void toVrVideoFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.load_fragment, new VRVideoFragment()).commit();
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("VR 视频");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String sub = (String) Objects.requireNonNull(getSupportActionBar()).getSubtitle();
        switch (Objects.requireNonNull(sub)) {
            case "VR 图片":
                menu.findItem(R.id.video).setVisible(true);
                menu.findItem(R.id.image).setVisible(false);
                break;
            case "VR 视频":
                menu.findItem(R.id.video).setVisible(false);
                menu.findItem(R.id.image).setVisible(true);
                break;
            default:
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.video:
                toVrVideoFragment();
                break;
            case R.id.image:
                toVrImageFragment();
                break;
            default:
        }
        invalidateOptionsMenu();
        return true;
    }
}