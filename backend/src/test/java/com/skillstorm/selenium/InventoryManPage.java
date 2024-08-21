package com.skillstorm.selenium;

import java.time.Duration;

import org.hibernate.annotations.processing.Find;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryManPage {

    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/";

    // Add Warehouse button
    @FindBy(id="add-warehouse")
    private WebElement addWarehouseButton;

    public InventoryManPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    public void clickAddWarehouseButton() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addWarehouseButton.click();
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }
}