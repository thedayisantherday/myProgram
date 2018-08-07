package com.example.zxg.myprogram.widget.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.adapter.PopupDialogAdapter;

import java.util.List;

/**
 * Created by zxg on 2017/1/9.
 * QQ:1092885570
 */

public class PopupFromBottomDialog {

    private static String TAG = PopupFromBottomDialog.class.getSimpleName();

    private AlertDialog mAlertDialog;
    private Context mContext;
    private List<String> mItems;
    private OnItemListener mOnItemListener;

    private TextView tv_cancel;

    public PopupFromBottomDialog(Context context, List<String> items) {
        mContext = context;
        mItems = items;
    }

    /**
     * 显示对话框
     */
    public void showDialog(){
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.dialog).create();
        //点击对话框外部对话框不消失
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();

        Window window = mAlertDialog.getWindow();
        window.setContentView(R.layout.layout_dialog_popupfrombottom);

        ListView lv_items = (ListView) window.findViewById(R.id.lv_items);
        lv_items.setAdapter(new PopupDialogAdapter(mContext, mItems));
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnItemListener.onItemClick(position);
            }
        });

        tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.BOTTOM);
    }

    public void dismiss(){
        mAlertDialog.dismiss();
    }

    /**
     * 设置点击对话框外部对话框是否消失
     * @param isCancel
     */
    public void setCanceledOnTouchOutside(boolean isCancel){
        if (mAlertDialog != null){
            mAlertDialog.setCanceledOnTouchOutside(isCancel);
        }
    }

    /**
     * 设置是否显示取消按钮
     * @param isVisible
     */
    public void setCancelVisible(boolean isVisible){
        if (tv_cancel != null){
            if (isVisible) {
                tv_cancel.setVisibility(View.VISIBLE);
            }else {
                tv_cancel.setVisibility(View.GONE);
            }
        }
    }

    public void setmOnItemListener(OnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    public interface OnItemListener{
        public void onItemClick(int position);
    }
}
