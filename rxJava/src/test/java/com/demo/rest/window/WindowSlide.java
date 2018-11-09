package com.demo.rest.window;


import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WindowSlide {
    private static final Logger LOG = LoggerFactory.getLogger(WindowSlide.class);
    /**
     * 两个数字相加，scan用
     */
    public static final BiFunction<Integer, Integer, Integer> SCAN_PUBLIC_SUM = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer integer, Integer integer2) throws Exception {
            LOG.info("SCAN [{}] call ...... {}   {}",Thread.currentThread().getName(), integer, integer2);
            return integer + integer2;
        }
    };

    /**
     * 两个数字相加，reduce用
     */
    public static final BiFunction<Integer, Integer, Integer> REDUCE_PUBLIC_SUM = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer integer, Integer integer2) throws Exception {
            LOG.info("REDUCE [{}] call ...... {}   {}",Thread.currentThread().getName(), integer, integer2);
            return integer + integer2;
        }
    };

    public static final Function<Observable<Integer>, Observable<Integer>> WINDOW_SUM = new Function<Observable<Integer>, Observable<Integer>>() {
        @Override
        public Observable<Integer> apply(Observable<Integer> integerObservable) throws Exception {
            //跳过第一个数据，因为给了scan一个默认值0，这个值需要跳过，如果不设置就不需要跳过
            //integerObservable.scan(0, PUBLIC_SUM).skip(1)
            //scan对发射的数据进行处理，发送每次的处理结果
            return integerObservable.scan(SCAN_PUBLIC_SUM);
        }
    };

    public static final Function<Observable<Integer>, Observable<Integer>> INNER_BUCKET_SUM = new Function<Observable<Integer>, Observable<Integer>>() {
        @Override
        public Observable<Integer> apply(Observable<Integer> integerObservable) throws Exception {
            //reduce对发射的数据进行处理，返回最终的处理结果
            //integerObservable.reduce(0, PUBLIC_SUM)
            return integerObservable.reduce(REDUCE_PUBLIC_SUM).toObservable();
        }
    };

    @Test
    public void testWindowSlide() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();
        behaviorSubject
                // 1秒作为一个基本块,横向移动
                .window(1000, TimeUnit.MILLISECONDS)
                //将flatMap汇总平铺成一个事件,然后累加成一个Observable<Integer>对象，比如说1s内有10个对象，被累加起来
                //flatMap将一个发射数据的Observable变换为多个Observables，然后将它们发射的数据合并后放进一个单独的Observable。
                .flatMap(INNER_BUCKET_SUM)
                //对这个对象2个发送，步长为1
                .window(2,1)
                //对窗口里面的进行求和,用的scan, 每次累加都会打印出来
                .flatMap(WINDOW_SUM)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        // 输出统计数据到日志
                        LOG.info("[{}] call ...... {}",
                                Thread.currentThread().getName(), integer);
                    }
                });
        for (int i = 0; i < 50; i++) {
            //200ms生产一个数据，
            behaviorSubject.onNext(i);
            LOG.info("i = {}" ,i);
            Thread.sleep(200);
        }
        countDownLatch.await();
    }
}
