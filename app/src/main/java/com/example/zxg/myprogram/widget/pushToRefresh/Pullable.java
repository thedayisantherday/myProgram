package com.example.zxg.myprogram.widget.pushToRefresh;

/**
 * Created by zxg on 16/11/29.
 */
public interface Pullable {

    /**
     * 是否可以下拉
     *
     * @return true可以下拉，否则不能下拉
     */
    boolean canPullDown();

    /**
     * 是否可以上拉
     *
     * @return true可以上拉，否则不能上拉
     */
    boolean canPullUp();
}
