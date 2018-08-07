package com.example.zxg.myprogram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.widget.DoubleSurfaceViewFlicker;

import java.io.InputStream;
import java.net.URL;

public class SurfaceViewActivity extends AppCompatActivity {

    private DoubleSurfaceViewFlicker surfaceViewFlicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        try {
            URL url = new URL("www.baidu.com");
            InputStream inputStream = url.openStream();

        } catch (Exception e) {
            e.printStackTrace();
        }

        surfaceViewFlicker = (DoubleSurfaceViewFlicker) findViewById(R.id.test_surfaceview);
    }
}
