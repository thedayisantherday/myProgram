package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.DPIUtil;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.widget.ImageTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TestActivity extends Activity {

    private TextView tv_test;
    private ImageTextView imageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        imageTextView = (ImageTextView) findViewById(R.id.imageTextView);
        imageTextView.setTextSpace(20);
        imageTextView.setTextSize(DPIUtil.dip2px(TestActivity.this, 15));
        imageTextView.setText("1随着天气降温，我们的本能又在驱使着我们去不断给自己补充热量，这样身体虽然变暖了，但也会让我们长胖一圈。很多人会选择慢跑、健走等方式来健身，但是这样的运动方式由于缺少竞争性，很容易让人产生运动枯燥感。");
        imageTextView.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
        imageTextView.setBackgroundColor(Color.parseColor("#ff00ff"));
        imageTextView.setTextColor(Color.RED);

//        Glide.with(this).load("").downloadOnly()
        /*Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        }).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return null;
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });*/
    }

    private void getLineWidth() {
        Layout layout = tv_test.getLayout();
        String text = tv_test.getText().toString();
        int start = 0;
        int end;
        for (int i = 0; i < tv_test.getLineCount(); i++) {
            end = layout.getLineEnd(i);

            String line = text.substring(start, end); //指定行的内容
            start = end;
            float width = layout.getLineWidth(i); //指定行的宽度

            Log.e("TestActivity", line + "," + width);
        }
    }

    public static void test1() {
        Map<String, Object> params = new HashMap<>();
        params.put("from", 1);
        params.put("tagId", "tagId");
        params.put("isPost", true);

        for(String key : params.keySet()) {
            LogUtils.i("test1", key + ": " + params.get(key));
        }



        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        LogUtils.i("String test1", "" + (a == c));
        LogUtils.i("String test2", "" + (a == e));
        LogUtils.i("String test3", "" + (("String" + 2) == a));
        LogUtils.i("String test4", "" + ("hello2" == a));
        LogUtils.i("String test5", "" + ((1+2) == 3));
    }

    public void test2() {
        //临界资源
        Service service = new Service();

        //创建并启动线程A
        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();

        //创建并启动线程B
        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();
    }

    //资源类
    class Service {
        public void print(String stringParam) {
            try {
                synchronized (stringParam) {
                    System.out.println(stringParam.hashCode());
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //线程A
    class ThreadA extends Thread {
        private Service service;

        public ThreadA(Service service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.print("AA");
        }
    }

    //线程B
    class ThreadB extends Thread {
        private Service service;

        public ThreadB(Service service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.print("AA");
        }
    }

    private void test3() {
        TodoList todoList = new TodoList();
        todoList.remove();
    }

    class TodoList extends PriorityQueue<TodoItem> {

    }

    class TodoItem implements Comparable<TodoItem> {
        @Override
        public int compareTo(@NonNull TodoItem another) {
            return 0;
        }
    }

}
