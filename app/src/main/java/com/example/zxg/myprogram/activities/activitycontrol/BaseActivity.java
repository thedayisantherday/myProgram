package com.example.zxg.myprogram.activities.activitycontrol;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxg.myprogram.R;

/**
 * activity基类
 * Created by zxg on 2016/9/26.
 * QQ:1092885570
 */
public abstract class BaseActivity extends FragmentActivity {

    public static String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;
    public BaseActivity mthis;
    private LinearLayout ll_base_container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mthis = this;
        //添加到activity栈中
        ActivityManager.getInstance().addActivity(this);

        setContentView(R.layout.activity_base);
        ll_base_container = (LinearLayout) findViewById(R.id.ll_base_container);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.rl_titlebar = findViewById(R.id.rl_titlebar);
        viewHolder.iv_left = (ImageView) findViewById(R.id.iv_left);
        viewHolder.tv_left = (TextView) findViewById(R.id.tv_left);
        viewHolder.tv_title = (TextView) findViewById(R.id.tv_title);
        viewHolder.tv_remark = (TextView) findViewById(R.id.tv_remark);
        viewHolder.iv_center = (ImageView) findViewById(R.id.iv_center);
        viewHolder.tv_right = (TextView) findViewById(R.id.tv_right);
        viewHolder.iv_right = (ImageView) findViewById(R.id.iv_right);

        initHead(viewHolder);
    }

    /**
     * 设置布局
     * @param view
     */
    public void setView(View view){
        if(ll_base_container != null)
            ll_base_container.addView(view);
    }

    public void setView(int resourceID){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(resourceID, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        setView(contentView);
    }

    /**
     * 初始化Activity头部
     * @param viewHolder
     */
    public void initHead(ViewHolder viewHolder) {};

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }

    public class ViewHolder {
        public View rl_titlebar;
        public ImageView iv_left, iv_center, iv_right;
        public TextView tv_left, tv_title, tv_remark, tv_right;
    }
}
