package com.skillstorm.selenium;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AddWarehousePage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/add-warehouse";

    public AddWarehousePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    

}
