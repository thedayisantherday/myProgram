package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.widget.wheelview.WheelAddressSelector;

public class WheelviewFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout ll_addr;
    private LinearLayout ll_postcode;
    private TextView tv_addr;
    private TextView tv_postcode;

    private PopupWindow mPopupWindow;
    private WheelAddressSelector mWheelAddressSelector;
    private View mAddrSelectorView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wheelview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        ll_addr = (LinearLayout) mActivity.findViewById(R.id.ll_addr);
        ll_addr.setOnClickListener(this);
        ll_postcode = (LinearLayout) mActivity.findViewById(R.id.ll_postcode);
        tv_addr = (TextView) mActivity.findViewById(R.id.tv_addr);
        tv_postcode = (TextView) mActivity.findViewById(R.id.tv_postcode);
    }

    /**
     * 以PopupWindow弹窗的方式显示地址选择框
     * @param parentView 地址选择弹窗依附的父布局
     */
    private void setAddrSelectorDialog(View parentView) {
        if(mAddrSelectorView == null){
            mAddrSelectorView = View.inflate(mActivity, R.layout.wheel_address_selector, null);
        }

        if(mWheelAddressSelector==null) {
            mWheelAddressSelector = new WheelAddressSelector(mActivity, mAddrSelectorView);
            mWheelAddressSelector.btn_cancel.setOnClickListener(this);
            mWheelAddressSelector.btn_confirm.setOnClickListener(this);
        }

        mPopupWindow = new PopupWindow(mAddrSelectorView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        //显示PopupWindow
        mPopupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                mPopupWindow = null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_addr:
                setAddrSelectorDialog(ll_addr);
                break;
            case R.id.btn_confirm:
                tv_addr.setText(mWheelAddressSelector.mCurrentProviceName+
                        mWheelAddressSelector.mCurrentCityName+mWheelAddressSelector.mCurrentDistrictName);
                tv_postcode.setText(mWheelAddressSelector.mCurrentZipCode);
                if(mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
            case R.id.btn_cancel:
                if(mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
        }
    }
}
