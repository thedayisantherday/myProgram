package com.example.zxg.myprogram.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.JsonUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.view.BaseFloorEntity;
import com.example.zxg.myprogram.view.BaseRecyclerviewAdapter;
import com.example.zxg.myprogram.view.BaseVH;

import java.util.ArrayList;
import java.util.List;

/**RecyclerView Adapter
 * Created by zxg on 2016/10/31.
 * QQ:1092885570
 */

public class RecyclerViewAdapter extends BaseRecyclerviewAdapter{

    private View.OnClickListener mItemClickListener; //监听器，用于RecyclerView item点击事件监听

    public RecyclerViewAdapter(List<BaseFloorEntity> floorEntityList, View.OnClickListener onItemClickListener) {
        super(floorEntityList);
        this.mItemClickListener = onItemClickListener;
    }

    /**
     * 创建viewholder，用于缓存item view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);
        switch (viewType) {
            case TestFloorData.FLOOR_TYPE_1:
                return new TestVH1(inflater.inflate(R.layout.recyclerview_item, parent, false), mItemClickListener);
            case TestFloorData.FLOOR_TYPE_2:
                return new TestVH2(inflater.inflate(R.layout.recyclerview_item, parent, false), mItemClickListener);
            default:
                return null;
        }
    }

    /**
     * 将数据与viewholder绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseVH holder, int position) {
        if (floorEntityList != null && position < floorEntityList.size()) {
            holder.bindVH(floorEntityList.get(position));
        }
    }

    //存放测试数据
    public static class TestFloorData extends BaseFloorEntity{
        public static final int FLOOR_TYPE_1 = 1;
        public static final int FLOOR_TYPE_2 = 2;
        public int floorType;
        public String code;
        public String message;
        public String body;

        public TestFloorData(int floorType, String code, String message, String body) {
            this.floorType = floorType;
            this.code = code;
            this.message = message;
            this.body = body;
        }

        @Override
        public int getFloorType() {
            return floorType;
        }
    }

    class TestVH1 extends BaseVH {

        public View.OnClickListener itemClickListener;

        public TestVH1(View itemView, View.OnClickListener onItemClickListener) {
            super(itemView);
            itemClickListener = onItemClickListener;
        }

        @Override
        public void bindVH(BaseFloorEntity entity) {
            if (entity instanceof TestFloorData) {
                itemView.setOnClickListener(itemClickListener);
                itemView.findViewById(R.id.ll_content).setBackgroundColor(Color.parseColor("#FFFAF0"));
            }
        }
    }

    class TestVH2 extends BaseVH {

        public View.OnClickListener itemClickListener;

        public TestVH2(View itemView, View.OnClickListener onItemClickListener) {
            super(itemView);
            itemClickListener = onItemClickListener;
        }

        @Override
        public void bindVH(BaseFloorEntity entity) {
            if (entity instanceof TestFloorData) {
                itemView.setOnClickListener(itemClickListener);
                itemView.findViewById(R.id.ll_content).setBackgroundColor(Color.parseColor("#F0FFF0"));
            }
        }
    }
}
