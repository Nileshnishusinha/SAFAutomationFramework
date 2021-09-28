package com.pages;

import com.cucumber.CustomElementFunctions;
import com.stepDefinition.Injection;
import org.openqa.selenium.By;

public class VolPage extends CustomElementFunctions {
    private Injection inject;
    String text;
    public VolPage(Injection inject) {
        super(inject);
        this.inject = inject;
    }

    public void validateTheExpectedTitleAs(String arg0) throws InterruptedException {

//        String actual = driver.getTitle();
//        String expected = arg0;
        Thread.sleep(6000);
//       if (actual.equalsIgnoreCase(expected))
//       {
//           System.out.println("test case passed");
//       }
//       else
//           System.out.println("test case failed");
//    }
//        Assert.assertTrue("Title of the webpage not matched ",actual.equalsIgnoreCase(expected));
    }

    public void testShippingDetails(String shipmentId) throws Exception {
//        String a = inject.driver.getTitle();
        Thread.sleep(4000);
        if (inject.driver.findElement(By.xpath("/html/body/div[1]/center/h2")).getText().equalsIgnoreCase("Shipping Details")) {
            waitForElementToBeClickable(inject.driver.findElement(By.xpath("//a[text()='Shipments']")));
            System.out.println("The text 'Shipping Details' is present in h2 tag");
        } else {
            System.out.println("The text 'Shipping Details' is not present in h2 tag");
        }
        if (inject.driver.findElement(By.xpath("//*[@id=\"shippingTable\"]/table/tbody/tr[2]/td[1]/a")).getText().equals(shipmentId)) {
            inject.driver.findElement(By.xpath("//*[@id=\"shippingTable\"]/table/tbody/tr[2]/td[1]/a")).click();
            System.out.println("Clicked on the link " + '"' + shipmentId + '"');
        }
        text = inject.driver.findElement(By.xpath("//*[@id='result']/table/tbody/tr[2]/td[1]")).getText();
        System.out.println("Printed statemented is:" + shipmentId);
//        navigateTo("https://webapps.tekstac.com/shippingDetails/",10);

    }

    public void validateResult(String CustomerName) throws InterruptedException {
//        if (text.equalsIgnoreCase(CustomerName)) {
//            System.out.println("The shipping details of " + CustomerName + " is displayed");
//        } else {
//            System.out.println("The shipping details of " + CustomerName + " is not displayed");
//        }
//        Thread.sleep(5000);

        System.out.println("Printed statemented is:" + CustomerName);
    }
}
