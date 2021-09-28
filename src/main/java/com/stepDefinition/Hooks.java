package com.stepDefinition;

import com.cucumber.CustomElementFunctions;
import cucumber.api.java.en.Given;

public class Hooks extends CustomElementFunctions {
    private Injection inject;

    public Hooks(Injection inject) {
        super(inject);
        this.inject = inject;
    }

    String URL = getProp("URL", inject.globalParameterFile);

    @Given("^Start browser as \"([^\"]*)\" and open the application$")
    public void startBrowserAsAndOpenTheApplication(String arg0) throws Throwable {
        invokeApp(arg0);
        navigateTo(URL, 10);
    }
}
