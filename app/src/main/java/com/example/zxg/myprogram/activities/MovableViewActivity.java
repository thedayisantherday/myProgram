package com.example.zxg.myprogram.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseSlideCloseActivity;
import com.example.zxg.myprogram.widget.MovableView;

public class MovableViewActivity extends BaseSlideCloseActivity {

    private static String TAG = MovableViewActivity.class.getSimpleName();
    private MovableView mMovableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movable_view);

        mMovableView = (MovableView) findViewById(R.id.movable_view);
        final TextView tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "height = " + tv_test.getHeight() + ", width = " + tv_test.getWidth() + ", x = " + tv_test.getX() + ", y = " + tv_test.getY());
            }
        });
    }
}
