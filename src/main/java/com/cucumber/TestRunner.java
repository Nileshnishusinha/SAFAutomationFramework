package com.cucumber;

import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/Features"},
        glue = {"com.stepDefinition"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:Reports/cucumber-reports/report.html"},
        tags = {"@Tc_04"},
        dryRun = false,
        monochrome = true,
        strict = true)


public class TestRunner {
    @AfterClass
    public static void writeExtentReport() {
//        Reporter.loadXMLConfig(new File("D:/Projects/Framework/Config/extent-config.xml"));
//        Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
//        Reporter.getExtentReport();
        Reporter.setSystemInfo("User Name", System.getProperty("Nilesh"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("Machine", "Windows 10" + "64 Bit");
        Reporter.setSystemInfo("Selenium", "3.7.0");
        Reporter.setSystemInfo("Maven", "3.5.2");
        Reporter.setSystemInfo("Java Version", "1.8.0_151");
        Reporter.assignAuthor("Rapid Automation Tool - Nilesh Sinha");
    }

}
