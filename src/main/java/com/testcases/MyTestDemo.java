package com.testcases;

import com.cucumber.CustomElementFunctions;
import com.stepDefinition.Injection;
import com.utility.MyScreenRecorder;

public class MyTestDemo extends CustomElementFunctions {
    private Injection inject;

    public MyTestDemo(Injection inject) {
        super(inject);
    }

    public void navigationTest() throws Exception {
        //        TODO
        screenRecording();
    }

    public void screenRecording() throws Exception {
        //        MyScreenRecorder.startRecording("navigationTest");
        navigateTo("http://www.google.com", 6);
        navigateTo("http://www.facebook.com", 6);
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
//        MyScreenRecorder.stopRecording();
    }

    public void navigation_FeatureTest() throws Exception {
        MyScreenRecorder.startRecording("navigation_FeatureTest");
//FIXME
        inject.driver.get("http://www.amazon.com");
        inject.driver.navigate().to("http://www.rediff.com");
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();
        inject.driver.navigate().back();
        inject.driver.navigate().forward();

        MyScreenRecorder.stopRecording();
    }


    public void checkTheFunctionalityOfLogFourJIsWorkingAsExpected() {

        System.out.println("hi");
//        Logger log =Logger.getLogger(String.valueOf(VolPage.class));
//        invokeApp();
//        log.info("Launching browser");
//        startApplication(driver,"chrome","http://www.google.com");
//        log.info("google url has been launched");
//        quitBrowser(driver);
//        log.info("closing browser");
    }
}