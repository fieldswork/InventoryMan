package com.skillstorm.selenium;

import java.time.Duration;

import org.hibernate.annotations.processing.Find;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryManPage {

    private WebDriver driver;
    private static final String url = "http://localhost:3000";

    // Add Warehouse button
    @FindBy(xpath = "/html/body/div/div/nav/div/li[3]/a")
    private WebElement addWarehouseButton;

    public InventoryManPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    public String getTabTitle() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return driver.getTitle();
    }

    public void get() {
        this.driver.get(url);
    }
}