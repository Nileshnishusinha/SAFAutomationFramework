package com.stepDefinition;

import com.cucumber.CustomElementFunctions;
import com.pages.VolPage;
import com.testcases.MyTestDemo;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class VOL extends CustomElementFunctions {
    private Injection inject;

    public VOL(Injection inject) throws Throwable {
        super(inject);
        this.inject = inject;
    }

//    @Then("Validate the expected title as \"([^\"]*)\"")
//    public void validateTheExpectedTitleAs(String arg0) throws Throwable {
//        inject.getPage(VolPage.class).validateTheExpectedTitleAs(arg0);
//    }
//

//
//    @Given("^check the functionality of properties file is working as expected$")
//    public void checkTheFunctionalityOfPropertiesFileIsWorkingAsExpected() throws IOException {
//        String property = getProp("browser", globalParameterFile);
//        System.out.println(property);
//        writePropertiesFile("email", "nilesh.sinha@cognizant.com", globalParameterFile);
//        getAllpropertyfromPropertiesFile(globalParameterFile);
//    }
//
//    @Given("^check the functionality of Logfourj is working as expected$")
//    public void checkTheFunctionalityOfLogfourjIsWorkingAsExpected() {
//        inject.getPage(MyTestDemo.class).checkTheFunctionalityOfLogFourJIsWorkingAsExpected();
//    }


    @When("^Test the text in H2 tag and the \"([^\"]*)\" for ShipmentID$")
    public void testShippingDetails(String ShipmentId) throws Throwable {
        inject.getPage(VolPage.class).testShippingDetails(ShipmentId);

    }

    @Then("^Validate the Customer name \"([^\"]*)\" is displayed$")
    public void validateResult(String CustomerName) throws Throwable {
//        inject.getPage(VolPage.class).validateResult(CustomerName);
    }

    @And("^Close Browser$")
    public void closeBrowser() {
        inject.driver.quit();
//        Assert.fail();
    }


    @Given("^Testing of the Screen Recording features$")
    public void testingOfTheScreenRecordingFeatures() throws Exception {
        inject.getPage(MyTestDemo.class).navigationTest();
    }
}
