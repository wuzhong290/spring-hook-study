package com.demo.designMode.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;

/**
 * Created by wuzhong on 2017/9/7.
 */
public class TaxiFareConfiguration {
    private static final String drlFile = "TAXI_FARE_RULE.drl";

    public static KieContainer getInstance(){
        return INSTANCE;
    }

    static KieContainer INSTANCE;

    static {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        INSTANCE = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
    }
}
