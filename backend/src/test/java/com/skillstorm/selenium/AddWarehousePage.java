package com.skillstorm.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddWarehousePage {
    
    private WebDriver driver;
    private static final String url = "http://inventoryman.s3-website-us-east-1.amazonaws.com/add-warehouse";

    @FindBy(xpath = "/html/body/div/div/div/form/div[1]/input")
    private WebElement warehouseName;

    @FindBy(xpath = "/html/body/div/div/div/form/div[2]/input")
    private WebElement warehouseCapacity;

    @FindBy(xpath = "/html/body/div/div/div/form/button")
    private WebElement submitButton;

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

    // Enters a string into the warehouse name field
    public void enterWarehouseName(String warehouseName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.warehouseName.sendKeys(warehouseName);
    }

    // Enters a string into the warehouse capacity field
    public void enterWarehouseCapacity(String warehouseCapacity) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.warehouseCapacity.sendKeys(warehouseCapacity);
    }

    // Clicks the submit button
    public void clickSubmitButton() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        this.submitButton.click();
    }

    // Checks if the user is redirected to the Warehouses page
    public boolean amIOnWarehousesPage() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return this.driver.getCurrentUrl().equals("http://inventoryman.s3-website-us-east-1.amazonaws.com/warehouses");
    }

    //// Checks if the warehouse name is in the Warehouses page
    //public boolean isWarehouseInWarehousesPage(String warehouseName) {
    //    try {
    //        Thread.sleep(1000);
    //    } catch(InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    List<WebElement> warehouses = this.driver.findElements(By.className("warehouse-name"));
    //    for (WebElement warehouse : warehouses) {
    //        if (warehouse.getText().equals(warehouseName)) {
    //            return true;
    //        }
    //    }
    //    return false;
    //}

    // Using xpath /html/body/div/div/div/div/div[2]/div[<variable>]/div/h5 to find the warehouse name
    public boolean isWarehouseInWarehousesPage(String warehouseName) {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> warehouses = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                WebElement warehouse = this.driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[" + i + "]/div/h5"));
                warehouses.add(warehouse);
                values.add(warehouse.getText());
            } catch (Exception e) {
                break;
            }
        }
        return values.contains(warehouseName);
    }

    // /html/body/div/div/div/div/div[2]/div[<variable>]/div/p is the xpath for the capacity, but the capacity is formatted as "0 / <capacity> cubic feet utilized"
    //public boolean isCapacityInWarehousesPage(String warehouseCapacity) {
    //    try {
    //        Thread.sleep(1000);
    //    } catch(InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    List<WebElement> capacities = new ArrayList<>();
    //    List<String> values = new ArrayList<>();
    //    for (int i = 1; i < 100; i++) {
    //        try {
    //            Thread.sleep(1000);
    //        } catch(InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            parse text to get capacity
    //        } catch (Exception e) {
    //            break;
    //        }
    //    }
    //    return values.contains(warehouseCapacity);
    //}
}
