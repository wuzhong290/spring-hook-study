package com.demo.designMode.rule;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wuzhong on 2017/9/8.
 */
@Component("taxiFareRule")
public class TaxiFareRule {
    private static final String drlFile = "TAXI_FARE_RULE.drl";

    public static KieContainer getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        static final KieContainer INSTANCE = getKieContainer();
    }

    private static KieContainer getKieContainer(){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }
}
