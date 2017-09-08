package com.demo.designMode.service;

import com.demo.designMode.model.Fare;
import com.demo.designMode.model.TaxiRide;
import com.demo.designMode.rule.TaxiFareRule;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Service
public class TaxiFareCalculatorService {

    @Autowired
    private TaxiFareRule taxiFareRule;

    public int calculateFare(TaxiRide taxiRide, Fare rideFare) {
        KieSession kieSession = taxiFareRule.getInstance().newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rideFare.getRideFare();
    }
}
