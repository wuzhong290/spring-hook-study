package com.demo.designMode.service;

import com.demo.designMode.model.Fare;
import com.demo.designMode.model.TaxiRide;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Service
public class TaxiFareCalculatorService {

    public int calculateFare(TaxiRide taxiRide, Fare rideFare) {
        KieSession kieSession = TaxiFareConfiguration.getInstance().newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rideFare.getRideFare();
    }
}
