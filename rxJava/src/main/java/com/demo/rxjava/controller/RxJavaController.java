package com.demo.rxjava.controller;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.demo.rxjava.model.EventDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;
import io.reactivex.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * Created by wuzhong on 2017/9/18.
 */
@RestController
@RequestMapping("/rxjava")
public class RxJavaController {

    @RequestMapping(method = RequestMethod.GET, value = "/invoices")
    public Observable<Invoice> getInvoices() {
        return Observable.wrap(new ObservableSource<Invoice>() {
            @Override
            public void subscribe(Observer<? super Invoice> observer) {
                observer.onNext(new Invoice("Acme", new Date()));
                observer.onNext(new Invoice("Oceanic", new Date()));
                observer.onComplete();
            }
        });
    }

    @RequestMapping(method = RequestMethod.GET, value = "/single")
    public Single<String> single() {
        return Single.wrap(new SingleSource<String>() {
            @Override
            public void subscribe(SingleObserver<? super String> observer) {
                observer.onSuccess("single value");
            }
        });
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sse")
    public ObservableSseEmitter<String> sse() {
        return new ObservableSseEmitter<String>(Observable.just("single value"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages")
    public ObservableSseEmitter<String> messages() {
        return new ObservableSseEmitter<String>(Observable.wrap(new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("message 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                observer.onNext("message 2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                observer.onNext("message 3");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                observer.onNext("message 4");
                observer.onComplete();
            }
        }));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public ObservableSseEmitter<EventDto> event() {
        return new ObservableSseEmitter<EventDto>(APPLICATION_JSON_UTF8, Observable.just(
                new EventDto("Spring.io", getDate(2016, 5, 11)),
                new EventDto("JavaOne", getDate(2016, 9, 22))
        ));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/download")
    public StreamingResponseBody handle() {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write("11111111111111111111".getBytes());
                outputStream.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream.write("2222222222222222222".getBytes());
                outputStream.flush();
                outputStream.close();
            }
        };
    }

    private static Date getDate(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }

    private static class Invoice {

        private final String title;

        private final Date issueDate;

        @JSONCreator
        public Invoice(@JsonProperty("title") String title, @JsonProperty("issueDate") Date issueDate) {
            this.title = title;
            this.issueDate = issueDate;
        }

        public String getTitle() {
            return title;
        }

        public Date getIssueDate() {
            return issueDate;
        }
    }
}


