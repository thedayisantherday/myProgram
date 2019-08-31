package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.AndroidH5Activity;
import com.example.zxg.myprogram.activities.LoopRecyclerviewActivity;
import com.example.zxg.myprogram.activities.MovableViewActivity;
import com.example.zxg.myprogram.activities.MovingWithFingerActivity;
import com.example.zxg.myprogram.activities.ScaleActivity;
import com.example.zxg.myprogram.activities.SurfaceViewActivity;
import com.example.zxg.myprogram.activities.TestActivity;
import com.example.zxg.myprogram.activities.TouchEventActivity;
import com.example.zxg.myprogram.activities.VerticalViewpagerActivity;
import com.example.zxg.myprogram.activities.ViewpagerActivity;
import com.example.zxg.myprogram.activities.WebviewActivity;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.widget.dialogs.CustomDialog;
import com.example.zxg.myprogram.widget.dialogs.PopupFromBottomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * description ：
 * Created by zhuxiaoguang at 18:37 on 2019/8/30
 */
public class CustomViewFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private Button btn_scale,
            btn_viewpager,
            btn_test,
            btn_custom_dialog,
            btn_popup_dialog,
            btn_moving_with_finger,
            btn_vertical_viewpager,
            btn_doubleSurfaceViewFlicker,
            btn_movable_view,
            btn_loop_recyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customview, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        btn_scale = (Button) rootView.findViewById(R.id.btn_scale);
        btn_scale.setOnClickListener(this);

        btn_viewpager = (Button) rootView.findViewById(R.id.btn_viewpager);
        btn_viewpager.setOnClickListener(this);

        btn_test = (Button) rootView.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_custom_dialog = (Button) rootView.findViewById(R.id.btn_custom_dialog);
        btn_custom_dialog.setOnClickListener(this);

        btn_popup_dialog = (Button) rootView.findViewById(R.id.btn_popup_dialog);
        btn_popup_dialog.setOnClickListener(this);

        btn_moving_with_finger = (Button) rootView.findViewById(R.id.btn_moving_with_finger);
        btn_moving_with_finger.setOnClickListener(this);

        btn_vertical_viewpager = (Button)rootView.findViewById(R.id.btn_vertical_viewpager);
        btn_vertical_viewpager.setOnClickListener(this);

        btn_doubleSurfaceViewFlicker = (Button)rootView.findViewById(R.id.btn_doubleSurfaceViewFlicker);
        btn_doubleSurfaceViewFlicker.setOnClickListener(this);

        btn_movable_view = (Button) rootView.findViewById(R.id.btn_movable_view);
        btn_movable_view.setOnClickListener(this);

        btn_loop_recyclerview = (Button) rootView.findViewById(R.id.btn_loop_recyclerview);
        btn_loop_recyclerview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scale:
                AnimUtils.doCustomAnim(mActivity,
                        ScaleActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_viewpager:
                AnimUtils.doScaleUpAnim(mActivity, ViewpagerActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_test:
                /*AnimUtils.doScaleUpAnim((MainActivity)mContext, com.example.zxg.myprogram.test1.MainActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);*/
                AnimUtils.doCustomAnim(mActivity,
                        TestActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                /*AnimUtils.doScaleUpAnim((MainActivity)mContext, TestActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);*/
                break;
            case R.id.btn_custom_dialog:
                CustomDialog dialog = new CustomDialog(mActivity, "test1", "do test1", "取消", "确定");
                dialog.showDialog();
                dialog.setClickCallBack(new CustomDialog.ClickCallBack() {
                    @Override
                    public void onNegative() {
                        LogUtils.i(TAG, "Negative button is clicked!");
                    }

                    @Override
                    public void onPositive() {
                        LogUtils.i(TAG, "Positive button is clicked!");
                    }
                });
                break;
            case R.id.btn_popup_dialog:
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 2; i++) {

                    list.add("sure");
                    list.add("cancel");
                }
                final PopupFromBottomDialog popupFromBottomDialog = new PopupFromBottomDialog(mActivity, list);
                popupFromBottomDialog.showDialog();
                popupFromBottomDialog.setmOnItemListener(new PopupFromBottomDialog.OnItemListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case 0:
                            case 1:
                                popupFromBottomDialog.dismiss();
                                break;

                        }
                    }
                });
                break;
            case R.id.btn_moving_with_finger:
                AnimUtils.doCustomAnim(mActivity,
                        MovingWithFingerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_vertical_viewpager:
                AnimUtils.doCustomAnim(mActivity,
                        VerticalViewpagerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_doubleSurfaceViewFlicker:
                AnimUtils.doCustomAnim(mActivity,
                        SurfaceViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_movable_view:
                AnimUtils.doCustomAnim(mActivity,
                        MovableViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_loop_recyclerview:
                AnimUtils.doCustomAnim(mActivity,
                        LoopRecyclerviewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
    }
}
