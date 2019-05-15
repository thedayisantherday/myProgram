package com.example.zxg.myprogram.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class BaseRecyclerviewAdapter extends RecyclerView.Adapter<BaseVH> {
    public final List<BaseFloorEntity> list;
    private final boolean isAppContext;
    public LayoutInflater inflater;

    public BaseRecyclerviewAdapter(List<BaseFloorEntity> list) {
        this(list, false);
    }

    public BaseRecyclerviewAdapter(List<BaseFloorEntity> list, boolean isAppContext) {
        this.list = list;
        this.isAppContext = isAppContext;
    }

    public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.inflater == null) {
            this.inflater = LayoutInflater.from(this.isAppContext ? parent.getContext().getApplicationContext() : parent.getContext());
        }

        return null;
    }

    public void onBindViewHolder(BaseVH holder, int position) {
        holder.bindVH((BaseFloorEntity)this.list.get(position));
    }

    public int getItemViewType(int position) {
        return ((BaseFloorEntity)this.list.get(position)).getFloorType();
    }

    public int getItemCount() {
        return this.list.size();
    }
}
