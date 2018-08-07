package com.example.zxg.myprogram.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.JsonUtils;
import com.example.zxg.myprogram.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**RecyclerView Adapter
 * Created by zxg on 2016/10/31.
 * QQ:1092885570
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private List<Data> mCommonModels = new ArrayList<Data>();
    private OnItemClickListener mItemListener; //监听器，用于RecyclerView item点击事件监听

    public RecyclerViewAdapter(/*List<CommonModel> commonModels*/) {
        Data model = new Data("1", "the first item", "{[Hello, this is the content of the first item]}");
        mCommonModels.add(model);
        model = new Data("2", "the second item", "{[Hello, this is the content of the second item]}");
        mCommonModels.add(model);
        model = new Data("3", "the third item", "{[Hello, this is the content of the third item]}");
        mCommonModels.add(model);
        model = new Data("4", "the fourth item", "{[Hello, this is the content of the fourth item]}");
        mCommonModels.add(model);
        model = new Data("5", "the fifth item", "{[Hello, this is the content of the fifth item]}");
        mCommonModels.add(model);
    }

    /**
     * 创建viewholder，用于缓存item view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    /**
     * 将数据与viewholder绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Data commonModel = mCommonModels.get(position);
        holder.tv_code.setText(commonModel.code);
        holder.tv_message.setText(commonModel.message);
        holder.tv_body.setText(JsonUtils.string2Jsonstring(commonModel.body));
    }

    @Override
    public int getItemCount() {
        return mCommonModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemListener = listener;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_code;
        public TextView tv_message;
        public TextView tv_body;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_code = (TextView) itemView.findViewById(R.id.tv_code);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            tv_body = (TextView) itemView.findViewById(R.id.tv_body);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("RecyclerView--------", "RecyclerView item clicked!"+getAdapterPosition());
                    if (mItemListener != null) {
                        mItemListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    //存放测试数据
    class Data {
        public String code;
        public String message;
        public String body;

        public Data(String code, String message, String body) {
            this.code = code;
            this.message = message;
            this.body = body;
        }
    }
}
