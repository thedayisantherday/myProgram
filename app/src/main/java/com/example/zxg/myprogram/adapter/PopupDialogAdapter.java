package com.example.zxg.myprogram.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zxg.myprogram.R;

import java.util.List;

/**
 * Created by zxg on 17/1/12.
 */

public class PopupDialogAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mItemsList;

    public  PopupDialogAdapter(Context context, List<String> itemsList) {
        mContext = context;
        mItemsList = itemsList;
    }

    @Override
    public int getCount() {
        return mItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.layout_dialog_item_popupfrombottom,null);
            viewHolder.tv_item_content = (TextView) convertView.findViewById(R.id.tv_item_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_content.setText(getItem(position).toString());
        return convertView;
    }

    class ViewHolder{
        TextView tv_item_content;
    }
}
