在RxJava中主要有4个角色：

Observable
Subject
Observer
Subscriber
Observable和Subject是两个“生产”实体，
Observer和Subscriber是两个“消费”实体。
说直白点Observable对应于观察者模式中的被观察者，
而Observer和Subscriber对应于观察者模式中的观察者。
Subscriber其实是一个实现了Observer的抽象类，
后面我们分析源码的时候也会介绍到。Subject比较复杂，以后再分析。

上一篇文章中我们说到RxJava中有个关键概念：事件。
观察者Observer和被观察者Observable通过subscribe()方法实现订阅关系。
从而Observable 可以在需要的时候发出事件来通知Observer。


异步场景从线程(T1)中的请求处理开始。
并发请求处理可以通过调用startCallableProcessing(Callable, Object…)
或startDeferredResultProcessing(DeferredResult, Object…)来启动，
两者都会在一个单独的线程(T2)中产生结果。
保存结果并将请求发送到容器，
以便在第三个线程(T3)中继续使用保存的结果进行处理。
在被分派的线程(T3)中，保存的结果可以通过getConcurrentResult()访问，
或者通过hasConcurrentResult()检测到它的存在。


@RequestMapping(method = RequestMethod.GET, value = "/getTimeoutInvoices")
public ObservableDeferredResult<Invoice> getTimeoutInvoices() {
    return new ObservableDeferredResult<Invoice>(100L,new Invoice("timeout", new Date()), Observable.wrap(new ObservableSource<Invoice>() {
        @Override
        public void subscribe(Observer<? super Invoice> observer) {
            //handlerResult执行必须放到另外一个线程里面，否则ObservableDeferredResult对象实例化就卡在那里了
            handlerResult(observer);
        }
    }));
}

public ObservableDeferredResult(Long timeout, Object timeoutResult, Observable<T> observable) {
    super(timeout, timeoutResult);
    Assert.notNull(observable, "observable can not be null");

    observer = new DeferredResultObserver<List<T>>(observable.toList().toObservable(), this);
}

public DeferredResultObserver(Observable<T> observable, DeferredResult<T> deferredResult) {
    this.deferredResult = deferredResult;
    this.deferredResult.onTimeout(this);
    this.deferredResult.onCompletion(this);
    //handlerResult执行必须放到另外一个线程里面，否则ObservableDeferredResult对象实例化就卡在这里了
    observable.subscribe(this);
}
