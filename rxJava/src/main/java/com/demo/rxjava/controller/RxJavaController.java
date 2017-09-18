package com.demo.rxjava.controller;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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


