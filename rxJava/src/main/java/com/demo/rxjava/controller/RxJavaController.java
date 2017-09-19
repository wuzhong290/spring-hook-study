package com.demo.rxjava.controller;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.demo.rxjava.model.EventDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        return Observable.just(
                new Invoice("Acme", new Date()),
                new Invoice("Oceanic", new Date())
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "/single")
    public Single<String> single() {
        return Single.just("single value");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sse")
    public ObservableSseEmitter<String> sse() {
        return new ObservableSseEmitter<String>(Observable.just("single value"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages")
    public ObservableSseEmitter<String> messages() {
        return new ObservableSseEmitter<String>(Observable.just("message 1", "message 2", "message 3"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/events")
    public ObservableSseEmitter<EventDto> event() {
        return new ObservableSseEmitter<EventDto>(APPLICATION_JSON_UTF8, Observable.just(
                new EventDto("Spring.io", getDate(2016, 5, 11)),
                new EventDto("JavaOne", getDate(2016, 9, 22))
        ));
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


