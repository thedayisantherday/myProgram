package com.example.zxg.myprogram.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 实现对listView的循环滚动
 * @author liangjunying email:junying.hao@163.com blog:http://blog.csdn.net/laibaigan
 *
 */
public class MainActivity extends Activity implements OnScrollListener {

    private ListView listView;
    private List<String> list;
    private ListAdapter adapter;
//	private LinearLayout linear;
//
//	private int stopPosition;
//	private int firstViewHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        listView = (ListView) findViewById(R.id.listView1);
        list = getList();
        adapter = new ListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setSelection(list.size());
    }

    /**
     * 获取数据
     * @return
     */
    public List<String> getList(){
        List<String> list =  new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 设置滚动监听，当滚动到第二个时，跳到地list.size()+2个，滚动到倒数第二个时，跳到中间第二个，setSelection时，
     * 由于listView滚动并未停止，所以setSelection后会继续滚动，不会出现突然停止的现象
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
//		stopPosition = firstVisibleItem;
//		linear = (LinearLayout) view.getChildAt(0);
//
//		if(linear != null){
//			firstViewHeight = linear.getBottom();
//		}

        /**到顶部添加数据*/
        if (firstVisibleItem <= 2) {
            listView.setSelection(list.size() + 2);
        } else if (firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {//到底部添加数据
            listView.setSelection(firstVisibleItem - list.size());
        }

    }

}
