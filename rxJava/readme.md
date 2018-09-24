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
