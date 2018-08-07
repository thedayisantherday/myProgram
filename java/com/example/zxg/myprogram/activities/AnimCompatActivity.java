package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.utils.LogUtils;

public class AnimCompatActivity extends BaseActivity {

    public static String TAG = AnimCompatActivity.class.getSimpleName();

    private Context mContext;
    private EditText et_code;
    private EditText et_message;
    private EditText et_body;
    private Button bt_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_anim_compat);
        mContext = this;
        initData();
        initView();
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimCompatActivity.this.finish();
            }
        });
    }

    private void initData(){

    }

    private void initView(){
        et_code = (EditText) findViewById(R.id.et_code);
        et_message = (EditText) findViewById(R.id.et_message);
        et_body = (EditText) findViewById(R.id.et_body);
        bt_sure = (Button) findViewById(R.id.bt_sure);
    }
}
