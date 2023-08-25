package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.AutoClickActivity;
import com.example.zxg.myprogram.activities.TestActivity;
import com.example.zxg.myprogram.common.ThreeTuple;
import com.example.zxg.myprogram.common.TupleUtil;
import com.example.zxg.myprogram.netapi.api.LoginApi;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.utils.NetUtils;
import com.example.zxg.myprogram.utils.SysUtils;
import com.example.zxg.myprogram.utils.TimeUtils;
import com.example.zxg.myprogram.utils.XUtilsTools;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * description ：
 * Created by zhuxiaoguang at 18:37 on 2019/8/30
 */
public class ToolUtilsFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private Button btn_net,
            btn_tuple;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_toolutils, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        btn_net = (Button) rootView.findViewById(R.id.btn_net);
        btn_net.setOnClickListener(this);

        btn_tuple = (Button) rootView.findViewById(R.id.btn_tuple);
        btn_tuple.setOnClickListener(this);

//        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_net:
                XUtilsTools.displayImage(mActivity, new ImageView(mActivity), "url",null,null);

                /*if (Tools.isFastClick()){
                    LogUtils.i(TAG, "---isFastClick");
                }

                String str = NumberUtils.formatDecimals(2016.100192, 4);
                if (Tools.isEmpty(str)){
                    LogUtils.i(TAG, "str is empty");
                }
                LogUtils.i(TAG, "upperCaseFirstOne:"+Tools.upperCaseFirstOne("my -- test1 -test1"));
                LogUtils.i(TAG, "getCharCount:"+Tools.getCharCount("my -- -test1"));
                LogUtils.i(TAG, "---my test1");*/

//                List list = null;
//                list.contains(2);
//                list.size();
//                list.add(1);
////                list.add(3);
//                if(list.contains(2))
//                    LogUtils.i(TAG, "list(0)的元素："+ list.get(0));
//                else
//                    LogUtils.i(TAG, "list(1)的元素："+ list.get(1));
//                LogUtils.i(TAG,"myprogram isAppRunning: "+SysUtils.isAppRunning(mContext,"com.example.zxg.myprogram"));

                LogUtils.i(TAG,"mockserver isAppRunning: "+ SysUtils.isAppRunning(mActivity,"com.youtitle.kuaidian"));

                LogUtils.i(TAG, "IP地址："+ NetUtils.getCurrentIpAddr());
                LogUtils.i(TAG, "WIFI IP地址："+ NetUtils.getCurrentWifiIPAddr(mActivity));
                LogUtils.i(TAG, "网络类型："+ NetUtils.getNetConnectType(mActivity));

                LogUtils.i(TAG, "欢迎语："+ TimeUtils.getWelcome());
                LoginApi loginApi= new LoginApi(mActivity) {
                    @Override
                    public void onResult(String jsonStr) {
                        LogUtils.i(TAG, "====="+jsonStr);

                    }

                    @Override
                    public void onFail() {

                    }
                };
                loginApi.getTest();
                AnimUtils.doCustomAnim(mActivity,
                        AutoClickActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_tuple:
                ThreeTuple<String, Integer, Boolean> threeTuple = TupleUtil.tuple("test1", 1, true);
                Toast.makeText(mActivity, "ThreeTuple.first = " + threeTuple.first + ", ThreeTuple.second = " + threeTuple.second + ", ThreeTuple.three = " + threeTuple.three, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_rxjava:
                handleRxJava();
                break;
            case R.id.btn_image_libs:
                handleImageLibs();
                break;
        }
    }

    private void handleRxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                    }
                }).flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return new ObservableSource<String>() {
                            @Override
                            public void subscribe(Observer<? super String> observer) {
                                observer.onNext(integer.toString());
                            }
                        };
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        List<String> list = new ArrayList<>();
        list.add("b");
        list.add("a");
        list.add("c");
        Log.d(TAG, "zxg: 1对1:[b, a, c]-->[b, a, c]");
        //1对1
//                Observable.just(list)
//                        .flatMap(
//                                new Function<List<String>, ObservableSource<?>>() {
//                                    @Override
//                                    public ObservableSource<?> apply(List<String> s) throws Exception {
////                                System.out.println("map--1----" + s);
//                                        return Observable.fromArray(s);
//                                    }
//                                })
//                        .subscribe(s -> {
//                            Log.d(TAG, "zxg: " + s);
//                        });

//                Log.d(TAG, "zxg: 1对多:[b, a, c]-->b, a, c");
//                //1对多
//                Observable.just(list)
//                        .flatMap(
//                                new Function<List<String>, ObservableSource<?>>() {
//                                    @Override
//                                    public ObservableSource<?> apply(List<String> s) throws Exception {
////                                System.out.println("map--1----" + s);
//                                        return Observable.fromIterable(s);
//                                    }
//                                })
//                        .subscribe(s -> {
//                            Log.d(TAG, "zxg: " + s);
//                        });

        //多对多
        Log.d(TAG, "zxg: 多对多:a, b, c-->[a, c]");
        Observable.just("a", "b", "c")
                .flatMap(
                        new Function<String, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(String s) throws Exception {
//                                System.out.println("map--1----" + s);
                                if (s.equalsIgnoreCase("b")) return Observable.empty();
                                return Observable.just(s);
                            }
                        })
                .subscribe(s -> {
                    Log.d(TAG, "zxg: " + s);
                });

        //多对多
        Log.d(TAG, "zxg: 多对多:a, b, c-->a,b,c,d");
        Observable.just("a", "b", "c")
                .flatMap(
                        new Function<String, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(String s) throws Exception {
//                                System.out.println("map--1----" + s);
                                if (s.equalsIgnoreCase("c")) return Observable.just("c", "d");
                                return Observable.just(s);
                            }
                        })
                .subscribe(s -> {
                    Log.d(TAG, "zxg: " + s);
                });
        Log.d(TAG, "zxg: map 一对一:[b, a, c]-->[b, a, c]");
        Observable.just(list)
                .map(new Function<List<String>, List<String>>() {
                    @Override
                    public List<String> apply(List<String> strings) throws Exception {
                        return strings;
                    }
                })
                .subscribe(s -> {
                    Log.d(TAG, "zxg: " + s);
                });

        handleFlowable();
    }

    private void handleFlowable() {
        /**
         * 步骤1：创建被观察者 =  Flowable
         */
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);
        // 需要传入背压参数BackpressureStrategy，下面会详细讲解

        /**
         * 步骤2：创建观察者 =  Subscriber
         */
        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);
                // 关于request()下面会继续详细说明
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        RxJavaPlugins.setOnObservableSubscribe(((observable, observer) ->
            new Observer() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.e("Tag", "onSubscribe");
                    observer.onSubscribe(d);
                }

                @Override
                public void onNext(Object o) {
                    Log.e("Tag", "onNext");
                    observer.onNext(o);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Tag", "onError");
                    observer.onError(e);
                }

                @Override
                public void onComplete() {
                    Log.e("Tag", "onComplete");
                    observer.onComplete();
                }
            }));

        /**
         * 步骤3：建立订阅关系
         */
        upstream
            .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(downstream);

    }

    private void handleImageLibs() {
//        Glide.with(this)
//                .load("")
//                .asBitmap()
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .centerCrop()
//                .transform()
//                .into();
    }
}
