package com.example.zxg.myprogram.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.widget.ImageTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

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
        imageTextView.setBackgroundColor(Color.parseColor("#ff00ff"));
        imageTextView.setTextSize(30);
        imageTextView.setTextColor(Color.RED);
        imageTextView.setTextviewLines(0, 0, 1, 3);
        imageTextView.setTextViewLineSpacing(5, 1.0f);

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
