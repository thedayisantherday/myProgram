package com.example.zxg.myprogram.view;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseVH extends RecyclerView.ViewHolder {
    public BaseVH(View itemView) {
        super(itemView);
    }

    public abstract void bindVH(BaseFloorEntity entity);

    public final <T extends View> T find(@IdRes int id) {
        return this.itemView.findViewById(id);
    }
}