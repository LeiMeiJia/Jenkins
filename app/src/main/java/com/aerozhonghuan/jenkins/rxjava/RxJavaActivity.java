package com.aerozhonghuan.jenkins.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.aerozhonghuan.jenkins.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava基本原理
 * <p/>
 * onStart===main
 * doOnSubscribe====RxNewThreadScheduler-2
 * OnSubscribe====RxIoScheduler-4
 * filter===RxIoScheduler-3
 * doOnNext=====RxIoScheduler-2
 * onNext====RxNewThreadScheduler-1
 */
public class RxJavaActivity extends AppCompatActivity {

    Subscriber<Drawable> observer;
    Observable<Drawable> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        String[] names = {"test", "test1", "test2"};

        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        Log.d("test", name);
                    }
                });


        final int drawableRes = R.mipmap.ic_launcher;
        final ImageView imageView = (ImageView) findViewById(R.id.iv);

        // 创建观察者对象，复写回调方法，决定事件触发的时候将有怎样的行为。
        observer = new Subscriber<Drawable>() {
            // 手动调用
            @Override
            public void onCompleted() {
                System.out.println("successful");
            }

            // 系统调用
            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

            // 手动调用
            @Override
            public void onNext(Drawable drawable) {
                System.out.println("onNext=====" + Thread.currentThread().getName());
                imageView.setImageDrawable(drawable);
//                int i = 9 / 0;
            }

            // 系统调用
            @Override
            public void onStart() {
                super.onStart();
                System.out.println("onStart===" + Thread.currentThread().getName());
            }
        };

        /**
         * 创建被观察者，它决定什么时候触发事件以及触发怎样的事件。
         * RxJava使用create()方法来创建一个Observable，并为它定义事件触发规则：
         * OnSubscribe会被存储在返回的 Observable对象中，它的作用相当于一个计划表，
         * 当Observable被订阅的时候，OnSubscribe的call()方法会自动被调用，事件序列就会依照设定依次触发
         */
        observable = Observable.create(new Observable.OnSubscribe<Drawable>() {

            // 当事件被触发的时候，call方法被调用
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                System.out.println("OnSubscribe====" + Thread.currentThread().getName());
                Drawable drawable = getResources().getDrawable(drawableRes);
                // 此时调用的为父类onStart()方法，内容为空
//                subscriber.onStart();
                subscriber.onNext(drawable);
                subscriber.onCompleted();
//                subscriber.unsubscribe();
            }
        });

        /**
         * 简洁源码
         *
         * public Subscription subscribe(Subscriber subscriber) {
         *     // subscribe()中subscriber的对象为用户创建，在底层先调用该对象的onStart()方法，此时为系统调用。
         *     subscriber.onStart();
         *     // subscriber对象被系统转换为SafeSubscriber
         *     if (!(subscriber instanceof SafeSubscriber)) {
         *        subscriber = new SafeSubscriber<T>(subscriber);
         *     }
         *     // call方法中如果再次调用onStart()方法，此时调用的是SafeSubscriber对象的onStart()方法，
         *     // 由于该对象没有onStart()方法，所以调用父类的onStart()方法，显示为空。
         *     hook.onSubscribeStart(observable, observable.onSubscribe).call(subscriber);
         *     return subscriber;
         * }
         */
        // 建立订阅关系
        // 当 Observable 被订阅的时候，OnSubscribe的 call() 方法会自动被调用，事件序列就会依照设定依次触发
        observable
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnSubscribe====" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
                .filter(new Func1<Drawable, Boolean>() {
                    @Override
                    public Boolean call(Drawable drawable) {
                        System.out.println("filter===" + Thread.currentThread().getName());
                        return true;
                    }
                })
//                .observeOn(Schedulers.io())
                .doOnNext(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        System.out.println("doOnNext=====" + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.newThread()) // 指定 Subscriber 的回调发生在主线程
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }
}
