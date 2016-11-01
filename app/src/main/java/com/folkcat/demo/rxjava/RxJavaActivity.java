package com.folkcat.demo.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.folkcat.demo.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Tamas on 2016/9/9.
 */
public class RxJavaActivity extends Activity implements View.OnClickListener{
    private static final String TAG="RxJavaActivity";
    private Button mBtnType1;
    private Button mBtnType2;
    private Button mBtnType3;
    private Button mBtnType4;

    Observable mIntegerObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber subscriber) {
            Log.i(TAG,"call() is called, in thread:"+Thread.currentThread().toString());
            subscriber.onNext(800);

            /*
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
            subscriber.onCompleted();
            */
        }
    }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    Subscriber mIntegerSubscriber = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
            Log.i(TAG,"Complete! ");
        }
        @Override
        public void onError(Throwable e) {
            Log.i(TAG,"onError");
        }
        @Override
        public void onNext(Integer value) {
            Log.i(TAG,"onNext: " + value+" in thread:"+Thread.currentThread().toString());
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        initLayout();


    }
    private void initLayout(){
        mBtnType1=(Button)findViewById(R.id.btn_rx_type1);
        mBtnType1.setOnClickListener(this);
        mBtnType2=(Button)findViewById(R.id.btn_rx_type2);
        mBtnType2.setOnClickListener(this);
        mBtnType3=(Button)findViewById(R.id.btn_rx_type3);
        mBtnType3.setOnClickListener(this);
        mBtnType4=(Button)findViewById(R.id.btn_rx_type4);
        mBtnType4.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_rx_type1:
                Log.i(TAG,"JustButton is clicked  主线程:"+Thread.currentThread().toString());


                mIntegerObservable.just(8);//这句没有效果
                mIntegerObservable.subscribe(mIntegerSubscriber);
                mIntegerObservable.just(9);//这句也没有效果
                break;
            case R.id.btn_rx_type2:
                testRxJavaAnomousType();
                break;
            case R.id.btn_rx_type3:
                testRxWithFilter();
                break;
            case R.id.btn_rx_type4:
                testRxWithFilterAndMap();
                break;
            default:
                break;
        }
    }
    private void testRxJavaAnomousType(){
        Observable.just(7,8 ,9).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"Complete!");
            }
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onNext(Integer value) {
                Log.i(TAG,"onNext: " + value);
            }
        });
    }
    //发出奇数
    private void testRxWithFilter(){
        Observable.just(11, 12, 13, 14, 15, 16) // add more numbers11 13 15
                .filter(new Func1<Integer,Boolean>() {
                    @Override
                    public Boolean call(Integer value) {
                        return value % 2 == 1;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG,"Complete!");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.i(TAG,"onNext: " + value);
                    }
                });
    }
    //发出的这些奇数的平方根
    private void testRxWithFilterAndMap(){
        Observable.just(1, 2, 3, 4, 5, 6) // add more numbers
                .filter(new Func1<Integer,Boolean>() {
                    @Override
                    public Boolean call(Integer value) {
                        return value % 2 == 1;
                    }
                })
                .map(new Func1<Integer,Double>() {
                    @Override
                    public Double call(Integer value) {
                        return Math.sqrt(value);
                    }
                })
                .subscribe(new Subscriber<Double>() { // notice Subscriber type changed to
                    @Override
                    public void onCompleted() {
                        Log.i(TAG,"Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Double value) {
                        Log.i(TAG,"onNext: " + value);
                    }
                });
    }

    private void testRxJavaCustomType(){
        Observable.just(7,8 ,9).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"Complete!");
            }
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onNext(Integer value) {
                Log.i(TAG,"onNext: " + value);
            }
        });
    }
}
