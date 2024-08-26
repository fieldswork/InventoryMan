package com.skillstorm.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/";

    // /html/body/div/div/nav/a - InventoryMan button
    @FindBy(xpath = "/html/body/div/div/nav/a")
    private WebElement inventoryManButton;

    

    // Warehouses button: //*[@id="view-warehouse"]
    @FindBy(xpath = "//*[@id=\"view-warehouse\"]")
    private WebElement warehousesButton;

    // Items button: //*[@id="view-items"] 
    @FindBy(xpath = "//*[@id=\"view-items\"]")
    private WebElement itemsButton;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to the Landing page
     */
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    // Check if the user is on the landing page
    public boolean amIOnLandingPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com");
    }

    //selectWarehousesPage
    public void selectWarehousesPage() {
        this.warehousesButton.click();
    }

    // selectItemsPage
    public void selectItemsPage() {
        this.itemsButton.click();
    }

    // amIOnWarehousesPage
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    // amIOnItemsPage
    public boolean amIOnItemsPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/items");
    }

    // selectInventoryMan

}
