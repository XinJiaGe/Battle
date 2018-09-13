package com.jiage.battle.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：李忻佳
 * 时间：2018/5/3/003.
 * 说明：异步，更新UI
 */

public abstract class RxAsynchronous<T> {
    public RxAsynchronous(){
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(onSubscribe(e));
                e.onComplete();
            }
        }).subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                onSubscribes(d);
            }

            @Override
            public void onNext(T t) {
                onResult(t);
            }

            @Override
            public void onError(Throwable e) {
                onErrors(e);
            }

            @Override
            public void onComplete() {
                onCompletes();
            }
        });
    }

    public abstract T onSubscribe(ObservableEmitter<T> observable);
    public void onResult(T result){};
    public void onSubscribes(Disposable disposable){}
    public void onErrors(Throwable e) {}
    public void onCompletes() {}
}
