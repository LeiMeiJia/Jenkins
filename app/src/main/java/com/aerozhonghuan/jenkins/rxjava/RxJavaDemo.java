package com.aerozhonghuan.jenkins.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RxJava 提供了对事件序列进行变换的支持，这是它的核心功能之一。
 * 所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。
 * <p/>
 * Created by LiuK on 2016/6/3
 */
public class RxJavaDemo {

    // 创建学生对象
    Student[] students = {new Student("test1"), new Student("test2")};

    /**
     * map(): 事件对象的直接变换，具体功能上面已经介绍过。它是 RxJava 最常用的变换。
     * 一对一的转换
     */
    public void MapDemo() {
        // 事件变换，将事件序列存在Observable中， map是一对一的转化
        Observable<String> stringObservable = Observable.from(students).map(new Func1<Student, String>() { //  Func1 包装的是有返回值的方法。
            @Override
            public String call(Student student) {
                return student.getName(); // 参数类型是Student对象，返回类型String。
            }
        });

        // Observer对象
        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String name) {
                // 打印每个学生的姓名
                System.out.println(name);
            }
        };

        // 订阅事件
        stringObservable.subscribe(stringSubscriber);
    }

    /**
     * 使用 flatMap()进行一对多转换
     * <p/>
     * flatMap()和map()有一个相同点：它也是把传入的参数转化之后返回另一个对象。但需要注意，和map()不同的是，
     * flatMap()中返回的是个Observable对象，并且这个Observable对象并不是被直接发送到了Subscriber的回调方法中。
     * <p/>
     * flatMap()的原理是这样的：
     * 1. 使用传入的事件对象创建一个 Observable 对象；
     * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
     * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将
     * 这些事件统一交给 Subscriber 的回调方法。
     * 这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将
     * 初始的对象『铺平』之后通过统一路径分发了下去。而这个『铺平』就是 flatMap() 所谓的 flat。
     */
    public void flatMapDemo() {

        Observable<String> observable = Observable.from(students).flatMap(new Func1<Student, Observable<String>>() {
            @Override
            public Observable<String> call(Student student) {
                ArrayList<String> arrayList = student.getArrayList();
                return Observable.from(arrayList);
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String course) {
                System.out.println(course);
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * RxJava的转换使用
     * 1、使用just()、from()创建OnSubscribe对象
     * 2、使用map()、flatMap()进行转换，底层调用的是lift()方法，返回一个新的Observable，同时创建一个新的OnSubscribe
     * 3、lift()方法底层会创建一个新的OnSubscribe对象和新的Observable对象
     * <p>
     * public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
     * cover for generics insanity
     * }
     * public final <R> Observable<R> map(Func1<? super T, ? extends R> func) {
     * return lift(new OperatorMap<T, R>(func));
     * }
     * public final <R> Observable<R> lift(final Operator<? extends R, ? super T> operator) {
     * return new Observable<R>(new OnSubscribeLift<T, R>(onSubscribe, operator));
     * }
     * <p>
     * a、接口Operator继承Func1，泛型为Subscribe类型
     * b、新 OnSubscribe的call()方法中的onSubscribe，就是指的原始Observable中的原始 OnSubscribe，在这个call()方法里
     * 新 OnSubscribe利用operator.call(subscriber) 生成了一个新的 Subscriber，（Operator就是在这里，
     * 通过自己的call()方法将新 Subscriber 和原始 Subscriber 进行关联，并插入自己的『变换』代码以实现变换），
     * 然后利用这个新Subscriber向原始 Observable 进行订阅。这样就实现了 lift() 过程，有点像一种代理机制，
     * 通过事件拦截和处理实现事件序列的变换
     * c、调用subscribe()方法时使用的是新的Observable，对应使用新的OnSubscribe。
     * 底层调用的是newOnSubscribe的call()方法，在这里方法里newSubscriber和oldSubscriber通过Operator转换。
     * 然后oldOnSubscribe对象调用call()方法，传递的参数为newSubscriber，此时新Subscriber向原始Observable 产生订阅关系。
     * <p>
     * 4、在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，这个新的 Observable 会像一个代理一样，
     * 负责接收原始的 Observable 发出的事件，并在处理后发送给 Subscriber。
     * <p>
     * <p>
     * just(T...): 将传入的参数依次发送出来。
     * from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     */
    private void demo() {
        // just(T...): 将传入的参数依次发送出来。等价于 create(OnSubscribe)
        Observable<String> just1 = Observable.just("test", "test1", "test2");
        just1.subscribe(new Action1<String>() {
            @Override
            public void call(String s) { //等价于Subscribe的OnNext方法，并不是OnSubscribe中的call()方法
                Log.d("just", s);
            }
        });

        // 创建Observable，等价于create(OnSubscribe)，此时创建事件触发规则， 存储事件序列
        Observable<String> just2 = Observable.just("images/logo.png");// 输入类型 String

        // 变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。
        Observable<Bitmap> bitmapObservable = just2.map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) { // 参数类型是String，返回类型是Bitmap
                return BitmapFactory.decodeFile(s); //  Func1 包装的是有返回值的方法。
            }
        });
        // 参数类型是Bitmap
        bitmapObservable.subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) { // 等价于Subscribe的OnNext方法，并不是OnSubscribe中的call()方法
            }
        });

        //from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        String[] words = {"hello", "rxjava"};
        Observable<String> from1 = Observable.from(words);
        from1.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("from", s);
            }
        });

        // 打印每个学生的课程，一对多
        Observable.from(students).subscribe(new Subscriber<Student>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(Student student) {
                ArrayList<String> arrayList = student.getArrayList();
                for (String course : arrayList) {
                    System.out.println(course);
                }
            }
        });
    }

}
