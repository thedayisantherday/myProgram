package com.example.zxg.myprogram.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.utils.Tools;
import com.example.zxg.myprogram.widget.scalescrollview.HorizontalScaleView;
import com.example.zxg.myprogram.widget.scalescrollview.ResizeLayout;

/**
 * 刻度尺功能
 */
public class ScaleActivity extends BaseActivity implements View.OnClickListener{

    //    TextView mTvHorizontalScale;
    private EditText mETHorizontalScale;
    public HorizontalScaleView scaleScrollView;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_scale);

        scaleScrollView = (HorizontalScaleView) findViewById(R.id.horizontalScale);
        scaleScrollView.setScrollerListener(new HorizontalScaleView.ScrollerListener() {
            @Override
            public void onScroll(float scale) {
                mETHorizontalScale.setText("" + ((int)(scale+scaleScrollView.initValue/scaleScrollView.mScaleValue)*scaleScrollView.mScaleValue));
                mETHorizontalScale.setSelection(mETHorizontalScale.getText().length());
            }
        });

        scaleScrollView.initValue = 96688;//设置初始值，即当前额度

        ResizeLayout rl_layout = (ResizeLayout) findViewById(R.id.rl_scroll);
        rl_layout.setOnClickListener(this);
        rl_layout.setOnResizeListener(new ResizeLayout.OnResizeListener() {
            public void OnResize(int w, int h, int oldw, int oldh) {

                if(h > oldh){
                    scaleScrollView.isEdit = true;
                    //键盘关闭时，根据编辑的期望总额度滑动刻度尺
                    String editValue = mETHorizontalScale.getText().toString().trim();
                    if(null == editValue || "" == editValue || editValue.length() == 0){
                        Toast.makeText(getApplicationContext(),"请输入调整临额值，或移动刻度条", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //判断是否存在小数点，存在小数点则截断
                    int mDotLoc = editValue.indexOf(".");
                    if(mDotLoc > -1) {
                        editValue = editValue.substring(0, mDotLoc);
                        //mETHorizontalScale.setSelection(editValue.length());
                    }
                    int mEditValue = Integer.parseInt(editValue);
                    int min = (scaleScrollView.mMinNum +  scaleScrollView.initValue/scaleScrollView.mScaleValue)*scaleScrollView.mScaleValue;
                    int max = (scaleScrollView.mMaxNum +  scaleScrollView.initValue/scaleScrollView.mScaleValue)*scaleScrollView.mScaleValue;
                    if(!isFirst)
                        if(mEditValue>=scaleScrollView.initValue && mEditValue<=max)
                            scaleScrollView.doScrollTo((mEditValue -min)/(float)scaleScrollView.mScaleValue);
                        else if(mEditValue < min){
                            //超出最小值到最大值之间的范围时，提示
                            mETHorizontalScale.setText(""+min);
                            if(0 == scaleScrollView.initValue % scaleScrollView.mScaleValue)
                                scaleScrollView.doScrollTo(0);
                            else
                                scaleScrollView.doScrollTo(2);
                        }else{
                            //超出最小值到最大值之间的范围时，提示
                            mETHorizontalScale.setText(""+max);
                            scaleScrollView.doScrollTo(scaleScrollView.mMaxNum - scaleScrollView.mMinNum);
                        }
                    else
                        isFirst = false;
                }
            }
        });

        mETHorizontalScale = (EditText) findViewById(R.id.et_horizontalScaleValue);
        mETHorizontalScale.setText(""+scaleScrollView.initValue);
        Tools.setEditTextDecimal(mETHorizontalScale, 2);

        initViews();
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setVisibility(View.VISIBLE);
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleActivity.this.finish();
            }
        });
        viewHolder.tv_title.setVisibility(View.VISIBLE);
        viewHolder.tv_title.setText("ScaleActivity");
    }

    private void initViews(){
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        Button sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.add:
                System.out.println("onclick");
                scaleScrollView.doScrollTo(2);
                break;
            case R.id.sub:
                scaleScrollView.doScrollTo(7);
                break;
            case R.id.rl_scroll:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive()){
                    imm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
                }
                break;
            default:
                break;
        }
    }
}
