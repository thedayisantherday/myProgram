package com.example.zxg.myprogram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.widget.CustomView;
import com.example.zxg.myprogram.widget.CustomViewGroup;
import com.example.zxg.myprogram.widget.DoubleSurfaceViewFlicker;

import java.io.InputStream;
import java.net.URL;

public class TouchEventActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static String TAG = TouchEventActivity.class.getSimpleName();
    private CustomViewGroup mCustomViewGroup;
    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        mCustomViewGroup = (CustomViewGroup) findViewById(R.id.test_custom_viewgroup);
        mCustomView = (CustomView) findViewById(R.id.test_custom_view);

        mCustomViewGroup.setOnClickListener(this);
        mCustomView.setOnClickListener(this);

        mCustomViewGroup.setOnTouchListener(this);
        mCustomView.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_custom_viewgroup:
                Log.i("CustomViewGroup", "onClick===> CustomViewGroup");
                break;
            case R.id.test_custom_view:
                Log.i("CustomView", "onClick===> CustomView");
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.test_custom_viewgroup:
                Log.i("CustomViewGroup", "onTouch===> CustomViewGroup");
                return true;
            case R.id.test_custom_view:
                Log.i("CustomView", "onTouch===> CustomView");
                break;

        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent===> action : " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent===> action : " + event.getAction());
        return super.onTouchEvent(event);
    }
}
