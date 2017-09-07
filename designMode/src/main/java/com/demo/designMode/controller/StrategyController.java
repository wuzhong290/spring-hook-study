package com.demo.designMode.controller;

import com.demo.designMode.model.Fare;
import com.demo.designMode.model.TaxiRide;
import com.demo.designMode.service.StrategyService;
import com.demo.designMode.service.TaxiFareCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private TaxiFareCalculatorService taxiFareCalculatorService;

    @RequestMapping(value = "/strategy")
    @ResponseBody
    public void strategy(){
        strategyService.test();
    }


    @RequestMapping(value = "/strategy1")
    @ResponseBody
    public void strategy1(){
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setNightSurcharge(false);
        taxiRide.setDistanceInMile(9L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        System.out.println(totalCharge);
    }
}
