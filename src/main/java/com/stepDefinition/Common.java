package com.stepDefinition;

public class Common {


    //-----------------------------------objects start-----------------------------------------------------
    String globalEmail = System.getProperty("email");
    String globalMobile = System.getProperty("phone");
    String globalParameters = "\\src\\test\\resources\\RunConfig.properties";

    //-----------------------------------objects end -----------------------------------------------------

    private Injection inject;

    public Common(Injection inject) {
//        super(inject);
        this.inject = inject;
        inject.classpath = this.getClass().getName();
    }

//    @Given("^Browser \"([^\"]*)\" is launched$")
//    public void browserIsLaunched(String strBrowser) throws Throwable {
//        invokeApp(strBrowser);
//        driver.manage().deleteAllCookies();
//        if (!(globalEmail == null))
//        {
//                writePropertiesFile("email_id",globalEmail,globalParameters);
//        }
//        if (!(globalMobile == null))
//        {
//            writePropertiesFile("phone_Number",globalMobile,globalParameters);
//        }
//    }


//    @And("^Navigate to \"([^\"]*)\" page$")
//    public void navigateToPage(String arg0) throws Throwable {
//        if (getProp("Recording", globalParameters).equalsIgnoreCase("true")) {
//            MyScreenRecorder.startRecording("navigateToPage");
//        }
//        navigateTo(arg0, 6);
//    }

//    @And("^Close Browser$")
//    public void closeBrowser() throws Throwable {
////        if(getProp("Recording",globalParameters).equalsIgnoreCase("true")) {
////            MyScreenRecorder.stopRecording();
////        driver.quit();
//    }

//    @Given("^Start browser and open the application$")
//    public void SetUp() throws Throwable {
////        String pathnew = getProp("URL", globalParameters);
////        startApplication(driver,"chrome", pathnew);
//        String url = System.getProperty("user.dir") + globalParameters;
//        WebDriver driver = inject.getDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
//        driver.get(url);
//    }
}
