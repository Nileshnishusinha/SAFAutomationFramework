package com.cucumber;

import com.stepDefinition.Injection;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.stepDefinition.Injection.globalParameterFile;

public class CustomElementFunctions {
    public String webElementWaitTime;
    //    public WebDriver driver;
    Injection inject;

    public CustomElementFunctions(Injection inject) {
        this.inject = inject;
    }

    public void startApplication(String browserName, String appURL) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
            inject.driver = new ChromeDriver();
        }
//        else if (browserName.equalsIgnoreCase("firefox")) {
//            driver = new FirefoxDriver();
//        }
        else {
            System.out.println("We do not support this browser");
        }
        inject.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        inject.driver.manage().window().maximize();
        inject.driver.get(appURL);
        inject.driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
//        return driver;
    }

    //    public void quitBrowser(WebDriver tempDriver) {
//        tempDriver=this.driver;
//        tempDriver.quit();
//    }
//
    public synchronized void waitForElementToBeClickable(WebElement element) throws Exception {
        try {
            webElementWaitTime = getProp("webElementWaitTime", inject.globalParameterFile);
            WebDriverWait wait = new WebDriverWait(inject.driver, Long.parseLong(webElementWaitTime));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String URL, int intWaitTime) throws Exception {
        try {
            inject.driver.get(URL);
            inject.driver.manage().timeouts().implicitlyWait(intWaitTime, TimeUnit.SECONDS);
            inject.driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        } catch (Exception e) {
//            reportEvent("error", e.toString());
            e.printStackTrace();
        }
    }

    public boolean validateTextIgnoreCase(By objLocator, String expectedText) {
        try {
            String actualText = "";
            if (!(inject.driver.findElement(objLocator).getText().equals(""))) {
                actualText = inject.driver.findElement(objLocator).getText();
            } else if (!(inject.driver.findElement(objLocator).getAttribute("value").equals(""))) {
                actualText = inject.driver.findElement(objLocator).getAttribute("value");
            } else if (!(inject.driver.findElement(objLocator).getAttribute("name").equals(""))) {
                actualText = inject.driver.findElement(objLocator).getAttribute("name");
            }

            if (actualText.equalsIgnoreCase(expectedText)) {
                scrollToElement(objLocator, 5);
//                reportEvent("info","Actual data " + actualText + " is matching with expected data " + expectedText);
                return true;
            } else {
//                reportEvent("info","Actual data " + actualText + " is not matching with expected data " + expectedText);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
//                reportEvent("error", "Text from the application is not matching with the expected text");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    public void scrollToElement(By objLocator, Integer intWaitTime) throws Exception {
        Date strStart = currentDateTime();
        Date strStop;
        try {
            for (int i = 0; i <= intWaitTime; i++) {

                if (waitForElement(objLocator, intWaitTime)) {

                    try {
                        WebElement ele = inject.driver.findElement(objLocator);
                        ((JavascriptExecutor) inject.driver).executeScript("arguments[0].scrollIntoView(true);", ele);
                        System.out.println("Scrolled to object: " + objLocator.toString());
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    strStop = currentDateTime();
                    if (secondsDifference(strStart, strStop) == intWaitTime || secondsDifference(strStart, strStop) > intWaitTime) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date currentDateTime() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Date strDateTime = format.parse(dtf.format(now).toString());
            return strDateTime;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean waitForElement(By objLocator, Integer intWaitTime) throws Exception {
        //declaring explicit wait for object
        WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
        try {
            WebElement elemnt = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
            System.out.println("Object: " + objLocator.toString() + " is clickable");
            return true;
        } catch (Exception e) {
            try {
                WebElement elemnt = wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
                System.out.println("Object: " + objLocator.toString() + " is visible");
                return true;
            } catch (Exception e1) {
                try {
                    WebElement elemnt = wait.until(ExpectedConditions.presenceOfElementLocated(objLocator));
                    System.out.println("Object: " + objLocator.toString() + " is present");
                    return true;
                } catch (Exception e2) {
                    e2.printStackTrace();

                    System.out.println("Object with xpath- " + objLocator.toString() + " not found");
                    return false;
                }
            }
        }

    }

    public int secondsDifference(Date start, Date stop) {
        long diffSeconds = 0;
        int diffSecs = 0;
        try {
            long diff = stop.getTime() - start.getTime();
            diffSeconds = diff / 1000 % 60;
            diffSecs = (int) diffSeconds;
            return diffSecs;
        } catch (Exception e) {
            return diffSecs;
        }
    }

    public String getAttribute(By objLocator, String strAttribute) throws Exception {
        try {
            String strValue = inject.driver.findElement(objLocator).getAttribute(strAttribute);
            if (strValue == null) {
                return "";
            } else {
                return strValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public void highlightElement(By objLocator) throws Exception {
        try {
            //Create object of a JavascriptExecutor interface
            JavascriptExecutor js = (JavascriptExecutor) inject.driver;
            //use executeScript() method and pass the arguments
            WebElement ele = inject.driver.findElement(objLocator);
            //Here i pass values based on css style. Yellow background color with solid red color border.
            for (int i = 0; i < 1; i++) {
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, "color: yellow; border: 2px solid yellow;");
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", ele, "");
            }
            System.out.println("Object: " + objLocator.toString() + " is highlighted");
        } catch (Exception e) {
//            reportEvent("error", e.toString());
            e.printStackTrace();
        }
    }

    public void mouseHover(By objLocator) throws Exception {
        try {
            highlightElement(objLocator);
            Actions action = new Actions(inject.driver);
            WebElement ele = inject.driver.findElement(objLocator);
            action.moveToElement(ele).build().perform();
            System.out.println("Mouseover on object: " + objLocator.toString() + " is done");
        } catch (Exception e) {
            e.printStackTrace();
//            reportEvent("error", e.toString());
        }
    }

    public void selectDropdownByText(By objLocator, String strText) throws Exception {
        scrollToElement(objLocator, 2);
        Select ele = new Select(inject.driver.findElement(objLocator));
        try {
            //highlightElement(objLocator);
            ele.selectByVisibleText(strText);
            System.out.println("Selecting value " + strText + " from: " + objLocator.toString() + " is done");
//            reportEvent("pass", "Selecting value " + strText + " is successful");
        } catch (Exception e) {
            try {
                /*In case any alert pops up*/
                Thread.sleep(1000);
                Robot t = new Robot();
                t.keyPress(KeyEvent.VK_ESCAPE);
                Thread.sleep(1000);
                t.keyRelease(KeyEvent.VK_ESCAPE);
                System.out.println("Selecting value from: " + objLocator.toString() + " is done");
//                reportEvent("pass", "Selecting dropdown value " + strText + " is successful");
            } catch (Exception e1) {
                e1.printStackTrace();
//                reportEvent("error", e.toString());
            }
        }
    }

    public void selectDropdownByIndex(By objLocator, int intIndex) throws Exception {
        try {
            //highlightElement(objLocator);
            Select ele = new Select(inject.driver.findElement(objLocator));
            ele.selectByIndex(intIndex);
            System.out.println("Selecting value from: " + objLocator.toString() + " is done");
//            reportEvent("pass", "Selecting dropdown value " + ele.getFirstSelectedOption().toString() + " is successful");
        } catch (Exception e) {
            e.printStackTrace();
//            reportEvent("error", e.toString());
        }
    }

    public void selectDropdownByValue(By objLocator, String strValue) throws Exception {
        Select ele = new Select(inject.driver.findElement(objLocator));
        try {
            //highlightElement(objLocator);
            ele.selectByValue(strValue);
            System.out.println("Selecting value from: " + objLocator.toString() + " is done");
//            reportEvent("pass", "Selecting dropdown value " + ele.getFirstSelectedOption().toString() + " is successful");
        } catch (Exception e) {
            try {
                /*In case any alert pops up*/
                Thread.sleep(1000);
                Robot t = new Robot();
                t.keyPress(KeyEvent.VK_ESCAPE);
                Thread.sleep(1000);
                t.keyRelease(KeyEvent.VK_ESCAPE);
                System.out.println("Selecting value from: " + objLocator.toString() + " is done");
//                reportEvent("pass", "Selecting dropdown value " + strValue + " is successful");
            } catch (Exception e1) {
                e1.printStackTrace();
//                reportEvent("error", e.toString());
            }
        }
    }

    public String getProp(String strPropName, String strPropPath) {
        try {
            //File srcRep = new File(".OR_IE.properties");
            FileInputStream fis = null;
            fis = new FileInputStream(System.getProperty("user.dir") + strPropPath);
            // Create Properties class object to read properties file
            Properties pro = new Properties();
            // Load file so we can use into our script
            pro.load(fis);
            if (pro.getProperty(strPropName) != null) {
                return pro.getProperty(strPropName);
            } else {
                System.out.println("Object property " + strPropName + " is not found");
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static void writePropertiesFile(String strKey, String strKeyValue, String strFilePath) {
        Properties prop = new Properties();
        strFilePath = System.getProperty("user.dir") + strFilePath;
        try {
            InputStream in = new FileInputStream(strFilePath);
            prop.load(in);

        } catch (IOException ex) {
            System.out.println(ex);
        }
        //Setting the value to  our properties file.
        prop.setProperty(strKey, strKeyValue);

        try {
            prop.store(new FileOutputStream(strFilePath), null);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void getAllpropertyfromPropertiesFile(String strFilePath) throws IOException {
        Properties prop = new Properties();
        strFilePath = System.getProperty("user.dir") + strFilePath;
        try {
            InputStream in = new FileInputStream(strFilePath);
            prop.load(in);
            prop.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }

    public void setValue(By objLocator, String strValue, int intWaitTime) throws Exception {
        try {
            scrollToElement(objLocator, 5);
            WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
            WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
            for (int i = 0; i < intWaitTime; i++) {
                ele.sendKeys(strValue.trim());
                Thread.sleep(200);
                if (ele.getText().equals(strValue)) {
                    break;
                } else if (ele.getAttribute("value") != null) {
                    if (ele.getAttribute("value").equals(strValue)) {
                        break;
                    } else {
                        ClearTextSafely(objLocator);
                        ele.sendKeys(strValue);
                        Thread.sleep(1000);
                        break;
                    }
                } else {
                    ClearTextSafely(objLocator);
                    ele.sendKeys(strValue);
                    Thread.sleep(1000);
                }
            }
//            reportEvent("pass", "Entered " + strValue + " in textbox");
            System.out.println("Data entry in object: " + objLocator.toString() + " is done");
        } catch (Exception e) {
//            reportEvent("fail", e.toString());
            e.printStackTrace();
        }
    }

    public boolean ClearTextSafely(By objLocator) throws Exception {
        try {
            WebElement element = inject.driver.findElement(objLocator);
//			((JavascriptExecutor) FunctionLibrary.driver).executeScript("arguments[0].value ='';", element);
//            Actions action = new Actions(driver);
//            action.clickAndHold(element).perform();
//            Thread.sleep(1000);
//            action.release().perform();
            Thread.sleep(1000);
            element.sendKeys(Keys.CONTROL + "a");
            Thread.sleep(1000);
            element.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(1000);
            element.sendKeys(Keys.TAB);
            Thread.sleep(4000);
            System.out.println("textbox is cleared");
            return true;
        } catch (Exception e) {
            System.out.println("Could not clear textbox");
            return false;
        }
    }

    public void clickElement(By objLocator, int intWaitTime) {
        try {
//			WebDriverWait wait = new WebDriverWait(global.driver, global.intWaitTime);
//			WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
            WebElement ele = inject.driver.findElement(objLocator);
            ele.click();
            System.out.println("Clicked on object: " + objLocator.toString());
        } catch (Exception e) {
            try {
                WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
                JavascriptExecutor jse = (JavascriptExecutor) inject.driver;
                jse.executeScript("window.scrollTo(0," + ele.getLocation().getY() + ")");
                jse.executeScript("arguments[0].click();", ele);
                System.out.println("Clicked on object: " + objLocator.toString());
            } catch (Exception e1) {
                try {
                    WebElement ele = inject.driver.findElement(objLocator);
                    JavascriptExecutor jse = (JavascriptExecutor) inject.driver;
                    jse.executeScript("window.scrollTo(0," + ele.getLocation().getY() + ")");
                    jse.executeScript("arguments[0].click();", ele);
                } catch (Exception e2) {
                    e2.printStackTrace();

                }
            }
        }
    }

    public void clickElement(String strText, int intWaitTime) {
        try {
//			WebDriverWait wait = new WebDriverWait(global.driver, global.intWaitTime);
//			WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
            WebElement ele = inject.driver.findElement(By.linkText(strText));
            ele.click();
            System.out.println("Clicked on Link: " + ele.getText().toString());
        } catch (Exception e) {
            try {
                WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
                WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(strText)));
                JavascriptExecutor jse = (JavascriptExecutor) inject.driver;
                jse.executeScript("window.scrollTo(0," + ele.getLocation().getY() + ")");
                jse.executeScript("arguments[0].click();", ele);
                System.out.println("Clicked on Link: " + ele.getText().toString());
            } catch (Exception e1) {
                try {
                    WebElement ele = inject.driver.findElement(By.linkText(strText));
                    JavascriptExecutor jse = (JavascriptExecutor) inject.driver;
                    jse.executeScript("window.scrollTo(0," + ele.getLocation().getY() + ")");
                    jse.executeScript("arguments[0].click();", ele);
                } catch (Exception e2) {
                    e2.printStackTrace();

                }
            }
        }
    }

    public void clickElement(By objLocator, boolean flgScroll, int intWaitTime) {
        try {
            WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
            WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
            if (flgScroll) {
                JavascriptExecutor jse = (JavascriptExecutor) inject.driver;
                jse.executeScript("window.scrollTo(0," + ele.getLocation().getY() + ")");
            }
            ele.click();
            System.out.println("Clicked on object: " + objLocator.toString());
        } catch (Exception e) {
            try {
                WebElement ele = inject.driver.findElement(objLocator);
                ele.click();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void invokeApp(String strBrowser) {
        Map<String, Object> deviceMetrics = new HashMap<>();
        inject.strBrowser = strBrowser;
        if (strBrowser.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\IEDriverServer.exe");
            inject.driver = new InternetExplorerDriver();
            inject.driver.manage().window().maximize();
        } else if (strBrowser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("start-maximized");
            inject.driver = new ChromeDriver(options);
        } else if (strBrowser.equalsIgnoreCase("mozilla")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\geckodriver.exe");
            inject.driver = new FirefoxDriver();
            inject.driver.manage().window().maximize();
        } else if (strBrowser.equalsIgnoreCase("chrome-mobile")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
           /* deviceMetrics.put("width", 360);
            deviceMetrics.put("height", 640);
            deviceMetrics.put("pixelRatio", 2);*/
            String device = getProp("DeviceName", System.getProperty("user.dir") + globalParameterFile);
            Map<String, String> mobileEmulation = new HashMap<>();

            mobileEmulation.put("deviceName", device);
            Map<String, Object> chromeOptions = new HashMap<>();

            chromeOptions.put("mobileEmulation", mobileEmulation);

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();

            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            inject.driver = new ChromeDriver(capabilities);
        }
    }

//    public void invokeApp() throws IOException {
//
//        // Loading properties file
//        InputStream propertyFile = new FileInputStream(globalParameterFile);
//        Properties prop = new Properties();
//        prop.load(propertyFile);
//
//        // Check if desktop browser or remote server(SauceLabs/Perfecto) for
//        // execution
//        // Default scenario
//        if (prop.getProperty("isRemoteEnabled").equalsIgnoreCase("N")
//                || prop.getProperty("isRemoteEnabled").equalsIgnoreCase("")) {
//
//            // Desktop browser has been selected now checking which browser is
//            // selected for execution
//            if (prop.getProperty("browserName").equalsIgnoreCase("ie")) {
//                System.setProperty("webdriver.ie.driver",
//                        System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\IEDriverServer.exe");
//                driver = new InternetExplorerDriver();
//                driver.manage().window().maximize();
//                // Default scenario
//            } else if (prop.getProperty("browserName").equalsIgnoreCase("chrome")
//                    || prop.getProperty("browserName").equalsIgnoreCase("")) {
//                System.setProperty("webdriver.chrome.driver",
//                        System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
//                ChromeOptions options = new ChromeOptions();
//                options.setExperimentalOption("useAutomationExtension", false);
//                options.addArguments("start-maximized");
//                driver = new ChromeDriver(options);
//            } else if (prop.getProperty("browserName").equalsIgnoreCase("mozilla")) {
//                System.setProperty("webdriver.gecko.driver",
//                        System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\geckodriver.exe");
//                driver = new FirefoxDriver();
//                driver.manage().window().maximize();
//            }
//
//        } else if (prop.getProperty("isRemoteEnabled").equalsIgnoreCase("Y")) {
//            // Check if SauceLabs or Perfecto is selected for execution
//            if (prop.getProperty("remoteType").equalsIgnoreCase("SauceLabs")) {
//
//                // Set the SauceLabs credentials
//                String userName = prop.getProperty("sauceUserName");
//                String accessKey = prop.getProperty("sauceAccessKey");
//                String url = "https://" + userName + ":" + accessKey + "@ondemand.saucelabs.com:443/wd/hub";
//
//                // Set the SauceLabs details
//                DesiredCapabilities dc = new DesiredCapabilities();
//
//                // Check if sauce device is PC/Mac
//                if (prop.getProperty("sauceDevice").equalsIgnoreCase("PC")
//                        || prop.getProperty("sauceDevice").equalsIgnoreCase("Mac")) {
//                    dc.setCapability("platform",
//                            prop.getProperty("sauceOS") + " " + prop.getProperty("sauceOSVersion"));
//                    dc.setCapability("browserName", prop.getProperty("sauceBrowser"));
//                    dc.setCapability("browserVersion", prop.getProperty("sauceBrowserVersion"));
//                } else {
//                    dc.setCapability("deviceName", prop.getProperty("sauceMobileDevice"));
//                    dc.setCapability("deviceOrientation", prop.getProperty("sauceMobileOrientation"));
//                    dc.setCapability("platformName", prop.getProperty("sauceMobileOS"));
//                    dc.setCapability("platformVersion", prop.getProperty("sauceMobileOSVersion"));
//                    dc.setCapability("appiumVersion", prop.getProperty("sauceMobileAppiumVersion"));
//
//                    // Check if the app type is web / native / hybrid
//                    if (prop.getProperty("mobileAppType").equalsIgnoreCase("Web")) {
//                        dc.setCapability("browserName", "Chrome");
//                    } else {
//                        dc.setCapability("browserName", "");
//                        dc.setCapability("app", prop.getProperty("sauceAppURL"));
//                    }
//                }
//
//                // Start driver and open the URL:
//                driver = new RemoteWebDriver(new URL(url), dc);
//                driver.manage().window().maximize();
//                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//
//                // Default scenario
//            } else if (prop.getProperty("remoteType").equalsIgnoreCase("Perfecto")
//                    || prop.getProperty("remoteType").equalsIgnoreCase("")) {
//
//                String host = prop.getProperty("perfectoCloudURL");
//
//                // Set the Perfecto details
//                DesiredCapabilities dc = new DesiredCapabilities();
//                dc.setCapability("user", prop.getProperty("perfectoUserName"));
//                // Decoding and adding to Desired Capabilities
////                dc.setCapability("password", Encoder.base64DecodeFrom(prop.getProperty("perfectoPassword")));
//                // Check whether tunnel ID is required
//                if (prop.getProperty("tunnelIdRequired").equalsIgnoreCase("Y")) {
//                    dc.setCapability("tunnelId", Injection.tunnelId);
//                }
//
//                // Check if execution is set to Perfecto Web / Mobile
//                if (prop.getProperty("perfectoDevice").equalsIgnoreCase("Web")) {
//
//                    // dc.setCapability("platformName",
//                    // prop.getProperty("perfectoWebOS"));
//                    // dc.setCapability("platformVersion",
//                    // prop.getProperty("perfectoWebOSVersion"));
//                    // dc.setCapability("browserName",
//                    // prop.getProperty("perfectoWebBrowser"));
//                    // dc.setCapability("browserVersion",
//                    // prop.getProperty("perfectoWebBrowserVersion"));
//                    // dc.setCapability("resolution",
//                    // prop.getProperty("perfectoWebResolution"));
//                    // dc.setCapability("location",
//                    // prop.getProperty("perfectoWebLocation"));
//
//                } else if (prop.getProperty("perfectoDevice").equalsIgnoreCase("Mobile")) {
//                    inject.strMobileOS = prop.getProperty("perfectoMobilePlatform");
//
//                    dc.setCapability("platformName", prop.getProperty("perfectoMobilePlatform"));
////                    dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
//                    dc.setCapability("platformVersion", prop.getProperty("perfectoMobilePlatformVersion"));
//                    dc.setCapability("location", prop.getProperty("perfectoMobileLocation"));
//
//                    // For web app
//                    if (prop.getProperty("mobileAppType").equalsIgnoreCase("Web")) {
//                        if (prop.getProperty("perfectoMobilePlatform").equalsIgnoreCase("Android")) {
//                            dc.setCapability("browserName", prop.getProperty("mobileBrowser"));
//
//                        } else if (prop.getProperty("perfectoMobilePlatform").equalsIgnoreCase("iOS")) {
//                            dc.setCapability("browserName", prop.getProperty("mobileBrowser"));
//                        }
//
//                    } else if (prop.getProperty("mobileAppType").equalsIgnoreCase("Native")) {
//                        // For native app
//                        if (prop.getProperty("perfectoMobilePlatform").equalsIgnoreCase("Android")) {
//                            dc.setCapability("appPackage", prop.getProperty("appPackage"));
//                            // dc.setCapability("appActivity",
//                            // prop.getProperty("appActivity"));
//                            dc.setCapability("browserName", "");
//
//                        } else if (prop.getProperty("perfectoMobilePlatform").equalsIgnoreCase("iOs")) {
//                            dc.setCapability("bundleId", prop.getProperty("bundleId"));
//                            dc.setCapability("browserName", "");
//                        }
//
//
//                    }
//                    // dc.setCapability("app", prop.getProperty("appURL"));
//
//                    if (prop.getProperty("isPerfectoDeviceIDUsed").equalsIgnoreCase("Y")) {
//                        // Set the device ID for execution
//                        dc.setCapability("deviceName", prop.getProperty("perfectoDevideID"));
//                        //dc.setCapability("UDID", prop.getProperty("perfectoDevideID"));
//
//                    } else if (prop.getProperty("isPerfectoDeviceIDUsed").equalsIgnoreCase("N")) {
//
//                        // Set the device attributes for execution
//
//                        dc.setCapability("resolution", prop.getProperty("perfectoMobileResolution"));
//                        dc.setCapability("manufacturer", prop.getProperty("perfectoMobileManufacturer"));
//                        dc.setCapability("model", prop.getProperty("perfectoMobileModel"));
//                    }
//                }
//
//                driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), dc);
//                driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
//            }
//        }
//    }

    public boolean exists(String objName, By objLocator, Integer intWaitTime) throws Exception {
        //Check whether element is clickable or not
//        try {
        WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
        if (wait.until(ExpectedConditions.presenceOfElementLocated(objLocator)) != null) {
            System.out.println("Object: " + objLocator.toString() + " is present");
//            reportEvent("pass", "Object " + objName + " is present");
            return true;
        } else if (wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator)) != null) {
            System.out.println("Object: " + objLocator.toString() + " is visible");
//            reportEvent("pass", "Object " + objName + " is present");
            return true;
        } else if (wait.until(ExpectedConditions.elementToBeClickable(objLocator)) != null) {
            System.out.println("Object: " + objLocator.toString() + " is Clickable");
//            reportEvent("pass", "Object " + objName + " is present");
            return true;
        } else {
//            reportEvent("fail", "Object " + objName + " with locator " + objLocator.toString() + " is not found");
            return false;
        }
//        } catch (Exception e) {
//            reportEvent("error", e.toString());
//            e.printStackTrace();
//            return false;
//        }
    }

    public boolean notExists(String objName, By objLocator, Integer intWaitTime) throws Exception {
        //Check whether element is clickable or not
        try {
            WebDriverWait wait = new WebDriverWait(inject.driver, intWaitTime);
            if (wait.until(ExpectedConditions.elementToBeClickable(objLocator)) != null) {
                System.out.println("Object: " + objLocator.toString() + " is clickable");
//                reportEvent("fail", "Object " + objName + " is present");
                return false;
            } else if (wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator)) != null) {
                System.out.println("Object: " + objLocator.toString() + " is visible");
//                reportEvent("fail", "Object " + objName + " is present");
                return false;
            } else if (wait.until(ExpectedConditions.presenceOfElementLocated(objLocator)) != null) {
                System.out.println("Object: " + objLocator.toString() + " is Present");
//                reportEvent("fail", "Object " + objName + " is present");
                return false;
            } else {
//                reportEvent("pass", "Object " + objName + " with locator " + objLocator.toString() + " does not exist");
                return true;
            }
        } catch (Exception e) {
//            reportEvent("pass", "Object " + objName + " with locator " + objLocator.toString() + " does not exist");
            return true;
        }
    }

    public void moveCursor(By objLocator) throws Exception {
        try {
            scrollToElement(objLocator, 5);
            Actions action = new Actions(inject.driver);
            WebElement ele = inject.driver.findElement(objLocator);
            action.moveToElement(ele).build().perform();
//	        System.out.println("Mouseover on object: "+objLocator.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveCursor(By objLocator, By objNext, int intWaitTime) throws Exception {
        try {
            Actions action = new Actions(inject.driver);
            WebElement ele;
            for (int i = 0; i < 30; i++) {
                ele = inject.driver.findElement(objLocator);
                scrollToElement(objLocator, intWaitTime);
                action.moveToElement(ele).build().perform();
                if (waitForElement(objNext, intWaitTime)) {
                    break;
                } else {
                    Thread.sleep(1000);
                }
            }
            System.out.println("Mouseover on object: " + objLocator.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String takeFullScreenshot(String strPath, String strStamp) throws Exception {
        try {
//        	Screenshot full_Screenshot1 = new AShot().takeScreenshot(global.driver);
            File dir = new File(strPath + "\\");
            dir.mkdir();
            File src = ((TakesScreenshot) inject.driver).getScreenshotAs(OutputType.FILE);
//        	try {
            FileUtils.copyFile(src, new File(strPath + "\\" + strStamp + ".png"));
//			ImageIO.write(full_Screenshot1.getImage(), "PNG", new File(strPath+"\\" + strStamp + ".png"));
            return strPath + "\\" + strStamp + ".png";
        } catch (Exception e) {
            try {

            } catch (Exception e1) {
//                reportEvent("error", e.toString());
            }
            e.printStackTrace();
            return null;
        }
    }

    public boolean switchWindow(String strTitle, int intWaitTime) throws Exception {
        String CurrentWindow;
        boolean flgswitch = false;
        String FirstWindow = "";
        try {
            try {
                FirstWindow = inject.driver.getWindowHandle();  //will keep current window to switch back
            } catch (Exception e) {

            }
            for (int i = 0; i < intWaitTime; i++) {
                for (String winHandle : inject.driver.getWindowHandles()) {
                    if (inject.strBrowser.equals("IE")) {
                        Thread.sleep(2000);
                    }
                    if (inject.driver.switchTo().window(winHandle).getTitle().toLowerCase().contains(strTitle.toLowerCase())) {
                        //This is the one I am looking for
                        try {
                            CurrentWindow = inject.driver.getWindowHandle();
                            System.out.println("Previous Window: " + FirstWindow + " | Current Window: " + CurrentWindow);
                            if (!FirstWindow.equals(CurrentWindow)) {
                                Thread.sleep(1000);
                                inject.driver.manage().window().maximize();
                                System.out.println("Switched to window: " + inject.driver.getTitle().toString());
                                flgswitch = true;
                                break;
                            } else {
                                inject.driver.switchTo().window(FirstWindow);
                            }
                        } catch (Exception e) {
                            inject.driver.switchTo().window(FirstWindow);
                            System.out.println("Could not switch window");
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            inject.driver.switchTo().window(FirstWindow);
                        } catch (Exception e) {

                        }
                    }
                }
                if (flgswitch = true) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            reportEvent("error", e.toString());
        }
        if (flgswitch) {
            return true;
        } else {
            return false;
        }
    }

    public boolean switchWindow(Integer intWindow, int intWaitTime) throws Exception {
        boolean flgswitch = false;
        String FirstWindow = "";
        String CurrentWindow = "";
        try {
            FirstWindow = inject.driver.getWindowHandle();  //will keep current window to switch back
        } catch (Exception er) {
            FirstWindow = "";
        }
        if (intWindow > 0) {
            for (int i = 0; i < 60; i++) {
                if (inject.driver.getWindowHandles().size() > 1) {
                    break;
                } else {
                    Thread.sleep(1000);
                }
            }
        }
        if (inject.strBrowser.equals("IE")) {
            Thread.sleep(2000);
        }
        try {
            Set handles = inject.driver.getWindowHandles();
            String[] individualHandle = new String[handles.size()];
            Iterator it = handles.iterator();
            int i = 0;
            while (it.hasNext()) {
                individualHandle[i] = (String) it.next();
                i++;
            }
//	        try {
            for (int j = 1; j < intWaitTime; j++) {
                try {
                    inject.driver.switchTo().window(individualHandle[intWindow]);
                    CurrentWindow = inject.driver.getWindowHandle();
                    Thread.sleep(1000);
                    System.out.println("Previous Window: " + FirstWindow + " | Current Window: " + CurrentWindow);
                    if (!FirstWindow.equals(CurrentWindow)) {
                        inject.driver.manage().window().maximize();
                        inject.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                        System.out.println("Switched to window nuber: " + intWindow);
                        flgswitch = true;
                        break;
                    } else {
                        System.out.println("Could not switch to window number: " + intWindow);
                    }
                } catch (Exception eswitch) {
                    eswitch.printStackTrace();
                }
            }
//			 } catch (InterruptedException e) {
//				e.printStackTrace();
//			 }
        } catch (Exception e) {
//            reportEvent("error", "Frame could not be switched - " + e.toString());
            e.printStackTrace();
        }
        if (flgswitch) {
            return true;
        } else {
            return false;
        }
    }
//    public boolean checkLink(By objLocator, String strName) throws Exception {
//        HttpClient client = (HttpClient) HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(driver.findElement(objLocator).getAttribute("href"));
//        try {
//            HttpResponse response = client.execute(request);
//            // verifying response code and The HttpStatus should be 200 if not,
//            // increment invalid link count
//            ////We can also check for 404 status code like response.getStatusLine().getStatusCode() == 404
//            if (response.getStatusLine().getStatusCode() != 200) {
//                System.out.println(strName + " Status Code: " + response.getStatusLine().getStatusCode());
//                reportEvent("error", strName + " link broken");
//                return false;
//            } else {
//                System.out.println(strName + " Status Code: " + response.getStatusLine().getStatusCode());
//                reportEvent("info", strName + " link is active");
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            reportEvent("fail", e.toString());
//            return false;
//        }
//    }

}